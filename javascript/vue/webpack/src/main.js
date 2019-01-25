/*导入Vue框架*/
import Vue from 'vue';
/*加载前端路由组件*/
import VueRouter from 'vue-router';
/*加载组件通信插件Vuex*/
import Vuex from 'vuex';
/*导入本地app.vue文件*/
import App from './app.vue';


/*不使用vue单文件的写法*/
/*document.getElementById('app').innerHTML = 'Hello, webpack.';*/

/*使用Vue.use()加载前端路由插件*/
Vue.use(VueRouter);
/*使用Vuex组件通信插件*/
Vue.use(Vuex);

/*******************************    路由相关配置   *******************************/

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


/*******************************    Vuex相关配置   *******************************/

const store = new Vuex.Store({
  /* state内存储数据，用于给所有组件使用
    数据保存在state字段内，此处定义计数器count并初始化为0，外部通过$store.state.count使用
    在组件内来自state的数据只能读取，不能手动修改，修改的唯一途径是使用mutations */
  state: {
    count: 0,
    list: [1, 5, 8, 10, 30, 50]
  },
  /* mutations用于提供方法给组件调用，一般用于操作state内的数据
   组件内通过this.$store.commit方法来执行mutations里定义的函数
   在mutations函数里不要异步操作数据，改变数据时用mutations，有业务逻辑代码时用actions*/
  mutations: {
    increment(state) {
      state.count++;
    },
    incrementBy5(state, n = 5) {
      state.count += n;
    },
    incrementByObject(state, params) {
      state.count += params.count;
    },
    decrease(state) {
      state.count--;
    }
  },
  /*getters用于将各组件共用的过滤state数据的功能提取出来，
    在组件内部使用this.$store.getters.filteredList调用 */
  getters: {
    filteredList: state => {
      return state.list.filter(item => item < 10);
    },
    /*调用getters内部的方法，将getters作为第二个参数传递*/
    listCount: (state, getters) => {
      return getters.filteredList.length;
    }
  },
  /*与mutation大同小异，内部提交的是mutation操作，但可以使用异步操作业务逻辑
    在组件内使用this.$store.dispatch调用
    改变数据时用mutations，有业务逻辑代码时用actions*/
  actions: {
    /*此处实现是同步的，与mutation中直接increment没有功能上的差别*/
    incrementByAction(context) {
      context.commit('increment');
    },
    asyncIncrement(context) {
      return new Promise(resolve => {
        setTimeout(() => {
          context.commit('increment');
          resolve();
        }, 1000)
      });
    }
  }
});

/*创建Vue根实例*/
new Vue({
  el: '#app',
  router: router,
  store: store,
  render: h => {
    return h(App)
  }
});