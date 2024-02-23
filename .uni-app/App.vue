<script>

import store from "@/store";

export default {
  onLaunch: async function () {
    store.commit("initState");
  }
}
</script>

<style>
page {
  background-color: #000000 !important;
}

::-webkit-scrollbar {
  display: none;
}

/*每个页面公共css */
</style>
