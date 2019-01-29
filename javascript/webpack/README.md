npm init  // 初始化项目
/*-i是install的简写，-D是--save-dev的简写，安装模块并保存到package.json的devDependencies*/
npm i -D webpack  // 安装最新稳定版
npm i -D webpack@<version>  // 安装指定版本
npm i -D webpack@beta  // 安装最新体验版 
npm i -g webpack  // 安装稳定版本到全局

按照基本要求配置玩html，js，webpack.config.js文件后，在根目录下执行webpack命令运行构建


// webpack是一般打包命令，多用于构建
webpack --config webpack.config.js  --watch  //  --config 指定配置文件  --watch 开启监听模式，监听本地文件的变化并实时构建刷新

// webpack-dev-server是一般开发模式命令，默认开启 --watch
// --hot 开启模块热替换，相对于默认的刷新机制提供更快的响应 
// --devtool source-map 开启Source Map用于保持开发运行界面代码格式一致，便于断点调试
webpack-dev-server --open --hot  --devtool source-map  --history-api-fallback --config webpack.config.js --host 127.0.0.1 --port 8080




## webpack.config.js
Webpack在执行构建时默认从项目根目录下webpack.config.js文件中读取配置，文件名是在package.json里的scripts标签下根据对应命令指定的