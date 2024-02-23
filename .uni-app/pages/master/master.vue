<template>
  <view class="body">
    <!--    导航-->
    <navigation-component/>
    <!--    区分显示内容-->
    <view class="view-container">
      <chat-component/>
    </view>
  </view>
</template>

<script setup>

import NavigationComponent from "@/components/navigationComponent";
import ChatComponent from "@/pages/master/component/chatComponent";
import store from "@/store";
import {reqGetModelList} from "@/api/config";
import {onLoad} from '@dcloudio/uni-app'

onLoad(() => {
  init()
})

const init = async () => {
  if (!store.getters.userSetting) {
    try {
      const {data} = await reqGetModelList();
      store.commit("setUserSetting", {
        model: data[0].model,
        token: '',
        url: '',
        isPlugIns: false
      });
      store.commit("setUserInfo", {
        avatar: ''
      });
    } catch (e) {
      console.log(e)
      uni.showToast({icon: 'none', duration: 3000, title: "初始化用户数据失败"});
    }
  }

}

</script>


<style scoped lang="scss">
.body {
  padding: 30rpx;
}

.view-container {
  color: white;
  padding-top: 10vh;
  flex-direction: column;
  flex: 1;
}

</style>
