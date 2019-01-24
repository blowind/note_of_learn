文件/目录说明：

## index.html
开发用主文件，坐落<div id="app">的地方

## index_prod.html
生产打包自动生成的主文件，用于部署到后台加载的主html

## package.json
项目文件所在地，使用npm init自动生成的配置文件
配置scripts中的dev项用于热更新调试，build项用于生产发布命令配置
其中dev中的 --history-api-fallback 标签针对使用前端路由vue-router时将所有路由都指向index.html，否则开发调试时会报404错误

## vue.config.js
webpack开发配置文件

## prod.config.js
webpack生产打包配置文件

## .babelrc文件
webpack依赖此配置文件来使用babel编译ES6代码

## src
源代码所在目录

## src/main.js
主app文件所在

