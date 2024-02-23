package com.cn.app.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName(value = "ts_user")
public class TsUser {

  @TableId(type = IdType.AUTO)
  private Long userId;

  private String openId;

  private String role;

  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createdTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;



}
