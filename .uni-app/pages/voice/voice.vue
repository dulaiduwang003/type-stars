<template>

  <view class="container">
    <view class="title">Chat with voice</view>
    <view class="introduce">
      day that the flowers fell in the classroom, how can I not see the disappearing rainy day I want to shower again
    </view>

    <view class="box">
      <view>
        <image src="/static/icon/bulb.svg"/>
      </view>
      <view class="box-content">
        <view>
          <view>
            I can't get rid of her smile and the smell of happiness
          </view>
          <view class="box-text">
            Her eyelashes curled and her lips smiled at me without warning and without warning and with an unexpected
            first gesture
          </view>
        </view>

      </view>
    </view>
    <view class="box">
      <view>
        <image src="/static/icon/net.svg"/>
      </view>
      <view class="box-content">
        <view>
          <view>
            The impression of love seems to be unable to withstand the time
          </view>
          <view class="box-text">
            The sequence of the iron box became a diary became an air evolved into a memory of the impression of love
            seems to be unable to withstand the time
          </view>
        </view>

      </view>
    </view>
    <button @click="toAudio">
      connection
    </button>

  </view>
</template>

<script setup>

const toAudio = () => {
  uni.vibrateShort()
  uni.showLoading({
    title: "连接中",
  });
  setTimeout(() => {
    uni.showToast({icon: 'none', duration: 3000, title: "无权限访问"});
    setTimeout(()=>{
      uni.navigateBack()
    },500)
    uni.hideLoading()

  },1000)

}

</script>


<style scoped lang="scss">
.container {
  padding: 30rpx;
  color: white;
  position: relative;
  height: 95vh;
}

button {
  width: 640rpx;
  margin-top: 30vh;
  background: #c156f2;
  color: white;
  font-weight: 550
}

.title {
  font-size: 60rpx;
  font-weight: 550
}

.introduce {
  padding-top: 20rpx;
  color: #bebebe
}

.box {
  display: flex;
  align-items: flex-start;
  margin-top: 60rpx
}

.box image {
  width: 100rpx;
  height: 100rpx
}

.box-content {
  padding-left: 20rpx;
  font-size: 28rpx
}

.box-text {
  padding-top: 10rpx;
  color: #838383
}
</style>
