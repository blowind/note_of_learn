<template>
  <div>Hello {{name}}</div>
</template>
<script>
  export default {
    data () {
      return { name: 'Vue.js'}
    }
  }
</script>
<!-- scoped标记指定css仅对本文件内的内容生效，否则全局有效 -->
<style scoped>
  div{
    color: #f60;
    font-size: 24px;
  }
</style>