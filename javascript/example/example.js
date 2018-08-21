

/***********************  基本依赖安装   ***********************/

npm install --save express
npm install --save express-handlebars   // 安装handlebars视图模板，当前最新版本为4.0.11



/***********************  示例   ***********************/

var express = require('express');
var app = express();

// 引用本地lib目录下自己编写的模块文件fortune.js
var fortune = require('./lib/fortune.js');

//  使用handlebars模板引擎方法一
var handlebars = require('express-handlebars').create({defaultLayout: 'main'});
app.engine('handlebars', handlebars.engine);

//  使用handlebars模板引擎方法二
// var exphbs = require('express-handlebars');
// app.engine('handlebars', exphbs({defaultLayout: 'main'}));

// 对Express的视图引擎进行配置，将其作为默认的视图引擎
app.set('view engine', 'handlebars');

// 禁用Express的X-Powered-By头信息
app.disable('x-powered-by');


// 指定静态文件所在目录，__dirname表示脚本文件所在目录
app.use(express.static(__dirname + '/public'));

app.set('port', process.env.PORT || 3000);

// 添加路由的基本方法，返回默认状态码为200，因此可以省略不填
// app.get('/', function(req, res) {
// 	//  设置响应头
// 	res.type('text/plain');
// 	res.send("Meadowlark Travel");
// });
// app.get('/about', function(req, res) {
// 	res.type('text/plain');
// 	res.send('About Meadowlark Travel');
// });
// use是一种添加中间件的方法，use一定要放在get之后，即中间件放在路由之后，否则会屏蔽路由
// Express根据回调函数中参数的个数区分404和500
// app.use(function(req, res) {
// 	res.type('text/plain');
// 	res.status(404);
// 	res.send('404 - Not Found');
// });
// app.use(function(err, req, res, next) {
// 	console.error(err.stack);
// 	res.type('text/plain');
// 	res.status(500);
// 	res.send('500 - Server Error');
// });



app.get('/', function(req, res) {
	res.render('home');
});
app.get('/about', function(req, res) {
	// 随机的传递值到前台显示，此处使用本地编写的模块中的函数
	res.render('about', {fortune: fortune.getFortune()});
});
// 没有布局的视图渲染，即/views/no-layout.handlebars不存在
app.get('/no-layout', function(req, res) {
	res.render('no-layout', {layout:null});
});
// 使用定制的布局渲染视图
app.get('/custom-layout', function(req, res) {
	res.render('custom-layout', {layout:'custom'});
});
app.get('/test', function(req, res) {
	res.type('text/plain');
	res.send("this is a test");
});


app.get('/header', function(req, res) {
	printReq(req, res);
	res.set("Content-Type", 'text/plain');
	var s = '';
	for(var name in req.headers) {
		s += name + ': ' + req.headers[name] + '\n';
	}
	res.send(s);
});



var tours = [
	{ id: 0, name: 'Hood River', price: 99.99 },
	{ id: 1, name: 'Oregon Coast', price: 149.95 },
];

app.get('/api/tours', function(req, res) {
	var toursXml = '<?xml version="1.0"?><tours>' + 
						tours.map(function(p) {
							return '<tour price="' + p.price +
									'" id="' + p.id + '">' + p.name + '</tour>';
						}).join('') + '</tours>';

	var toursText = tours.map(function(p){
		return p.id + ': ' + p.name + ' (' + p.price + ')';
	}).join('\n');

	res.format({
		'application/json': function() {
			res.json(tours);
		},
		'text/plain': function() {
			res.type('text/plain').send(toursXml);
		},
	});
});
app.put('/api/tour/:id', function(req, res) {
	var p = tours.some(function(p) { return p.id == req.params.id; });

	if(p) {
		if(req.query.name) {  
			p.name = req.query.name;
			console.log(req.query.name); 
		}
		if(req.query.price)  { 
			p.name = req.query.price;
			console.log(req.query.price);
		}
		res.json({success: true});
	} else {
		res.json({error: 'No such tour exists.'});
	}
});




// use是一种添加中间件的方法，use一定要放在get之后，即中间件放在路由之后，否则会屏蔽路由
// Express根据回调函数中参数的个数区分404和500
app.use(function(req, res) {
	res.status(404).render('404');
});
app.use(function(err, req, res, next) {
	console.error(err.stack);
	res.status(500).render('500');
});



app.listen(app.get('port'), function() {
	console.log("Express started on http://localhost:" + app.get('port') + "; press Ctrl-C to terminate");
});



express中request对象的常用属性和方法

req.params        一个数组，包含命名过的路由参数
req.param(name)   返回命名的路由参数，或者get/post请求参数，一般不适用
req.query         以键值对存放查询字符串参数（GET请求参数）
req.body          包含POST请求参数
req.route         当前匹配路由的信息，用于路由调试
req.cookies/req.signedCookies   包含从客户端传递过来的cookies值
req.headers         接收的请求头信息
req.accepts([types])     用来确定客户端是否接收一个指定的类型，如application/json
req.ip            客户端ip
req.path          请求路径
req.host          返回客户端主机名，可以伪造，意义不大
req.xhr           如果请求由ajax发起，返回true
req.protocol      标识请求的协议是http还是https
req.secure        等价于 req.protocol==='https'
req.url/req.originalUrl  返回路径和查询字符串
req.acceptedLanguages   返回客户端首选的一组语言

express中response对象的常用属性和方法
res.status(code)  设置HTTP状态码，一般是404或500
res.set(name, value)  设置响应头，一般不用手动设置
res.cookie(name, value, [options])  设置客户端cookies
res.clearCookie(name, [options])    清除客户端cookies
res.redirect(status, url)           默认重定向代码是302，其他301/303/307也可用
res.send([status], body)       状态码可选，默认内容类型是text/html，可以使用res.set()修改为text/plain，body是对象或者数组时，以JSON发送
res.json([status], json)   发送JSON及可选状态码
res.jsonp([status], json)  发送JSONP及可选状态码
res.type(type)             设置ContentType的便捷方法
res.format(object)         根据请求报头发送不同内容  res.format({'text/plain':'hithere','text/html':'<b>hi there</b>'})
res.attachment([filename])      将响应报头Content-Disposition设为attachment，让浏览器下载而不是展现内容
res.download(path, [filename], [callback])   同上
res.sendFile(path, [option], [callback])  根据路径读取指定文件并将内容发送到客户端。根据条件在相同URL下发送不同内容时比较有用
res.link(links)   设置链接响应报头，几乎无用
res.locals           一个对象，包含用于渲染视图的默认上下文
res.render(view, [locals], callback)   使用配置的模板引擎渲染视图