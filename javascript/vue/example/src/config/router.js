/*全局第三方组件*/
import Vue from 'vue'
import VueRouter from 'vue-router'

/*本地定义的组件*/
import Home from './Home.vue'
import Explorer from './Explorer.vue'
import Cart from './Cart.vue'
import Me from './Me.vue'

Vue.use(VueRouter)


export default = new VueRouter({
  /*使用history模式，该模式依赖HTML5 History API和服务器配置*/
  mode: 'history',
  base: __dirname,
  routes: [
    /*将页面组件与path指定的路由关联，属性name是命名路由功能，
      可以在路由配置中使用JSON格式引用，避免路径在各个vue组件内硬编码导致修改黑洞*/
    {name: 'Home', path: '/home', component: Home},
    {name: 'Categories', path: '/categories', component: Explorer},
    {name: 'Cart', path: '/cart', component: Cart},
    {name: 'Me', path: '/me', component: Me},
    /*动态路由：使用路径作为参数传递的写法*/
    {name: 'bookDetails', path: '/books/:id', component: BookDetails}
  ]
});