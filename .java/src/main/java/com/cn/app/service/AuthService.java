package com.cn.app.service;

import com.cn.app.dto.WeChatLoginDto;

/**
 * The interface Auth service.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
public interface AuthService {


    /**
     * WeChat login string.
     *
     * @param dto the dto
     * @return the string
     */
    String wechatLogin(final WeChatLoginDto dto);


    /**
     * Logout.
     */
    void logout();
}
