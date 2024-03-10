package com.cn.app.service.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.app.dto.AiRequestDto;
import com.cn.app.enums.AIInteractionModeEnum;
import com.cn.app.plugIns.GptPlugInsCommon;
import com.cn.app.plugIns.GptPlugInsExecuteCommon;
import com.cn.app.service.strategy.AIInteractionModeStrategy;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.IntStream;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.parseObject;


/**
 * The type Ai dialogue strategy.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Service
@RequiredArgsConstructor

public class AiDialogueStrategyImpl implements AIInteractionModeStrategy {

    @Override
    public String type() {
        return AIInteractionModeEnum.INPUT.getVal();
    }

    @Override
    public boolean isTypeMatch(final String type) {
        return Objects.equals(type, type());
    }


    private final GptPlugInsExecuteCommon gptPlugInsExecuteCommon;

    @Override
    @SuppressWarnings("all")
    public Flux<String> execution(final AiRequestDto dto) {
        return Flux.create(sink -> {
            //convert messages
            final List<JSONObject> messages = parseArray(dto.getMessages(), JSONObject.class);
            //build  request body
            Map<String, Object> map = new HashMap<>();
            map.put("model", dto.getModel());
            map.put("messages", messages);
            map.put("stream", true);
            map.put("tools", GptPlugInsCommon.PLUG_INS_REQUEST_FUNCTION);
            map.put("tool_choice", "auto");
            final StringBuilder argumentsBuilder = new StringBuilder();
            final List<JSONObject>[] initialStructureHolder = new ArrayList[1];
            final boolean[] pluginUsed = new boolean[1];
            final List<JSONObject>[] initialStructureArguments = new ArrayList[1];

            openAISendRequest(map, dto.getToken(), dto.getUrl())
                    .doFinally(signal -> {


                        if (pluginUsed[0] && initialStructureHolder[0] != null) {
                            //获取方法参数
                            final List<JSONObject> argumentList = initialStructureArguments[0];
                            //获取方法名
                            final List<JSONObject> holderList = initialStructureHolder[0];
                            final List<Task> taskList = new LinkedList<>();
                            JSONObject tool_function = new JSONObject();
                            JSONArray tools = new JSONArray();
                            tool_function.put("role", "assistant");

                            IntStream.range(0, holderList.size())
                                    .forEach(index -> {

                                        JSONObject function = holderList.get(index).getJSONObject("function");
                                        if (function != null) {
                                            // reinsert the full arguments
                                            function.put("arguments", JSON.toJSONString(argumentList.get(index)));
                                            String functionName = function.getString("name");
                                            String id = holderList.get(index).getString("id");
                                            String arguments = function.getString("arguments");
                                            tools.add(holderList.get(index));
                                            taskList.add(new Task()
                                                    .setFunctionName(functionName)
                                                    .setArguments(arguments)
                                                    .setToolCallId(id)
                                            );
                                        }
                                    });
                            tool_function.put("tool_calls", tools);
                            messages.add(tool_function);
                            taskList.parallelStream().forEach(t -> {
                                final JSONObject invoke = gptPlugInsExecuteCommon.invoke(t.getFunctionName(), t.getArguments(), dto.getUrl(), dto.getToken());
                                //build request body
                                messages.add(parseObject(JSON.toJSONString(new ToolsMessages()
                                        .setContent(invoke.toString())
                                        .setTool_call_id(t.getToolCallId())
                                        .setName(t.getFunctionName()))));
                                map.put("messages", messages);
                            });
                            openAISendRequest(map, dto.getToken(), dto.getUrl())
                                    .doFinally((f) -> sink.complete())
                                    .subscribe(data -> {
                                        sink.next(analysis(data));
                                    });

                        } else {
                            sink.complete();
                        }

                    })
                    .subscribe(data -> {

                        if (JSON.isValidObject(data)) {
                            JSONObject jsonObject = JSONObject.parseObject(data);
                            JSONArray choices = jsonObject.getJSONArray("choices");
                            if (choices != null && !choices.isEmpty()) {
                                final JSONObject dataSet = choices.getJSONObject(0);
                                if (dataSet.containsKey("delta")) {
                                    final JSONObject delta = dataSet.getJSONObject("delta");
                                    JSONArray t = delta.getJSONArray("tool_calls");
                                    if (t != null) {
                                        final JSONObject p = t.getJSONObject(0);
                                        if (p.containsKey("type")) {
                                            if (initialStructureHolder[0] == null) {
                                                initialStructureHolder[0] = new ArrayList<>();
                                                initialStructureHolder[0].add(p);
                                            } else {
                                                initialStructureHolder[0].add(p);
                                            }

                                        }
                                    }
                                    if (delta.containsKey("tool_calls")) {
                                        pluginUsed[0] = true; // the tag detects the use of the plugin
                                        JSONArray toolCalls = delta.getJSONArray("tool_calls");
                                        for (int i = 0; i < toolCalls.size(); i++) {
                                            JSONObject toolCall = toolCalls.getJSONObject(i);
                                            JSONObject function = toolCall.getJSONObject("function");
                                            if (function != null && function.containsKey("arguments")) {
                                                String arguments = function.getString("arguments");
                                                argumentsBuilder.append(arguments);

                                            }
                                        }
                                        final String string = argumentsBuilder.toString();
                                        try {
                                            if (JSON.isValidObject(string)) {
                                                if (initialStructureArguments[0] == null) {
                                                    initialStructureArguments[0] = new ArrayList<>();
                                                    initialStructureArguments[0].add(JSONObject.parseObject(argumentsBuilder.toString()));
                                                    argumentsBuilder.setLength(0);
                                                } else {
                                                    initialStructureArguments[0].add(JSONObject.parseObject(argumentsBuilder.toString()));
                                                }
                                            }
                                        } catch (Exception e) {

                                        }

                                    } else {

                                        if (!pluginUsed[0]) {
                                            // process and return data directly
                                            try {
                                                if (delta != null && delta.containsKey("content")) {
                                                    sink.next(delta.getString("content"));
                                                }
                                            } catch (Exception ex) {

                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }, throwable -> {
                        final String message = throwable.getMessage();
                        sink.next("服务貌似出现了一点问题,请稍后再试 错误原因: \n ```json \n" + message + " \n ```");
                    });
        });
    }

    private static Flux<String> openAISendRequest(final Object body, final String token, final String url) {
        return WebClient.builder()
                .baseUrl(url + "/chat/completions")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build()
                .post()
                .body(BodyInserters.fromValue(
                        body
                ))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), (clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            final String errorCode = parseObject(errorBody).getString("error");
                            final JSONObject jsonObject = parseObject(errorCode);

                            return Mono.error(new RuntimeException(jsonObject.toJSONString()));
                        })))
                .bodyToFlux(String.class);
    }


    private String analysis(String data) {
        if (JSON.isValidObject(data)) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            JSONArray choices = jsonObject.getJSONArray("choices");
            if (choices != null && !choices.isEmpty()) {
                final JSONObject dataSet = choices.getJSONObject(0);
                if (dataSet.containsKey("delta")) {
                    final JSONObject delta = dataSet.getJSONObject("delta");
                    if (delta.containsKey("content")) {

                        return delta.getString("content");
                    }
                }
            }
        }
        return "";
    }

    @Data
    @Accessors(chain = true)
    public static class Task {

        private String functionName;

        private String arguments;

        private String toolCallId;

    }

    @Data
    @Accessors(chain = true)
    public static class NormalMessages {

        private String role;

        private String content;

    }


    @Accessors(chain = true)
    @Data
    public static class ToolsMessages {

        private String role = "tool";

        private String content;

        private String tool_call_id;

        private String name;
    }


}
