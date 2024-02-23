package com.cn.app.controller;

import com.cn.app.dto.WeChatLoginDto;
import com.cn.app.exceptions.WechatException;
import com.cn.app.msg.Result;
import com.cn.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Auth controller.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * WeChat auth login result.
     *
     * @param dto the dto
     * @return the result
     */
    @PostMapping(value = "/wechat/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result wechatAuthLogin(@RequestBody @Validated WeChatLoginDto dto) {
        try {
            return Result.data(authService.wechatLogin(dto));
        } catch (WechatException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping(value = "/logout",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result logout() {
        authService.logout();
        return Result.ok();
    }
}
