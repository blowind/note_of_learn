<template>
    <div class="book-list">
        <div class="header">
            <div class="heading">{{ heading }}</div>
            <div class="more">更多...</div>
        </div>
        <div class="book-items">
            <!--使用$emit发送自定义事件给外部组件，select是事件名称，book是参数-->
            <div class="book" v-for="book in books" tag="div" @click="$emit('select', book)">
                <div class="cover">
                    <img :src="book.img_url"/>
                </div>
                <div class="title">{{ book.title }}</div>
                <div class="authors">{{ book.authors | join }}</div>
            </div>
        </div>
    </div>
</template>

<script type="text/ecmascript-6">
    import "./booklist.less"

    export default{
        /*props用来接收外部传入的参数，外部可见，data用来在内部存储数据*/
        props: ['heading', 'books'],
        filters: {
            /*使用过滤器在多作者时将作者数组内容连成字符串*/
            join(args){
                return args.join(',')
            }
        }
    }
</script>