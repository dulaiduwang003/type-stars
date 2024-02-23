import request from './../utils/request';

export function reqGetModelList() {
    return request({
        url: '/config/get/model/list',
        method: 'GET'
    })
}
