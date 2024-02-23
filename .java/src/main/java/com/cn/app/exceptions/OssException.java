package com.cn.app.exceptions;


import lombok.Data;

/**
 * 微信接口异常处理
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@SuppressWarnings("all")
@Data
public class OssException extends RuntimeException {

    private String message;

    private Integer code;


    public OssException(final String message, final Integer code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public OssException(final String message) {
        super(message);
        this.message = message;
        this.code = 500;
    }
}
