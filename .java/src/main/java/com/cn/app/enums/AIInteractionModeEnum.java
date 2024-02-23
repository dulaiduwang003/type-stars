package com.cn.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The enum Ai enum.
 *
 * @author bdth @github dulaiduwang003
 * @version 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum AIInteractionModeEnum {

    INPUT("INPUT"),

    VOICE("VOICE");

    private String val;


    /**
     * From string ai enum.
     *
     * @param value the value
     * @return the AI enum
     */
    public static AIInteractionModeEnum fromString(final String value) {
        for (AIInteractionModeEnum env : AIInteractionModeEnum.values()) {
            if (env.getVal().equals(value)) {
                return env;
            }
        }
        throw new IllegalArgumentException("conversion failed: " + value);
    }
}
