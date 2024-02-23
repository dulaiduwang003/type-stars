<script setup>
import store from "@/store";
import env from "@/env";
import {getTokenValue} from "@/store/token";


const uploadAvatar = (e) => {

  uni.showLoading({
    title: "正在上传中",
  });
  wx.uploadFile({
    url: `${env.baseHttps}/oss/upload/file`,  //服务器地址
    filePath: e.detail.avatarUrl,
    name: "file",
    header: {
      'Authorization': 'Bearer ' + getTokenValue()
    },
    success(res) {
      const data = JSON.parse(res.data);
      if (data.code !== 200) {
        uni.showToast({icon: 'none', duration: 3000, title: data.msg});
        return
      }

      store.commit("setUserInfo", {
        avatar: data.data.fileUrl
      });
      uni.showToast({
        title: '上传头像成功',
        icon: 'none',
        duration: 2000
      })
    },
    fail(res) {

      uni.showToast({
        title: '上传头像失败,请稍后重试',
        icon: 'none',
        duration: 2000
      })
    }
  })
}


</script>


<template>
  <view class="container">
    <view class="avatar" v-if="!store.getters.userInfo.avatar">
      <image src="/static/icon/camera.svg"/>
      <button open-type="chooseAvatar" @chooseavatar="uploadAvatar"
              class="avatar-button"
              :plain="true"></button>
    </view>

    <view class="user-avatar" v-else>
      <image :src="store.getters.userInfo.avatar"/>
      <button open-type="chooseAvatar" @chooseavatar="uploadAvatar"
              class="avatar-button"
              :plain="true"></button>

    </view>

    <view class="info">
      <view class="username">
        YOU
      </view>
      <view class="author">
        welcome to type stars mini app
      </view>
    </view>
  </view>

</template>


<style scoped lang="scss">
.container {
  display: flex;
  color: white;
  align-items: center
}

.author {
  font-size: 25rpx;
  padding-top: 20rpx;
  color: #e3e3e3
}

.info {
  padding-left: 50rpx
}

.username {
  font-size: 45rpx;
  font-weight: bold
}

.avatar {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 100%;
  width: 150rpx;
  height: 150rpx;
  background-color: #3e3e3e
}

.avatar image {
  width: 70rpx;
  height: 70rpx
}

.avatar > view {
  border: 8rpx solid #000000;
  border-radius: 100%;
  background-color: rgb(1, 149, 245);
  width: 35rpx;
  height: 35rpx;
  position: absolute;
  z-index: 2;
  bottom: 0;
  right: 0
}

.user-avatar {
  border-radius: 100%;
  width: 150rpx;
  height: 150rpx;
  overflow: hidden;
  position: relative;
}

.user-avatar image {
  width: 100%;
  height: 100%
}

button::after {
  border: none;
}

.avatar-button {
  width: 150rpx;
  height: 150rpx;
  position: absolute;
  left: 0;
  top: 0;
  border: none;
}
</style>
