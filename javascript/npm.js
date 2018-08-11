npm的基本安装流程：

1、安装nvm（当前最新 v0.33.11）
	curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.11/install.sh | bash

2、安装npm 8（当前最新稳定版本 v8.11.3）
	nvm install 8

	若有老版本（例如npm 7，可以使用nvm uninstall 7卸载）

3、查看安装版本
	$ node --version
	v8.11.3
	$ npm --version
	5.6.0
	$ nvm --version
	0.33.11

npm install -g 模块名称      //  全局安装
npm install 模块名称         //  本地安装


npm install 模块：           //  安装好后不写入package.json中
npm install 模块 --save      //  安装好后写入package.json的dependencies中（生产环境依赖）
npm install 模块 --save-dev  // 安装好后写入package.json的devDepencies中（开发环境依赖）


npm rebuild bcrypt --update-binary  // 更新bcrypt模块的二进制文件


// 初始化一个包并生成package.json文件
npm init


// 创建维护账号
npm aduser

// 发布包
npm publish
// 取消发布
npm unpublish


// 创建全局连接，在本地目录的node_modules子目录中创建一个纸箱全局安装包的符号链接
npm link express  
// 得到 ./node_modules/express -> /usr/lib/node_modules/express

// 将本地的包链接到全局
npm link


// 查看包的镜像源
npm get registry
// 结果：https://registry.npmjs.org/

// 设置新的镜像源
npm config set registry http://registry.npm.taobao.org/
// 恢复默认镜像源
npm config set registry https://registry.npmjs.org/

// 指定从安装源http://registry.url下载安装包underscore
npm install underscore --registry=http://registry.url



/************************          安装包说明         ************************/
【supervisor】
npm install -g supervisor        // 安装代码变更监控包，在运行的js源代码文件发生变更后重新加载
$ supervisor app.js               // 使用方法

【node-inspector】
npm install -g node-inspector    //  安装node脚本网页调试器
// 使用方法
$ node --debug-brk=5858 debug.js   // 连接要debug.js脚本的调试服务器
$ node-inspector          // 启动node-inspector

在浏览器中打开 http://127.0.0.1:8080/debug?port=5858


【express】
npm install express --save     // 在当前项目目录下安装express框架

【node-gyp】
npm install -g node-gyp  //  安装编写Node的C/C++扩展模块的构建工具node-gyp