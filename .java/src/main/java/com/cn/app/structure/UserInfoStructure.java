package com.cn.app.structure;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户结构体
 *
 * @author bdth
 * @version 1.0
 * @gitHub github@dulaiduwang003
 */
@Data
@Accessors(chain = true)
public class UserInfoStructure implements Serializable {

    private Long userId;

    private String openId;

    private String role;


}
