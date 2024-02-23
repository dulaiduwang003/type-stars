package com.cn.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.app.entity.TsUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface User mapper.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<TsUser> {
}
