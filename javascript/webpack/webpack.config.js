const path = require('path');
const webpack = require('webpack');
/*抽取散落的css生成单独css文件的插件*/
var ExtractTextPlugin = require('extract-text-webpack-plugin');

/* 通过CommonJS规范导出一个描述如何构建的Object对象 */
module.exports = {

	/*指定当前的编译模式，有development和production两种常用选项，或者使用none什么都不选*/
	"mode": "development",

	/* 模块入口文件，字符串写法，只有一个入口，只会生成一个Chunk */
	entry: './main.js',

	/*数组写法的入口文件，只有一个入口，入口有两个文件，只会生成一个Chunk*/
	/*entry: ['./main2.js', 'main3.js']*/

	/*使用对象的写法，配置多个入口生成多个Chunk，每个入口生成一个Chunk，Chunk名是对象中的键*/
	/*entry: {
		a: './main2.js',
		b: ['./main3.js', './main4.js']
	}*/

	/*配置成函数动态的生成入口对象，可以指定某个目录为Chunk专用目录，通过函数动态实现读取目录里文件最后单独编译输出成各个Chunk打包结果*/
	/*entry: () => {
		return new Promise((resolve) => {
			resolve({
				a: './main2.js',
				b: './main3.js'
			});
		});
	},*/


	/* 配置如何输出最终想要的代码 */
	output: {
		/*将所有依赖的模块合并输出到一个bundle.js文件中*/
		filename: 'bundle.js',

		/*多个输出文件的写法，根据Chunk名映射，用于浏览器长时间缓存文件
			hash是Chunk唯一标识的Hash值，chunkhash是内容的hash值*/
		// filename: '[name].js',
		// filename: '[name]_[chunkhash:20].js',
		// filename: '[name]_[hash:8].js',


		/* 配置输出文件存在本地的目录，必须是绝对路径*/
		path: path.resolve(__dirname, './dist'),

		/* 配置输出文件为异步加载的cdn文件等，例如如下配置最终生成的HTML引入的样子为 
		   <script src='https://cdn.example.com/assets/bundle.js'</script> */
		/*publicPath: 'https://cdn.example.com/assets/',*/

		/*放到指定目录下*/
		/*publicPath: '/assets',*/

		/*构建一个可以被其他模块导入的库 libraryTarget配置以何种方式导出  library配置导出库的名称]
			libraryTarget有             引入的文件中使用方法
			1、var  默认选择               LibraryName.doSth();
			2、commonjs                   require('library-name-in-npm')['LibraryName'].doSth();
			3、commonjs2                  require('library-name-in-npm').doSth();
			4、this                       this.LibraryName.doSth();
			5、window                     window.LibraryName.doSth();
			6、global                     global.LibraryName.doSth();
			六种配置，其中配置为commonjs2时library配置无意义	*/
		/*
		library: 'LibraryName',
		libraryTarget: "commonjs",
		*/
	
		/*是否包含有用的文件路径信息到生成的代码里*/
		pathinfo: true,

		/*附加Chunk的文件名称*/
		/* 
		chunkFilename: '[id].js',
		chunkFilename: '[chunkhash].js',
		*/
	
		/*JSONP异步加载资源时的回调函数名称，需要和服务端搭配使用*/
		/*jsonpFunction: 'myWebpackJsonp',*/

		/*生成的Source Map文件的名称*/
		sourceMapFilename: '[file].map',

		/*浏览器开发者工具里显示的源码模块名称*/
		/*devtoolModuleFilenameTemplate: 'webpack:///[resource-path]',*/

		/*异步加载跨域的资源时使用的方式*/
		/* 
		crossOriginLoading: 'use-credentials',
		corssOriginLoading: 'anonymous',
		crossOriginLoading: false,
		*/
	},

	/* 配置处理模块的规则 */
	module: {
		/* 配置webpack打包过程中读取和解析规则，通常用来配置Loader，大致通过以下方式配置
		   1、条件匹配：通过test、include、exclude三个配置项来选中Loader要应用的规则文件
		   2、应用规则：对选中的文件通过use配置项来应用Loader，Loader应用顺序为从后向前
		   3、重置顺序：通过enforce选项将某个Loader执行顺序放到最前或者最后 */
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
			},

			{
				/*匹配JS文件*/
				test: /\.js$/,
				/*使用babel-loader转换js文件， ?cacheDirectory表示传给babel-loader的参数，用于缓存babel的编译结果，加快重新编译速度*/
				// use: ['babel-loader?cacheDirectory'],

				/*传入多个参数进行配置的写法，使用对象*/
				use: [
					{
						loader: 'babel-loader',
						options: {
							cacheDirectory: true,
						}
					},
				],

				/*只命中本目录下的js文件，加快webpack搜索速度*/
				include: [
					path.resolve(__dirname),
					path.resolve(__dirname, 'src'),
				],

				/*细粒度的配置那些模块语法被解析，哪些不被解析，默认都是启用的，这边可以配置false达到禁用的效果*/
				parser: {
					amd: true, // 启用AMD
					commonjs: true,  // 启用CommonJS
					system: true,  // 启用SystemJS
					harmony: true,  // 启用ES6 import/export
					requireInclude: true,  // 启用 require.include
					requireEnsure: true,  // 启用 require.ensure
					requireContext: true,  // 启用 require.context
					browserify: true,  // 启用 browserify
					requireJs: true,   // 启用 requirejs
				}
			},

			{
				/*匹配SCSS文件*/
				test: /\.scss$/,
				/*使用一组Loader去处理scss文件，处理顺序从后到前*/
				use: ['style-loader', 'css-loader', 'sass-loader'],
				/*排除node_modules目录下的文件*/
				exclude: [
					path.resolve(__dirname, 'node_modules'),
					path.resolve(__dirname, 'anther_one'),
				]
			},

			{
				/*对非文本文件采用file-loader加载*/
				test: /\.(gif|png|jpe?g|eot|woff|ttf|svg|pdf)$/,
				use: ['file-loader'],
			}
		],

		/*让webpack忽略部分没有采用模块化的文件的递归解析和处理，提高构建性能，像jQuery、ChartJs就没有采用模块化标准*/
		noParse: /jquery|chartjs/,
		/*  // 函数式写法，content代表一个模块的文件路径
			noParse: (content) => {
			// 返回true或者false
			return /jquery|chartjs/.test(content);
		}*/
	},

	/* 配置寻找模块的规则 */
	resolve: {
		/*通过别名将原导入路径映射成新的导入路径，
		如文件中的 import Button form 'components/button' 替换成 import Button form './src/components/button'*/
		/*alias: {
			// 所有 import
			components: './src/components'
			// 通过$缩小命中范围，只命中react结尾的导入语句
			//'react$': '/path/to/react.min.js'
		},*/

		/*引入第三方模块时，针对package.json文件里的不同入口文件，指定引用优先级，不设置时默认值如下*/
		mainFields: ['browser', 'main'],
		/*在导入语句没带文件后缀时，用以匹配文件时自动追加的后缀，默认值如下*/
		extensions: ['.js', '.json'],
		/*配置webpack去哪些目录下寻找第三方模块，配置后可以将诸如 import '../../components/button' 的写法简写成 import 'button' 默认值如下*/
		modules: ['node_modules'],
		/*配置描述第三方模块的文件名称，默认值如下*/
		descriptionFiles: ['package.json'],
		/*要求import所有导入语句都必须带诸如js遮掩的文件后缀*/
		enforceExtension: false,
		/*效果同上，但只对node_modules下的模块生效，多配合enforceExtension为true的情况下设置为false以避免第三方模块不符合要求告警太多*/
		enforceModuleExtension: false,
	},


	/* 配置webpack打包过程中依赖的插件 */
	plugins: [
		/*指定提取后的css文件的名字*/
    	new ExtractTextPlugin({
    		filename: `[name]_[md5:contenthash:hex:20].css`,
    	}),

    	/*配合 --hot --inline 两个标签的插件*/
    	new webpack.HotModuleReplacementPlugin(),
	],

	/*配置开发服务器，只有通过webpack-dev-server启动时才有意义，webpack本身无法识别devServer配置项*/
	devServer: {
		/*代理到后端服务接口*/
		/*proxy: {
			'/api': 'http://localhost:8080',
		},*/

		/*开启模块热替换*/
		hot: true,
		/*是否开启代理客户端自动注入，不开启时通过iframe方式运行要开发的网页，默认开启*/
		inline: true,
		/*方便开发使用了HTML5 history API的单页应用，这类应用要求服务器在针对任何命中的路由都返回一个对应的HTML文件，只能用于只有一个HTML文件的应用*/
		historyApiFallback: true,

		/*由多个单页应用组成时的写法，使用正则匹配命中路由 */
		/*
		historyApiFallback: {
			rewrites: [
				{ from: /^\/user/, to: '/user.html' },
				{ from: /^\/game/, to: '/game.html' },
				{ from: /./, to: '/index.html' },
			]
		}
		*/

		/*配置DevServer HTTP服务器的文件根目录，默认为当前的执行目录即项目根目录*/
		/*contentBase: path.join(__dirname, 'public'),*/

		/*在HTTP相应中注入一些HTTP响应头*/
		headers: {
			'X-foo': 'bar'
		},

		/*配置白名单列表，在列表里的HTTP请求才会正常返回*/
		/*allowdHosts: [
			'host.com',
			'sub.host.com',
			'.host2.com'
		],*/

		/*开启https，DevServer会自动生成一份HTTPS证书*/
		https: false,
		/*使用自己的HTTPS证书的方法*/
		/*https: {
			key: fs.readFileSync('path/to/server.key'),
			cert: fs.readFileSync('path/to/server.crt'),
			ca: fs.readFileSync('path/to/ca.pem')
		}*/

		/*浏览器开发者工具控制台的日志输出等级，有none，error，warning，info，默认为info*/
		clientLogLevel: 'info',

		/*是否启用Gzip压缩*/
		compress: false,

		/*在DevServer启动且第一次构建完时，使用默认浏览器打开要开发的网页 通过openPage配置打开指定URL的网页*/
		open: true,
	},

	/*配置根目录，一般不配置，默认为执行启动Webpack所在当前目录，该值必须配成绝对路径字符串，也可以在webpack命令中用--context来设置*/
	context: path.resolve(__dirname),

	/*配置source-map类型*/
	devtool: 'source-map',

	/*配置输出代码的运行环境，可选项： 
	web(默认), 浏览器
	webworker, WebWorker
	node,    Node.js 使用require语句加载Chunk代码 
	async-node,  Node.js  异步加载Chunk代码
	node-webkit,   nw.js
	electron-main,   electron主线程
	electron-renderer,  electron渲染线程*/
	target: 'web',

	/*告诉webpack在JavaScript运行环境已经内置了哪些全局变量，不再将这些全局变量打包到代码中，而是可以直接使用*/
	externals: {
		// 将本地代码中导入语句的jquery替换成运行环境里的全局变量jQuery
		jquery: 'jQuery'
	},

	/*控制台输出日志控制*/
	stats: {
		assets: true,
		colors: true,
		errors: true,
		errorDetails: true,
		hash: true,
	},

	/*是否捕捉webpack构建的性能信息，用于分析构建性能瓶颈*/
	profile: true,

	/*是否启用缓存来提升构建速度*/
	cache: false,

	/*开启监听模式，webpack下默认关闭，devServer下默认开启*/
	watch: true,
	/*监听模式选项配置*/
	watchOptions: {
		// 不监听的文件或者文件夹，支持正则，默认为空
		ignored: /node_modules/,
		// 监听到变化后，等300ms再执行动作，防止更新太快导致编译频率太快，默认为300ms
		aggregateTimeout: 300,
		// 查询文件是否变化的轮询间隔，默认每秒1000次
		poll: 1000
	}
};