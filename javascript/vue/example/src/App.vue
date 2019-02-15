<template>
  <div id="app">
      <tabs></tabs>

      <div class="content">
        <!-- 使用router-view渲染视图，此处根据具体的点击的页面显示对应内容 -->
        <router-view></router-view>
      </div>
  </div>
</template>

<script>
/*引入首页底层的tabs标签栏，用来导航*/
import Tabs from './components/vue/tabs.vue'
/*引入全站的全局css布局文件*/
import "./assets/less/site.less"

export default {
  name: 'app',
    /*引入部件Tabs，在template中使用tabs标签使用，即首字符大写不转-的驼峰转-形式*/
  components: {
      Tabs
  }
}
</script>
