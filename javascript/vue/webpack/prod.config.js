var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
/*抽取散落的css生成单独css文件的插件*/
var ExtractTextPlugin = require('extract-text-webpack-plugin');
/*用户合并两个webpack的配置文件*/
var merge = require('webpack-merge');
/*Vue-loader在15.*之后的版本vue-loader的使用都是需要伴生VueLoaderPlugin的*/
const VueLoaderPlugin = require('vue-loader/lib/plugin');
var webpackBaseConfig = require('./vue.config.js');


/*清空基本配置的插件列表，因为测试环境和生成环境使用插件要达成的结果可能不一致
  比如此处使用ExtractTextPlugin提取后生成的css文件的名字要加hash值*/
webpackBaseConfig.plugins = [];

/*在vue.config.js的配置基础上扩展*/
module.exports = merge(webpackBaseConfig, {
  mode: 'production',
  output: {
    /*指定输出文件目录*/
    publicPath: './dist/',
    /*将入口文件重命名为带有20位hash值的唯一文件，为了保证上线后用户及时清除缓存下载最新文件*/
    filename: '[name].[hash].js'
  },
  plugins: [
    new ExtractTextPlugin({
      /*提取css并重命名为带有20位hash值的唯一文件*/
      filename: '[name].[hash].css',
      /*对于前端路由生成的各个js chunk页面，提取打包进main.css，
      不配置下面这项时会在各个页面里动态创建<style>标签的形式写入，不能把css做cdn*/
      allChunks: true
    }),
    /*定义当前node环境为生产环境*/
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
    /*提取模板并保存入口html文件*/
    new HtmlWebpackPlugin({
      /*通过template选项读取指定的模板ejs文件，输出到下面指定的文件*/
      filename: '../index_prod.html',
      template: './index.ejs',
      inject: false
    }),
    new VueLoaderPlugin()
  ]
});