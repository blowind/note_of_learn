
/*全局第三方组件*/
import Vue from 'vue'

/*本地定义的组件*/
import App from './App.vue'
import router from './config/router'


/*所有全局的配置都在本文件中*/
Vue.config.productionTip = false


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
