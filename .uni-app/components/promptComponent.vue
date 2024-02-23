<script setup>
import {defineProps, ref} from 'vue'
import {onLoad} from '@dcloudio/uni-app'

const promptList = ref([{
  title: 'i m going to eat',
  prompt: 'I like you so much that I can\'t separate and I only believe in this truth'
},
  {
    title: 'what you name',
    prompt: 'my name is jack'
  }])

</script>

<template>
  <view class="container">
    <scroll-view class="scroll-view" :scroll-x="true">
      <view class="box" v-for="(item,index) in promptList " :key="index">
        <view class="model">
          <view class="title">
              {{item.title}}
          </view>
          <view class="detailed">
            {{item.prompt}}
          </view>
        </view>
      </view>

    </scroll-view>
  </view>
</template>




<style scoped>

.scroll-view {
  height: 100%;
  width: 100%;
  white-space: nowrap;
  padding: 10rpx;
}

.title {
  font-weight: 550;
  color: #ececec;
}

.model {
  padding: 15rpx 30rpx
}

.box {
  display: inline-block;
  height: 7vh;
  background-color: #1D1D1D;
  border-radius: 20rpx;
  margin-right: 20rpx;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 0 20rpx 10rpx;
}

.detailed {

  font-size: 27rpx;
  color: rgba(193, 193, 193, 0.58);
  max-width: 400rpx;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

}

</style>
