package com.cn.app.service.strategy;

import com.cn.app.dto.AiRequestDto;
import reactor.core.publisher.Flux;

/**
 * The interface Ai interaction mode strategy.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
public interface AIInteractionModeStrategy {

    /**
     * Type string.
     *
     * @return the string
     */
    String type();


    /**
     * Is type match boolean.
     *
     * @param type the type
     * @return the boolean
     */
    boolean isTypeMatch(String type);


    /**
     * Execution flux.
     *
     * @param dto the dto
     * @return the flux
     */
    Flux<String> execution(AiRequestDto dto);
}
