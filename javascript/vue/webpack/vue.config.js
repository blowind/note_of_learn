var path = require('path');
/*抽取散落的css生成单独css文件的插件*/
var ExtractTextPlugin = require('extract-text-webpack-plugin');
/*Vue-loader在15.*之后的版本vue-loader的使用都是需要伴生VueLoaderPlugin的*/
const VueLoaderPlugin = require('vue-loader/lib/plugin');

var config = {
  mode: 'development',
  /*配置入口，webpack从main.js开始递归解析出所有依赖的模块*/
  entry: {
    main: './src/main.js'
  },
  output: {
    /*指定打包后文件输出目录*/
    path: path.join(__dirname, './dist'),
    /*资源文件引用目录，此处可以填cdn网址*/
    publicPath: '/dist/',
    /*指定输出文件名称，将入口所依赖的所有模块打包成一个文件main.js输出*/
    filename: 'main.js'
  },
  module: {
    rules: [
      {
        /*指定匹配的文件格式，此处指vue结尾的文件*/
        test: /\.vue$/,
        loader: 'vue-loader',
        /*处理.vue文件时，会对<template><script><style>处理，通过options进一步细分*/
        options: {
          loaders: {
            css: ExtractTextPlugin.extract({
              use: 'css-loader',
              fallback: 'vue-style-loader'
            })
          }
        }
      },
      {
        test:/\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/
      },
      {
        test: /\.css$/,
        use: ExtractTextPlugin.extract({
          use: 'css-loader',
          fallback: 'style-loader'
        })
      },
      {
        test: /\.(gif|jpg|png|woff|svg|eot|ttf)\??.*$/,
        /*如果这个文件小于1kb，则以base64的形式加载，不会生成一个文件*/
        loader: 'url-loader?limit=1024'
      }
    ]
  },
  plugins: [
    /*指定提取后的css文件的名字*/
    new ExtractTextPlugin("main.css"),
    new VueLoaderPlugin()
  ]
};

/*相当于 export default config;*/
module.exports = config;