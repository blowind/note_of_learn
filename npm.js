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


// 查看包的镜像源
npm get registry
// 结果：https://registry.npmjs.org/

// 设置新的镜像源
npm config set registry http://registry.npm.taobao.org/
// 恢复默认镜像源
npm config set registry https://registry.npmjs.org/