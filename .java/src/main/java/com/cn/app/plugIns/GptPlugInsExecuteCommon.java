package com.cn.app.plugIns;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Plug ins request.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class GptPlugInsExecuteCommon {

    private final DallDrawingPlugIn dallDrawingPlugIn;

    private final FileAnalysisPlugIn analysisPlugIn;

    private final PictureRecognitionPlugIn pictureRecognitionPlugIn;

    public JSONObject invoke(final String name, String dto) {

        switch (name) {
            case "GENERATE_IMAGE" -> {
                return dallDrawingPlugIn.GENERATE_IMAGE(dto);
            }
            case "PARSE_FILE" -> {
                return analysisPlugIn.PARSE_FILE(dto);
            }
            case "RECOGNITION" -> {
                return pictureRecognitionPlugIn.RECOGNITION(dto);
            }
            default -> throw new RuntimeException("no executable methods found");
        }

    }
}
