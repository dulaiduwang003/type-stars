package com.cn.app.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Ai interaction mode strategy factory.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AIInteractionModeStrategyFactory {

    private final List<AIInteractionModeStrategy> aiDialogueStrategies;


    /**
     * Gets .Uno
     *
     * @param type the type
     * @return the
     */
    public AIInteractionModeStrategy getImpl(String type) {
        return aiDialogueStrategies.stream()
                .filter(strategy -> strategy.isTypeMatch(type))
                .findAny()
                .orElseThrow(() -> new RuntimeException("no execution of this type was found"));
    }

}
