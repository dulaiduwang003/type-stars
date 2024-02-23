package com.cn.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The type We chat login dto.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class WeChatLoginDto {

    @NotBlank(message = "authorization code cannot be empty")
    private String code;
}
