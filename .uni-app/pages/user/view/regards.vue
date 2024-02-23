<template>
  <view class="container">
    <view class="model">
      <view>
        <view class="lottie-model">
          <lottie-animation :frames="AniLottie" :width="400" :height="300"/>
        </view>
        <view class="text">
          <view>
            Type Stars
          </view>
          <view class="version">
            Version 1.0.10
          </view>
          <view class="link" @click="copy">
            {{link}}
          </view>
        </view>
      </view>
    </view>

    <view class="label">
      <view>
        <view class="uni">
          #uni-app
        </view>
        <view class="java">
          #java
        </view>
        <view class="lottie">
          #lottie
        </view>
        <view class="spring">
          #spring
        </view>
      </view>

    </view>
  </view>

</template>

<script setup>
import {ref} from 'vue'
import LottieAnimation from "@/components/lottieAnimation.vue";
import AniLottie from "@/static/lottie/color.json";

const link = ref('https://github.com/dulaiduwang003/TS-GPT')

const copy = () => {
  uni.setClipboardData({
    data: link.value
  });
}

</script>


<style scoped lang="scss">
.container {
  height: 100vh;
  background: #181818;
  position: relative
}

.label {
  display: flex;
  width: 750rpx;
  justify-content: center;
  align-items: center;
  position: absolute;
  z-index: 2;
  bottom: 80rpx;
  color: white;
}

.spring {
  padding: 5rpx 15rpx;
  background: rgb(26, 97, 29);
  font-size: 24rpx;
  border-radius: 8rpx
}

.lottie {
  padding: 5rpx 15rpx;
  background: rgb(220, 24, 24);
  font-size: 24rpx;
  border-radius: 8rpx
}

.java {
  padding: 5rpx 15rpx;
  background: rgb(246, 130, 49);
  font-size: 24rpx;
  border-radius: 8rpx
}

.uni {
  padding: 5rpx 15rpx;
  background: rgb(43, 153, 57);
  font-size: 24rpx;
  border-radius: 8rpx
}

.model {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 20vh;
}

.text {
  text-align: center;
  font-size: 40rpx;
  font-weight: 600;
  color: rgb(208, 208, 208);
  padding-top: 50rpx
}

.label > view {
  width: 500rpx;
  display: flex;
  justify-content: space-between;
  align-items: center
}

.lottie-model {
  display: flex;
  justify-content: center;
  align-items: center
}

.link {
  font-size: 23rpx;
  font-weight: 500;
  padding-top: 100rpx
}

.version {
  padding-top: 25rpx;
  font-size: 26rpx;
  font-weight: 500
}
</style>
