/*导入Vue框架*/
import Vue from 'vue';
/*导入本地app.vue文件*/
import App from './app.vue';
/*加载前端路由组件*/
import VueRouter from 'vue-router';

/*不使用vue单文件的写法*/
/*document.getElementById('app').innerHTML = 'Hello, webpack.';*/

/*使用Vue.use()加载前端路由插件*/
Vue.use(VueRouter);

/*const类似java里面的final声明*/
const Routers = [
  {
    path: '/index',
    /*在vue-router导航钩子函数beforeEach中设置如下的标题*/
    meta: {
      title: '首页'
    },
    /* =>这种写法是异步懒加载，仅在请求到该页时进行加载，
       如果需要首页加载时一次性加载，写法为 component: require('./views/index.vue')*/
    component: (resolve) => require(['./views/index.vue'], resolve)
  },
  {
    path: '/about',
    meta: {
      title: '关于'
    },
    component: (resolve) => require(['./views/about.vue'], resolve)
  },
  {
    path: '/user/:id',
    meta: {
      title: '个人主页'
    },
    component: (resolve) => require(['./views/user.vue'], resolve)
  },
  /*最后一项，当访问的路径不存在时，重定向到首页*/
  {
    path: '*',
    redirect: '/index'
  }
];

const RouterConfig = {
  /*使用HTML5的History路由模式，即使用/设置路径，否则会使用#来设置路径*/
  mode: 'history',
  routes: Routers
};
const router = new VueRouter(RouterConfig);

/*通过页面跳转的钩子函数修改每个跳转页的标题*/
router.beforeEach((to, from, next) => {
  window.document.title = to.meta.title;
  next();
});

/*在跳转新页面时，将滚动条返回到页首*/
router.afterEach((to, from, next) => {
  window.scrollTo(0, 0);
});

/*创建Vue根实例*/
new Vue({
  el: '#app',
  router: router,
  render: h => {
    return h(App)
  }
});