<template>
    <div>
        点击的是：<span v-text="id"></span>
    </div>
</template>

<script>
    export default {
        name: "BookDetail",
        data(){
            return{
                id:this.$route.params.id
            }
        },
        props:[]
    }
</script>

<style scoped>

</style>