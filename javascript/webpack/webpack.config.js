const path = require('path');
/*抽取散落的css生成单独css文件的插件*/
var ExtractTextPlugin = require('extract-text-webpack-plugin');

/* 通过CommonJS规范导出一个描述如何构建的Object对象 */
module.exports = {
	/*指定当前的编译模式，有development和production两种常用选项，或者使用none什么都不选*/
	"mode": "development",
	/*JavaScript执行入口文件*/
	entry: './main.js',
	output: {
		/*将所有依赖的模块合并输出到一个bundle.js文件中*/
		filename: 'bundle.js',
		/*将输出文件都放到dist目录下*/
		path: path.resolve(__dirname, './dist'),
	},
	module: {
		/*webpack打包过程中依赖的处理规则*/
		rules: [
			{
				/*使用正则表达式去匹配要用Loader转换的css文件，webpack打包时不能直接处理css文件*/
				test: /\.css$/,
				/*use指定的Loader执行顺序由后到前，先使用css-loader读取文件，在使用style-loader将CSS内容注入JavaScript文件
				  通过?的方式传入参数，例如此处参数minimize告诉css-loader要开启CSS压缩*/

				/*传递参数的方式一，通过?号，实际编译时minimize参数已失效*/
				/*use: ['style-loader', 'css-loader?minimize']*/

				/*传递参数的方式二，通过对象，实际编译时minimize参数已失效*/
				/*use: ['style-loader', 
				{
					loader: 'css-loader',
					options: {
						minimize: true,
					}
				}]*/

				loaders: ExtractTextPlugin.extract({
					/*指定转换.css文件需要使用的Loader*/
					use: ['css-loader'],
				}),
			}
		]
	},
	/*webpack打包过程中依赖的插件*/
	plugins: [
		/*指定提取后的css文件的名字*/
    	new ExtractTextPlugin({
    		filename: `[name]_[md5:contenthash:hex:20].css`,
    	}),
	]
};