package com.cn.app.plugIns;

import com.alibaba.fastjson.JSONObject;
import com.cn.app.enums.FilePathEnum;
import com.cn.app.utils.UploadUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Serializable;

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
public class DallDrawingPlugIn {

    private final UploadUtil uploadUtil;

    @Value("${ali-oss.domain}")
    private String ossDomain;

    /**
     * The type Dall e 3.
     */
    @Data
    @Accessors(chain = true)
    public static class DallE3 implements Serializable {

        private String model = "dall-e-3";

        private String prompt;

    }


    /**
     * Generate image string.
     *
     * @param dto the prompt
     * @return the string
     */
    public JSONObject GENERATE_IMAGE(final String dto) {
        final String block = WebClient.builder()
                .baseUrl("https://www.lxlchat.top/v1/images/generations")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer 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")
                .codecs(item -> item.defaultCodecs().maxInMemorySize(20 * 1024 * 1024))
                .build()
                .post()
                .body(BodyInserters.fromValue(parseObject(dto, DallE3.class)))
                .retrieve()
                //print link error message
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), (clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            final String errorCode = parseObject(errorBody).getString("error");
                            final JSONObject jsonObject = parseObject(errorCode);
                            log.error("error!!! an error occurred :{}", jsonObject);
                            return Mono.error(new RuntimeException());
                        })))
                .bodyToMono(String.class).block();
        //analysis JSON
        final JSONObject jsonObject = JSONObject.parseObject(block);
        assert jsonObject != null;
        final JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
        String revised = data.getString("revised_prompt");

        final String image = ossDomain + uploadUtil.uploadImageFromUrl(data.getString("url"), FilePathEnum.TEMP.getDec());

        return (JSONObject) JSONObject.toJSON(new Vo().setImage(image).setIntroduce(revised));
    }


    /**
     * The type Vo.
     */
    @Data
    @Accessors(chain = true)
    public static class Vo {

        private String introduce;

        private String image;

    }
}
