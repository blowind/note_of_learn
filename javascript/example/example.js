

/***********************  基本依赖安装   ***********************/

npm install --save express
npm install --save express-handlebars   // 安装handlebars视图模板，当前最新版本为4.0.11


默认情况下，Express.js 4.x 和 3.x可以使用提供给res.render方法的模板扩展，也可以使用通过view engine设置的默认扩展，
去调用模板库里的require方法和__express方法。换句话说，Express.js是在外部实例化模板引擎库的，该库需要有__express方法。
当模板引擎库不提供__express方法，也不提供有参数(path、options、callback)的类似方法时，建议你用Consolidate.js

/***********************  示例   ***********************/

var express = require('express');
var app = express();

// 启用cookie功能需要安装cookie-parser中间件
var credentials = require('./credential/credentials.js');
app.use(require('cookie-parser')(credentials.cookieSecret));
app.use(require('express-session')());     // 内存会话管理中间件

// 引用本地lib目录下自己编写的模块文件fortune.js
var fortune = require('./lib/fortune.js');
// 使用自己编写的两个中间件处理http请求
var cartValidation = require('./lib/cartValidation.js');
app.use(cartValidation.checkWaivers);
app.use(cartValidation.checkGuestCounts);

//  使用handlebars模板引擎方法一
//  此处指定默认布局是 main，即完整的文件名是 main.handlebars
//  默认情况下express在views子目录查找视图，在views/layouts目录下查找布局文件，此处查找main.handlebars
//  section即段落，在布局文件中需要放置除{{{body}}}之外的其他内容时，使用这个辅助方法执行
//  【此处需要example.js主文件，jquery-test.handlebars视图文件，main.handlebars布局文件三个文件配合，其中主文件不要忘记配置jquery-test视图路由】
var handlebars = require('express-handlebars').create({
										defaultLayout: 'main',
										helpers: {
											section: function(name, options){
												if(!this._sections) this._sections = {};
												this._sections[name] = options.fn(this);
												return null;
											}
										}            });
app.engine('handlebars', handlebars.engine);


//  使用handlebars模板引擎方法二
// var exphbs = require('express-handlebars');
// app.engine('handlebars', exphbs({defaultLayout: 'main'}));


// 对Express进行配置，将其作为默认的视图引擎
// 此处为配置express视图引擎的固定语句，根据生成的引擎名不同进行对应设置
app.set('view engine', 'handlebars');

// 启用模板缓存，可以提高性能，默认开发模式下禁用，生产模式下启用
app.set('view cache', true);

// 禁用Express的X-Powered-By头信息
app.disable('x-powered-by');


// 指定静态文件所在目录，__dirname表示脚本文件所在目录
app.use(express.static(__dirname + '/public'));

app.set('port', process.env.PORT || 3000);


// 不使用模板的添加路由的基本方法，返回默认状态码为200，因此可以省略不填
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


【【【【【【【【【【【【【【【【使用段落，在布局文件中使用】】】】】】】】】】】】】】】】】】】
app.get('/jquery-test', function(req, res){
	res.render('jquery-test');
});



！！！！！！！！！ 中间件一定要放在路径路由回调之前，否则不生效


【【【【【【【【【【【【【【【【局部文件的使用】】】】】】】】】】】】】】】】】】】

// 添加中间件，给res.locals.partials添加数据，res.locals对于任何视图可用，
// 因此partials.abc可以在所有视图中作为上下文使用
// 在视图中使用 {{> weather}} 来包含局部文件，
// express-handlebars会在views/partials目录下查找weather.handlebars文件
// 如果局部文件太多可以分目录存储，views/partials/social/facebook.handlebars文件通过{{> social/facebook}}方式引入视图
// 【此处功能需要修改 example.js主文件，home.handlebars视图文件，添加weather.handlebars局部文件】
function getWeatherData() {
	return {
	locations: [
			{
				name: 'Portland',
				forecastUrl: 'http://www.wunderground.com/US/OR/Portland.html',
				iconUrl: 'http://icons-ak.wxug.com/i/c/k/cloudy.gif',
				weather: 'Overcast',
				temp: '54.1 F (12.3 C)',
			},
			{
				name: 'Bend',
				forecastUrl: 'http://www.wunderground.com/US/OR/Bend.html',
				iconUrl: 'http://icons-ak.wxug.com/i/c/k/partlycloudy.gif',
				weather: 'Partly Cloudy',
				temp: '55.0 F (12.8 C)',
			},
			{
				name: 'Manzanita',
				forecastUrl: 'http://www.wunderground.com/US/OR/Manzanita.html',
				iconUrl: 'http://icons-ak.wxug.com/i/c/k/rain.gif',
				weather: 'Light Rain',
				temp: '55.0 F (12.8 C)',
			},
		],
	};
}
app.use(function(req, res, next) {
	if(!res.locals.partials) {
		res.locals.partials = {};
	}
	res.locals.partials.abc = getWeatherData();
	next();
});
app.get('/', function(req, res) {
	// 如果不指定views engine，那么扩展必须显式地传递，即此处要写为home.handlebars
	res.render('home');
	// 设置cookie，需要引入中间件和密钥文件
	// 此处不带第三个参数，设置未签名cookie
	res.cookie('cookie_plaintext', 'cookie info');
	// 带第三个参数，设置签名cookie
	res.cookie('cookie_ciphertext', 'cookie in plaintext', {signed: true});

	req.session.userName = "myName";
});
app.get('/about', function(req, res) {
	var plaintext = req.cookies.cookie_plaintext;
	var cipher = req.signedCookies.cookie_ciphertext;
	console.log("明文cookie: " + plaintext);
	console.log("密文cookie: " + cipher);

	// 清除明文cookie，清除密文cookie同理
	res.clearCookie('cookie_plaintext');

	// 随机的传递值到前台显示，此处使用本地编写的模块中的函数
	res.render('about', {fortune: fortune.getFortune()});
});

cookie的操作，一般是在response上设置cookie项:
res.cookie("variableName", variableValue, [optionObject]);

在request上取出cookie项:
req.cookies.variableName;
req.signedCookies.variableName;


设置cookie时可以添加的选项
domain   控制跟cookie相关的域名，不能设置跟服务器所用域名不同的域名，强行设置不生效
path     控制应用这个cookie的路径，隐含的通配后面的路径，/会应用到网站所有页面
maxAge   指定客户端保存cookie多长时间，单位毫秒。省略则在浏览器关闭时删除cookie
secure   指定本cookie只通过安全（HTTPS）连接发送
httpOnly 表明本cookie只能由服务器修改，可用于防范XSS攻击
signed   设为true会对这个cookie签名，访问时需要使用 res.signedCookies而不是res.cookies


会话session配置对象的选项
key     存放唯一会话标识的cookie名称，默认为 connect.sid
store   会话存储的实例，默认为一个MemoryStore的实例
cookie  会话cookie 的设置(path, domain, secure等)

对于会话，不是在请求对象获取值，在响应对象设置值，而是全部在请求对象上操作（响应对象上没有session属性）
delete req.session.userName;   //  删除会话


【【【【【【【【【【【【【【【【基本单页面的渲染】】】】】】】】】】】】】】】】】】】

app.get('/handelbars', function(req, res) {
	res.render('hbs', {
		currency: {
			name: 'United States dollar',
			abbrev: 'USD',
		},
		tours: [
			{ name: 'Hood River', price: '$99.95'},
			{ name: 'Oregon Coast', price: '$159.95'},
		],
		specialUrl: '/january-specials',
		currencies: [ 'USD', 'GBP', 'BTC'],
	});
});
// 没有布局的视图渲染，即/views/no-layout.handlebars不存在
app.get('/no-layout', function(req, res) {
	res.render('no-layout', {layout:null});
});
// 使用views/layouts/microsite.handlebars文件作为布局来渲染
app.get('/foo', function(req, res) {
	res.render('foo', {layout: 'microsite'});
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

【【【【【【【【【【【【【【【【带参数的get请求处理】】】】】】】】】】】】】】】】】】】】

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
//  get请求中的参数都在req.query中
app.get('/api/tour/:id', function(req, res) {
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


【【【【【【【【【【【【【【【【post请求处理】】】】】】】】】】】】】】】】】】】】

// 需要先安装body-parser包，引入后req.body对象可用
// app.use(require('body-parser')());
app.use(express.urlencoded({extended: true}));
app.use(express.json());


app.use(function(req, res, next) {
	// 如果有即显消息，传到上下文中，然后清除，保证即显消息只生效一次
	res.locals.flash = req.session.flash;
	delete req.session.flash;
	next();
});

// 此处模拟数据存储到数据库的过程
function NewsletterSignup() {
}
NewsletterSignup.prototype.save = function(cb) {
	cb();
};
// 邮箱正则匹配
var VALID_EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)+$/;
// 处理form表单的post请求
// 此处获取表单页面并填入必要的字段（此处是隐藏字段csrf）
app.get('/newsletter', function(req, res) {
	res.render('newsletter', {csrf: 'CSRF token goes here'});
});
// 此处处理表单页面submit的post请求
app.post('/process', function(req, res) {
	console.log('Form (from querystring): ' + req.query.form);
	console.log('CSRF token (from hidden form field): ' + req.body._csrf);
	console.log('Name (from visible form field): ' + req.body.name);
	console.log('Email (from visible form field): ' + req.body.email);

	var name = req.body.name || '', email = req.body.email || '';
	if(!email.match(VALID_EMAIL_REGEX)) {
		if(req.xhr) return res.json({error: 'Invalid name email addresss.'});
		req.session.flash = {
			type: 'danger',
			intro: 'Validation error!',
			message: 'The email address you entered was not valid.',
		};
		return res.redirect(303, '/archive');
	}
	new NewsletterSignup({name: name, email: email}).save(function(err) {
		if(err) {
			if(req.xhr) return res.json({error: 'Database error.'});
			req.session.flash = {
				type: 'danger',
				intro: 'Database error!',
				message: 'There was a database error; please try again later.',
			}
			return res.redirect(303, '/archive');
		}
		if(req.xhr) return res.json({success: true});
		req.session.flash = {
			type: 'success',
			intro: 'Thank you!',
			message: 'You have now been signed up for the newsletter.',
		};
		return res.redirect(303, '/archive');
	});
	// res.redirect(303, '/thank-you');
});
// 此处处理post请求处理后的页面跳转
app.get('/archive', function(req, res) {
        res.render('archive');
});
app.get('/thank-you', function(req, res){
    res.render('thank-you');
});


app.post('/processAjax', function(req, res){
	// XHR是XML HTTP Request的简写，ajax请求依赖于XHR，所以ajax请求时xhr是true
	//  req.accepts确认最合适的响应类型，根据Accepts HTTP头信息判断，此处询问最佳返回格式是json还是html
	if(req.xhr || req.accepts('json,html')==='json'){
		// 如果发生错误，应该发送 { error: 'error description' }
		res.send({ success: true });
	} else {
		// 如果发生错误，应该重定向到错误页面
		res.redirect(303, '/thank-you');
	}
});


var formidable = require('formidable');
app.get('/vacation-photo', function(req, res) {
	var now = new Date();
	res.render('vacation-photo', {
		year: now.getFullYear(),
		month: now.getMonth(),
	});
});
app.post('/vacation-photo/:year/:month', function(req, res) {
	var form = new formidable.IncomingForm();
	form.parse(req, function(err, fields, files) {
		if(err) return res.redirect(303, '/error');
		console.log('received fields:');
		console.log(fields);
		console.log('received files:');
		console.log(files);
		res.redirect(303, '/thank-you');
	});
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


关于中间件：
1、路由处理器（app.get、app.post等，经常被统称为 app.VERB ）可以被看作只处理特定HTTP谓词（GET、POST等）的中间件。
2、路由处理器的第一个参数必须是路径。用 "/*" 可以让某个路由匹配所有路径
3、路由处理器和中间件的参数中都有回调函数，这个函数有 2 个、3 个或 4 个参数。如果有 2 个或 3 个参数，头两个参数是请求和响应对象，第三个参数是 next 函数。如果有 4 个参数，它就变成了错误处理中间件，第一个参数变成了错误对象，然后依次是请求、响应和 next 对象
4、如果不调用 next()，管道就会被终止，也不会再有处理器或中间件做后续处理。
5、如果调用了 next()，一般不宜再发送响应到客户端。如果你发送了，管道中后续的中间件或路由处理器还会执行，但它们发送的任何响应都会被忽略

示例：
var app = require('express')();
app.use(function(req, res, next){
	console.log('\n\nALLWAYS');
	next();
});
app.get('/a', function(req, res){
	console.log('/a: 路由终止 ');
	res.send('a');
});
app.get('/a', function(req, res){
	console.log('/a: 永远不会调用 ');
});
app.get('/b', function(req, res, next){
	console.log('/b: 路由未终止 ');
	next();
});
app.use(function(req, res, next){
	console.log('SOMETIMES');
	next();
});
app.get('/b', function(req, res, next){
	console.log('/b (part 2): 抛出错误 ' );
	throw new Error('b 失败 ');
});
app.use('/b', function(err, req, res, next){
	console.log('/b 检测到错误并传递 ');
	next(err);   // 传递了错误，最后走到500
});
app.get('/c', function(err, req){
	console.log('/c: 抛出错误 ');
	throw new Error('c 失败 ');
});
app.use('/c', function(err, req, res, next){
	console.log('/c: 检测到错误但不传递 ');
	next();     //  未传递错误，最后走到404
});
app.use(function(err, req, res, next){
	console.log(' 检测到未处理的错误 : ' + err.message);
	res.send('500 - 服务器错误 ');
});
app.use(function(req, res){
	console.log(' 未处理的路由 ');
	res.send('404 - 未找到 ');
});
app.listen(3000, function(){
	console.log(' 监听端口 3000');
});