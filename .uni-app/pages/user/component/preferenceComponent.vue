<script setup>
import {ref} from 'vue'
import {onLoad} from '@dcloudio/uni-app'
import store from "@/store";
import {removeTokenValue} from "@/store/token";
import {reqLogout} from "@/api/auth";

const isPlugIns = ref(false)

const toRouterPage = (url) => {
  uni.navigateTo(
      {url: url, animationType: 'pop-in', animationDuration: 200}
  )
}

const onChange = (e) => {
  const userSetting = store.getters.userSetting;
  isPlugIns.value = e.detail.value
  userSetting.isPlugIns = isPlugIns.value
  store.commit("setUserSetting", userSetting);
}

const logout = async () => {
  try {
    uni.showLoading({
      title: "正在退出ing",
    });
    await reqLogout();
    removeTokenValue()
    uni.hideLoading()
    uni.reLaunch({
      url: '/pages/master/master'
    })
  } catch (e) {

    uni.showToast({icon: 'none', duration: 3000, title: e.msg});
  }
}

onLoad(() => {
  const userSetting = store.getters.userSetting;
  isPlugIns.value = userSetting.isPlugIns;
})


</script>


<template>

  <view class="flex-padding">
    <view class="container">
      偏好设置
    </view>
    <view class="card">
      <view class="operation">
        <span class="prompt">Use plug ins & all</span>
        <switch :checked="isPlugIns" color="#FFCC33" style="transform:scale(0.7)" @change="onChange"/>
      </view>
      <p class="introduce">
        Using all the plugins included in this application may slow down response speed in some cases. Turning this
        option
        off will keep only essential chat plugins.
      </p>
    </view>
    <view class="card" @click="toRouterPage('/pages/user/view/myConfig')">
      <view class="operation">
        <span class="prompt">管理我的配置</span>
        <span>
        <image src="/static/setting/next.svg" class="image-icon"/>
      </span>
      </view>
      <p class="introduce">
        配置访问令牌以及代理地址用于正常使用功能 (数据不会存储在云端)
      </p>
    </view>
    <view class="card" @click="toRouterPage('/pages/voice/voice')">
      <view class="operation">
        <span class="prompt">Chat Voice</span>
        <span>
        <image src="/static/setting/next.svg" class="image-icon"/>
      </span>
      </view>
      <p class="introduce">
        The analysis of a few seconds of human voice can generate an endless stream of conversation, enabling real-time
        audio communication with users.
      </p>
    </view>
    <view class="card" @click="toRouterPage('/pages/user/view/changeModel')">
      <view class="operation">
        <span class="prompt">Change model</span>
        <span>
        <image src="/static/setting/next.svg" class="image-icon"/>
      </span>
      </view>
      <p class="introduce">
        此选项将会更改对话所使用的模型
      </p>
    </view>
    <view class="card">
      <view class="operation">
        <span class="prompt">Talk to developers</span>
        <span>
        <image src="/static/setting/next.svg" class="image-icon"/>
      </span>
      </view>
      <p class="introduce">
        探讨编程学术类问题或此应用BUG反馈报告
      </p>
      <button open-type="contact" class="customer">
      </button>
    </view>

    <view class="logout" @click="logout">
      <view>
        退出登录
      </view>
    </view>
  </view>
</template>


<style scoped lang="scss">
.container {
  color: white;
  font-size: 25rpx;
}


.logout {
  margin-top: 80rpx
}

.logout view {
  background-color: #f32b2b;
  color: white;
  text-align: center;
  border-radius: 10rpx;
  padding: 15rpx 0;
  font-size: 28rpx
}

.image-icon {
  width: 25rpx;
  height: 25rpx;
  margin-right: 14rpx
}

.card {
  background-color: #262626;
  padding: 30rpx;
  border-radius: 20rpx;
  margin: 30rpx 0;
  color: white;
  position: relative;
}

.customer {
  top: 0;
  right: 0;
  width: 100%;
  height: 100%;
  background-color: unset;
  color: white;
  z-index: 3;
  position: absolute;
}

.operation {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 20rpx
}

.prompt {
  font-size: 28rpx;
  font-weight: 550
}

.flex-padding {
  padding-bottom: 140rpx
}

.introduce {
  font-size: 23rpx;
  color: #c1c1c1;
}
</style>
