<template>
  <view class="body">
    <info-component/>
    <about-component/>
    <preference-component/>
  </view>
</template>

<script setup>

import InfoComponent from "@/pages/user/component/infoComponent.vue";
import AboutComponent from "@/pages/user/component/aboutComponent.vue";
import preferenceComponent from "@/pages/user/component/preferenceComponent.vue";
</script>


<style scoped lang="scss">
.body {
  padding: 30rpx;
}

</style>
