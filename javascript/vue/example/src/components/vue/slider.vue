<template>
    <!--ref是标准的HTML属性，表示引用到本对象-->
    <div class="swiper-container" ref="slider">
        <div class="swiper-wrapper">
            <div class="swiper-slide" v-for="slide in slides">
                <router-link tag="div" :to="{name: 'bookDetails', params: {id: slide.id}}">
                    <img :src="slide.img_url"/>
                </router-link>
            </div>
        </div>
        <!--如果需要分页器-->
        <div class="swiper-pagination" ref="pagination"></div>

        <div class="swiper-button-prev" ref="prev"></div>
        <div class="swiper-button-next" ref="next"></div>
    </div>
</template>

<script type="text/ecmascript-6">
    /* 引入第三方组件Swiper来实现图片轮播效果 */
    import Swiper from "swiper"
    import 'swiper/dist/css/swiper.css'

    export default {
        /*接收外部传入的参数统一用props*/
        props: ['slides'],

        /*此处不能放在created而应该放置在mounted，否则Swiper不生效，因为created调用时元素还没挂载到DOM上*/
        mounted() {
            /*Swiper在官方说明中是使用CSS选择器来指定生效的dom，但有可能匹配多个，此处使用vue的this.$refs引用来精准匹配*/
            new Swiper(this.$refs.slider, {
                /*单边无限循环拉取，否则到最前或者最后元素则不能继续*/
                loop: true,
                pagination: this.$refs.pagination,
                paginationClickable: true,
                spaceBetween: 30,
                centeredSlides: true,
                autoplay: 25,
                autoplayDisableOnInteraction: false,

                prevButton: this.$refs.prev,
                nextButton: this.$refs.next,
            })
        }
    }
</script>
<style>
    .swiper-container {
        width: 100%;
        margin: 0;
        padding: 0;
    }

    .swiper-wrapper {
        height: 200px;
    }

    .swiper-slide img {
        max-width: 100%;
    }

    .swiper-slide {
        text-align: center;
        background: #fff;
        /* Center slide text vertically */
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        display: flex;
        -webkit-box-pack: center;
        -ms-flex-pack: center;
        -webkit-justify-content: center;
        justify-content: center;
        -webkit-box-align: center;
        -ms-flex-align: center;
        -webkit-align-items: center;
        align-items: center;
    }
</style>