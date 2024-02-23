package com.cn.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The enum File enum.
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum FileTypeEnum {

    PDF("PDF"),

    XLSX("XLSX"),

    DOCX("DOCX"),

    IMAGE("IMAGE");

    String dec;

}
