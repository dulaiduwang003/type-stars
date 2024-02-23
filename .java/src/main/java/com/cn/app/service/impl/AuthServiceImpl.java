package com.cn.app.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.app.cache.UserConstant;
import com.cn.app.dto.WeChatLoginDto;
import com.cn.app.entity.TsUser;
import com.cn.app.enums.RoleEnum;
import com.cn.app.mapper.UserMapper;
import com.cn.app.service.AuthService;
import com.cn.app.structure.UserInfoStructure;
import com.cn.app.utils.WeChatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type Auth service.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    private final WeChatUtils weChatUtils;

    @Override
    public String wechatLogin(final WeChatLoginDto dto) {
        final String openId = weChatUtils.getOpenId(dto.getCode());
        TsUser tsUser = userMapper.selectOne(new QueryWrapper<TsUser>()
                .lambda()
                .eq(TsUser::getOpenId, openId)
        );
        if (tsUser == null) {
            tsUser = new TsUser()
                    .setRole(RoleEnum.USER.getDesc())
                    .setOpenId(openId);
            userMapper.insert(tsUser);
        }
        StpUtil.login(tsUser.getUserId());
        StpUtil.getSession()
                .set(UserConstant.USER_INFO, new UserInfoStructure()
                        .setOpenId(openId)
                        .setRole(tsUser.getRole())
                        .setUserId(tsUser.getUserId())
                );

        return StpUtil.getTokenValue();
    }

    @Override
    public void logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
    }
}
