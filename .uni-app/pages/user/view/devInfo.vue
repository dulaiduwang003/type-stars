<template>
  <view class="body">
    <view>
      <view style="display: flex;justify-content:center;align-items: center;padding-top: 300rpx">
        <view class="relative">
          <image src="/static/icon/github.svg" class="avatar"/>
          <view class="right-icon">
            ✨
          </view>
        </view>
      </view>
      <view style="padding: 60rpx 160rpx 20rpx;">
        <view style="color: rgb(173,186,199);font-weight: 550;font-size: 40rpx">
          bdth
        </view>
        <view style="color: #6b7682;padding-top: 10rpx">
          dulaiduwang003 · he/him
        </view>
        <view style="color: rgb(173,186,199);padding-top: 30rpx">
          road ahead is long and winding. I will explore every inch of it.
        </view>
      </view>

    </view>
  </view>
</template>

<script setup>

</script>


<style scoped lang="scss">
.name {
  padding-top: 20rpx;
  color: white;
  font-weight: 550;
  font-size: 35rpx
}

.avatar {
  height: 260rpx;
  width: 260rpx;
  border-radius: 100%;
  border: 5rpx solid #3f474f;
}

.body {
  height: 100vh;

}


.right-icon {
  font-size: 20rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1rpx solid #f16231;
  position: absolute;
  z-index: 5;
  bottom: 10rpx;
  right: 0;
  width: 50rpx;
  height: 50rpx;
  border-radius: 100%;
  background: rgba(250, 86, 0, 0.22)

}

.relative {
  position: relative
}
</style>
