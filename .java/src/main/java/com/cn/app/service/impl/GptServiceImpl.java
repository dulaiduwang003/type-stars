package com.cn.app.service.impl;

import com.cn.app.configuration.GptConfiguration;
import com.cn.app.service.GptService;
import com.cn.app.vo.ModelVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Gpt service.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class GptServiceImpl implements GptService {

    private final GptConfiguration gptConfiguration;

    @Override
    public List<ModelVo> getModelList() {
        return gptConfiguration.getModelList()
                .stream().map(c -> new ModelVo()
                        .setModel(c.getModel())
                        .setName(c.getName())).toList();

    }
}
