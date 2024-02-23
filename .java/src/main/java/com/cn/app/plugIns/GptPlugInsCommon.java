package com.cn.app.plugIns;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * The type Plug ins request.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Component
@Slf4j
public class GptPlugInsCommon {


    public static JSONArray PLUG_INS_REQUEST_FUNCTION;


    @PostConstruct
    private void loadFunctionJson() {
        //load the plugin
        InputStream inputStream = GptPlugInsCommon.class.getResourceAsStream("/plugIns/plugIns.json");
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                final String collect = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                PLUG_INS_REQUEST_FUNCTION = JSON.parseArray(collect);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        if (PLUG_INS_REQUEST_FUNCTION == null) {
            log.error("the plugin failed to load");
        }
    }


}
