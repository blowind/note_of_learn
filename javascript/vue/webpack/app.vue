<template>
  <!-- <div>Hello {{name}}</div> -->
  <v-title title="Vue组件化"></v-title>
  <v-button @click="handleClick">点击按钮</v-button>
</template>
<script>
  import vTitle from './title.vue';
  import vButton from './button.vue';

  export default {
    components: {
      vTitle,
      vButton
    },
    methods: {
      handleClick(e) {
        console.log(e);
      }
    },
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