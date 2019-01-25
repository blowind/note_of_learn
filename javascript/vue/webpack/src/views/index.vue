<template>
  <div>
  <h1>首页</h1>
  <p>vuex的计数器： {{count}}</p>
  <!-- 等价 -->
  <!-- <p>vuex的计数器： {{$store.state.count}}</p> -->

  <button @click="handleIncrement">+1</button>
  <button @click="handleDecrease">-1</button>
  <button @click="handleIncrementBy5">+5</button>
  <button @click="handleIncrementByObject">+20</button>
  <button @click="handleIncrementByAction">action +1</button>
  <button @click="handleAsyncIncrement">async + 1</button>

  <hr>
  <p>list过滤的所有小于10的数据: {{list}}</p>
  <p>list过滤后元素个数：{{listCount}}</p>

  <hr>

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
      },
      handleIncrement() {
        /*调用vuex的mutations里的方法执行修改state的操作*/
        this.$store.commit('increment');
      },
      handleIncrementBy5() {
        /*传递参数给mutations里的方法*/
        this.$store.commit('incrementBy5', 5);
      },
      handleIncrementByObject() {
        /*传递对象给mutations里的方法，其中type用于指定mutations里的方法名*/
        this.$store.commit({
          type: 'incrementByObject',
          count: 20
        });
      },
      handleIncrementByAction() {
        this.$store.dispatch('incrementByAction');
      },
      handleAsyncIncrement() {
        this.$store.dispatch('asyncIncrement').then(() => {
          console.log(this.$store.state.count);
        });
      },
      handleDecrease() {
        this.$store.commit('decrease');
      }
    },
    computed: {
      count() {
        return this.$store.state.count;
      },
      list() {
        return this.$store.getters.filteredList;
      },
      listCount() {
        return this.$store.getters.listCount;
      }
    }
  }
</script>