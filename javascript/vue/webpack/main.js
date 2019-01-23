/*导入Vue框架*/
import Vue from 'vue';
/*导入本地app.vue文件*/
import App from './app.vue';

/*不使用vue单文件的写法*/
/*document.getElementById('app').innerHTML = 'Hello, webpack.';*/

/*创建Vue根实例*/
new Vue({
  el: '#app',
  render: h => h(App)
});