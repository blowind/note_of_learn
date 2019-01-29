/*通过CommonJS规范导入CSS模块，同时要导入css相关的loader*/
require('./main.css');
/*不是通过webpack.config.js文件而是在源码引用时指定处理css文件的Loader*/
/*require('style-loader!css-loader?minimize!./main.css');*/

/*通过CommonJS规范导入show函数*/
const show = require('./show.js');

show('Webpack');