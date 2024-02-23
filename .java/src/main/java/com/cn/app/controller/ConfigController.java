package com.cn.app.controller;

import com.cn.app.msg.Result;
import com.cn.app.service.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Gpt controller.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {

    private final GptService gptService;

    @GetMapping(value = "/get/model/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getModelList() {
        return Result.data(gptService.getModelList());
    }

}
