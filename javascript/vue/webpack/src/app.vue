<template>
  
  <div>
    <!-- <p>Hello {{name}}</p> -->
    <v-title title="Vue组件化"></v-title>
    <v-button @click="handleClick">点击按钮</v-button>
    <p>
      <img src="./image/hani.jpg" style="width: 200px;">
    </p>

    <hr>
    <!-- 添加路由视图来挂在所有的路由组件 -->
    <router-view></router-view>
  </div>
</template>
<script>
  import vTitle from './title.vue';
  import vButton from './button.vue';

  export default {
    components: {
      /*ES6语法，当key和value名称一致时，缩写成一个*/
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