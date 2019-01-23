var path = require('path');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

var config = {
  mode: 'development',
  /*配置入库，webpack从main.js开始工作*/
  entry: {
    main: './main'
  },
  output: {
    /*指定打包后文件输出目录*/
    path: path.join(__dirname, './dist'),
    /*资源文件引用目录，此处可以填cdn网址*/
    publicPath: '/dist/',
    /*指定输出文件名称*/
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
      }
    ]
  },
  plugins: [
    /*指定提取后的css文件的名字*/
    new ExtractTextPlugin("main.css"),
    /*Vue-loader在15.*之后的版本vue-loader的使用都是需要伴生 VueLoaderPlugin的*/
    new VueLoaderPlugin()
  ]
};

/*相当于 export default config;*/
module.exports = config;