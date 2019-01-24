<template>
  <div>
  <h1>首页</h1>
  <!-- 指定跳转的页面，默认是生成<a>的跳转链接 -->
  <router-link to="/about">跳转到about</router-link>
  <!-- 指定跳转的页面，tag指定生成<li>的跳转链接，replace不会留下history记录，不能用回退键返回上一页 -->
  <router-link to="/user/1234" tag="li" replace>渲染的结果是li元素</router-link>

  <!-- 页面跳转的第二种方式 -->
    <button @click="handleRouter">跳转到user</button>
</div>

</template>
<script>
  export default {
    methods: {
      handleRouter() {
        this.$router.push('/user/4321');

        /*类似<router-link>标记里的replace标签，不会留下history记录*/
        /*this.$router.replace('/user/4321');*/

        /*类似于window.history.go()，在history记录中向前(+)或者后退(-)多少步，参数是整数*/
        /*this.$router.go(-1);*/
      }
    }
  }
</script>