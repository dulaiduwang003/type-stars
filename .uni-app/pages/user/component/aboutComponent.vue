<template>
  <view class="about">
    <view @click="toThisApp">
      关于此应用
    </view>
    <view @click="toDevInfo">
      开发者信息
    </view>
  </view>
</template>

<script setup>

const toThisApp = () => {
  uni.navigateTo({
    url: '/pages/user/view/regards',
    animationType: 'pop-in',
    animationDuration: 200
  })
}

const toDevInfo = () => {
  uni.navigateTo({
    url: '/pages/user/view/devInfo',
    animationType: 'pop-in',
    animationDuration: 200
  })
}

</script>


<style scoped lang="scss">

.about {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 60rpx 0 40rpx;
}

.about view {
  background-color: rgb(38, 38, 38);
  width: 340rpx;
  height: 60rpx;
  display: flex;
  border-radius: 18rpx;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 20rpx;
}
</style>
