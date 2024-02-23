package com.cn.app.service;

import com.cn.app.vo.ModelVo;

import java.util.List;

/**
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
public interface GptService {


    /**
     * Gets model list.
     *
     * @return the model list
     */
    List<ModelVo> getModelList();

}
