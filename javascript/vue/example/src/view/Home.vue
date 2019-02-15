<template>
  <div v-if="page_data">
    <div class="section">
      <!--使用本地进一步封装的图片轮播组件，绑定slide数组元素给slider组件-->
      <slider :slides="page_data.top"></slider>
      <!--使用本地封装的快讯组件-->
      <announcement :label="page_data.announcement.label" :content="page_data.announcement.text"></announcement>
    </div>

    <!--使用本地实现的图书列表组件-->
    <div class="section">
      <!-- 使用@select捕获booklist组件发送的自定义事件，有参数时$event是参数，无参时是一个DOM事件对象实例 -->
      <book-list :books="page_data.promotions" heading="最新更新" @select="preview($event)">
      </book-list>
    </div>

    <div class="section">
      <book-list :books="page_data.recommended" heading="编辑推荐">
      </book-list>
    </div>

    <modal-dialog ref="dialog">
      <div slot="heading"></div>
      <div>
        <div v-if="selected">
          <h2>{{ selected.title }}</h2>
        </div>
      </div>
    </modal-dialog>
  </div>
</template>

<script>
  import Slider from "../components/vue/slider.vue"
  import ModalDialog from "../components/vue/dialog.vue"
  import Announcement from "../components/vue/announcement.vue";

  import BookList from "./book/booklist.vue"

  import fakeServer from "../server/fakeServer"


  export default {
      data () {
          return {
              page_data: undefined,
              selected:undefined
          }
      },
      mounted(){
          /*加载本页的时候修改浏览器标签的title*/
          document.title = "书店首页"
      },
      created() {
          /*首页展示的数据内容，正常来说此部分数据都要用服务端获取，此处在本地模拟该数据*/
          this.page_data = fakeServer.getHomeData()
      },
      /*声明本地使用的组件，用于在template里直接作为html标签*/
      components: {Announcement, Slider, BookList, ModalDialog},
      methods: {
          preview (book) {
              this.selected = book
              this.$refs.dialog.open()
          }
      }
  }
</script>

