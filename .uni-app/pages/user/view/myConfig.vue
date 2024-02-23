<script setup>
import {ref} from 'vue'
import {onLoad} from '@dcloudio/uni-app'
import store from "@/store";


const form = ref({
  token: '',
  url: ''
})

onLoad(() => {
  const userSetting = store.getters.userSetting;
  form.value.token = userSetting.token;
  form.value.url = userSetting.url;
  console.log(userSetting)
})

const saveConfig = () => {
  const {token, url} = form.value
  if (!token) {
    uni.showToast({icon: 'none', duration: 3000, title: "请填写密钥"}); return
  }
  if (!url) {
    uni.showToast({icon: 'none', duration: 3000, title: "请填写代理地址"}); return
  }
  uni.vibrateShort()
  uni.showLoading({
    title: "正在保存中",
  });
  const userSetting = store.getters.userSetting;

  userSetting.token = token
  userSetting.url = url
  store.commit("setUserSetting", userSetting);
  uni.showToast({icon: 'none', duration: 3000, title: "保存成功"});
  uni.hideLoading()

}

</script>


<template>
  <view class="container">
    <textarea
        v-model="form.token"
        show-confirm-bar="false"
        maxlength="-1"
        placeholder="请设置密钥"
        class="token"/>

    <textarea
        v-model="form.url"
        show-confirm-bar="false"
        maxlength="-1"
        placeholder="请设置代理地址"
        class="url"/>

    <view class="btn">
      <button @click="saveConfig">
        保存配置
      </button>
    </view>

    <view class="tips">
      Tips
      <p class="prompt">
        您的任何配置不会存储在云端 而是存储于本地缓存能力中;密钥输入示例如下(注意 请不要使用示例的代理或密钥)
      </p>
      <p class="light">sk-5nw1CakeSVzr56rSu7k9T3BlkFJe</p>
      <p class="prompt">
        代理地址输入示例如下
      </p>
      <p class="light">https://www.bdth.top/v1</p>
    </view>

  </view>
</template>


<style scoped lang="scss">
.container {
  padding: 20rpx;
}

.btn {
  margin-top: 40rpx;
  color: white
}

.tips {
  color: #bfbfbf;
  margin-top: 40rpx;
  font-size: 26rpx;
  font-weight: 550
}

.prompt {
  margin-top: 20rpx;
  font-weight: 500;
  color: #5b5b5b
}

.light {
  color: #7a27d2;
  margin-top: 20rpx;
  margin-bottom: 30rpx
}

.btn button {
  background: #7b27d3;
  color: white;
  font-size: 26rpx;
  padding: 10rpx
}

.token {
  width: 670rpx;
  color: white;
  background: #212121;
  height: 260rpx;
  border-radius: 15rpx;
  padding: 20rpx
}

.url {
  margin-top: 20rpx;
  width: 670rpx;
  color: white;
  background: #212121;
  height: 100rpx;
  border-radius: 15rpx;
  padding: 20rpx
}
</style>
