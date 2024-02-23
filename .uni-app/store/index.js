import {createStore} from 'vuex'
import {getTokenValue, removeTokenValue} from "@/store/token";
import {getUserInfo, removeUserInfo, setUserInfo} from "@/store/userInfo";
import {getUserSetting, removeUserSetting, setUserSetting} from "@/store/userSetting";

const store = createStore({
    state: {
        userInfo: undefined,
        userSetting: undefined,
    },
    getters: {
        userInfo: (state) => state.userInfo,
        userSetting: (state) => state.userSetting,
    },
    mutations: {
        logout(state) {
            state.userInfo = undefined
            removeTokenValue()
        },
        setUserInfo(state, info) {
            state.userInfo = info;
            setUserInfo(info)
        },
        removeUserInfo(state) {
            state.userInfo = undefined;
            removeUserInfo()
        },
        setUserSetting(state, info) {
            state.userSetting = info;
            setUserSetting(info);
        },
        removeUserSetting(state) {
            state.userSetting = undefined;
            removeUserSetting()
        },
        initState(state) {
            let tokenValue = getTokenValue();
            if (tokenValue) {
                let user = getUserInfo();
                let setting = getUserSetting();
                state.userInfo = user;
                state.userSetting = setting;
            }
        },
    },
})

export default store
