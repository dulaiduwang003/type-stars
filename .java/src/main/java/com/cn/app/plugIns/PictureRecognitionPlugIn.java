package com.cn.app.plugIns;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * The type Dall drawing plug in.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PictureRecognitionPlugIn {

    /**
     * The type Dall e 3.
     */
    @Data
    @Accessors(chain = true)
    public static class PictureRecognition implements Serializable {

        private String model = "gpt-4-vision-preview";

        private Integer max_tokens = 300;

        private List<Message> messages;

    }

    @Data
    @Accessors(chain = true)
    public static class Message {

        private String role = "user";

        private List<JSONObject> content;
    }

    @Data
    @Accessors(chain = true)
    public static class Attribute {

        private String text;

        private String url;
    }


    /**
     * Generate image string.
     *
     * @param dto the prompt
     * @return the string
     */
    public JSONObject RECOGNITION(final String dto, final String proxyAddress, final String authToken) {
        final Vo vo = new Vo();
        try {
            final Attribute attribute = parseObject(dto, Attribute.class);
            final List<JSONObject> messages = getJsonObjects(attribute);
            final PictureRecognition body = new PictureRecognition()
                    .setMessages(List.of(new Message().setContent(messages)));
            final String block = WebClient.builder()
                    .baseUrl(proxyAddress + "/chat/completions")
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                    .codecs(item -> item.defaultCodecs().maxInMemorySize(20 * 1024 * 1024))
                    .build()
                    .post()
                    .body(BodyInserters.fromValue(body))
                    .retrieve()
                    .bodyToMono(String.class).block();
            final JSONObject jsonObject = parseObject(block);
            assert jsonObject != null;
            final JSONArray choices = jsonObject.getJSONArray("choices");
            final JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            return (JSONObject) JSONObject.toJSON(vo.setResult(message.getString("content")));
        } catch (Exception e) {
            return (JSONObject) JSONObject.toJSON(vo.setResult(e.getMessage()));
        }
    }

    private static List<JSONObject> getJsonObjects(Attribute attribute) {
        JSONObject prompt = new JSONObject();
        prompt.put("type", "text");
        prompt.put("text", attribute.getText());
        JSONObject image = new JSONObject();
        image.put("type", "image_url");
        final JSONObject imageUrl = new JSONObject();

        imageUrl.put("url", attribute.url);
        image.put("image_url", imageUrl);

        List<JSONObject> messages = new LinkedList<>();
        messages.add(prompt);
        messages.add(image);
        return messages;
    }


    /**
     * The type Vo.
     */
    @Data
    @Accessors(chain = true)
    public static class Vo {

        private String result;


    }
}
