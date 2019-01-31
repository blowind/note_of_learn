Vue src目录建议的目录结构安排：

src
|---README.md
|---assets          // 全局资源目录
|     |---images    // 图片目录
|     |---less      // less样式表目录
|     |---css       // css样式表目录
|     |---fonts     // 自定义字体文件目录
|---components      // 公共组件目录
|     |---ImageInput.vue     //  具体的公共组件
|     |---Slider.vue
|     |---...
|---directives.js   // 公共指令
|---filters.js      // 公共过滤器
|---login           // 根据场景区分的单页面
|     |---index.vue // 复杂场景单页面入口文件
|     |---LoginForm.vue  // 登陆场景私有表单组件
|     |---SocialLogin.vue  
|---cart
|     |---index.vue
|     |---ItemList.vue
|     |---CheckoutForm.vue
|---Discover.vue    // 简单场景单页面，不需要放到目录里
|---App.vue         // 程序入口文件
|---main.js