package com.cn.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.cn.app.exceptions.WechatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 微信工具类
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 * @date 2022/6/11 14:23
 */
@Component
@SuppressWarnings("all")
@RequiredArgsConstructor
@Slf4j
public class WeChatUtils {

    @Value("${we-chat.app-id}")
    private String appId;

    @Value("${we-chat.secret-key}")
    private String secretKey;

    @Value("${we-chat.host}")
    private String host;


    private final RedisUtils redisUtils;


    /**
     * Gets open id.
     *
     * @param code the code
     * @return the open id
     */
    public String getOpenId(final String code) {
        try {
            final String url = host + "/sns/jscode2session?appid=" + this.appId +
                    "&secret=" + this.secretKey + "&js_code=" + code + "&grant_type=authorization_code";
            final String response = WebClient.builder().build().get().uri(url).retrieve().bodyToMono(String.class).block();
            final JSONObject block = JSONObject.parseObject(response);

            final String openid = block.getString("openid");
            assert openid != null;
            return openid;
        } catch (Exception e) {

            log.error("FAILED TO OBTAIN WEIXIN USER ID INFORMATION:{}", e.getMessage());
            throw new WechatException("获取微信用户标识失败");
        }
    }


}
