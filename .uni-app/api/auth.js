import request from './../utils/request';

export function wechatLogin(data) {
    return request({
        url: '/auth/wechat/login',
        method: 'POST',
        data
    })
}
