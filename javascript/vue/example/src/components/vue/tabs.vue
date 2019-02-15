<template>
    <div class="tabs">
        <ul>
            <!-- router-link组件支持用户在具有路由功能的应用中导航，通过to属性指定目标地址
                  默认渲染后输出成 <a href="#">的超链接标签，tag="li"表示输出成<li>标签 -->
            <!-- 通过名称引用路由(命名路由)，注意此处是:to
                 如果直接使用字符串写明路由，则是to="/home" -->
            <!-- 添加 active-class="active" 可以指明当前标签被激活时改变样式为active，为避免多处重复配合在VueRouter中配置linkActiveClass属性 -->
            <!-- exact表名当前的路由要精确匹配才能激活exact样式，因为Home的路由是/，由于所有其他页面路径中都带/，如果不精确匹配则home标签会一直保持激活状态-->
            <router-link :to="{name: 'Home'}" tag="li" exact>
                <div><img src="../../assets/images/home.svg"></div>
                <div>首页</div>
            </router-link>

            <router-link :to="{ name: 'Categories' }" tag="li">
                <div>
                    <img src="../../assets/images/category.svg">
                </div>
                <div>分类</div>
            </router-link>

            <router-link :to="{ name: 'Cart' }" tag="li">
                <div><img src="../../assets/images/cart.svg"></div>
                <div>购物车</div>
            </router-link>

            <router-link :to="{ name: 'Me'}" tag="li">
                <div><img src="../../assets/images/me.svg"></div>
                <div>我</div>
            </router-link>
        </ul>
    </div>
</template>
<script>
    import "../less/tabs.less"

    export default {}
</script>