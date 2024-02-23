
package com.cn.app.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 微信 AccessToken 封装
 *
 * @author bdth @github dulaiduwang003
 * @version 1.0
 */
@Slf4j
public enum WeChatTokenUtil {
    INSTANCE;

    private final Token token = new Token();
    private final AtomicLong tokenExpireTime = new AtomicLong(0L);

    public String getWechatToken(final String appid, final String secret) {
        if (System.currentTimeMillis() / 1000 >= tokenExpireTime.get()) {
            resetToken(appid, secret);
        }
        return token.getToken();
    }

    private void resetToken(final String appid, final String secret) {
        final JSONObject block = JSONObject.parseObject(getAccessToken(appid, secret));
        long timestamp = System.currentTimeMillis() / 1000;
        String accessToken = block.getString("access_token");
        int expiresIn = block.getInteger("expires_in");
        token.setToken(accessToken);
        tokenExpireTime.set(timestamp + expiresIn);
    }

    private String getAccessToken(final String appid, final String secret) {
        final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        try {
            return WebClient.create()
                    .get().uri(accessTokenUrl)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.error("获取AccessToken失败:{}", e.getMessage());
            throw new RuntimeException();

        }
    }

    @Accessors(chain = true)
    @Data
    public static class Token {
        public String token;
    }
}
