<template>
  <!-- 访问当前路由过来的信息 -->
  <div>{{ $route.params.id }}</div>
</template>
<script>
  export default {
    mounted() {
      console.log(this.$route.params.id);
    }
  }
</script>