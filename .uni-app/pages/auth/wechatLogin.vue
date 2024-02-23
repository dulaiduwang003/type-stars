<script setup>
import AniLottie from '@/static/lottie/paperMan.json';
import StarLottie from '@/static/lottie/star.json';
import LottieAnimation from "@/components/lottieAnimation.vue";
import {ref} from 'vue'
import {wechatLogin} from "@/api/auth";
import {setTokenValue} from "@/store/token";


const isLoading = ref(false)

const handleLogin = () => {
  uni.vibrateShort()
  isLoading.value = true
  uni.login({
    async success(res) {
      try {
        const {data} = await wechatLogin({
          code: res.code
        })
        setTokenValue(data)
        uni.showToast({icon: 'none', duration: 3000, title: "欢迎使用 TYPE STARS"});
        uni.reLaunch({
          url: '/pages/master/master'
        })
      } catch (e) {
        uni.showToast({
          icon: 'none',
          duration: 6000,
          title: e.msg
        });
      } finally {
        isLoading.value = false
      }
    }
  })
}


</script>


<template>
  <view class="body">
    <view class="icon">
      <lottie-animation :frames="AniLottie" :width="500" :height="300"/>
    </view>
    <view class="prompt">
      <p class="title">TRY TYPE STARS</p>
      <p class="word">welcome to use type stars mini app</p>
    </view>
    <view class="button-box" @click="handleLogin">
      <button class="btn-login" :loading="isLoading">{{ !isLoading ? '微信授权登录' : '' }}</button>
      <view class="animation">
        <lottie-animation :frames="StarLottie" :width="400" :height="300"/>
      </view>
    </view>
  </view>


</template>


<style scoped lang="scss">


.icon {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 200rpx;
}

.btn-login {
  font-size: 30rpx;
  border-radius: 70rpx;
  color: white;
  background: linear-gradient(120deg, rgba(98, 158, 239) 1%, rgba(186, 124, 250) 30.31%, rgba(225, 109, 189) 66.24%, rgb(255, 146, 146) 97.8%);;
  animation: gradient 4s ease infinite;
  background-size: 200% 200%;
  border: none;
  padding: 10rpx;
}

.animation {
  position: absolute;
  z-index: 2;
  top: -65rpx;
  left: 130rpx
}

@keyframes gradient {
  0% {
    background-position: 0 12%;
  }

  50% {
    background-position: 100% 100%;
  }

  100% {
    background-position: 0 12%;
  }
}

.body {
  padding: 50rpx;
  animation: fadeIn 0.5s ease-in-out forwards;
}

.prompt {
  color: #ffffff;
  padding-left: 30rpx;
  padding-top: 130rpx
}

.title {
  font-size: 55rpx;
  font-weight: 550
}

.word {
  font-size: 23rpx;
  color: #a9a7a7;
  padding-top: 10rpx
}

.button-box {

  margin-top: 100rpx;
  padding: 0 30rpx;
  position: relative;
}
</style>
