

/***********************  基本依赖安装   ***********************/

npm install --save express
npm install --save express-handlebars   // 安装handlebars视图模板，当前最新版本为4.0.11



/***********************  示例   ***********************/

var express = require('express');

var app = express();
var handlebars = require('express-handlebars').create({defaultLayout: 'main'});

// 创建视图引擎，并对Express进行配置，将其作为默认的视图引擎
app.engine('handlebars', handlebars.engine);
app.set('view engine', 'handlebars');

app.set('port', process.env.PORT || 3000);

// 添加路由的基本方法，返回默认状态码为200，因此可以省略不填
app.get('/', function(req, res) {
	//  设置响应头
	res.type('text/plain');
	res.send("Meadowlark Travel");
});

app.get('/', function(req, res) {
	res.render('home');
});

app.get('/about', function(req, res) {
	res.type('text/plain');
	res.send('About Meadowlark Travel');
});

app.get('/about', function(req, res) {
	res.render('about');
});

// use是一种添加中间件的方法，use一定要放在get之后，即中间件放在路由之后，否则会屏蔽路由
// Express根据回调函数中参数的个数区分404和500
app.use(function(req, res) {
	res.type('text/plain');
	res.status(404);
	res.send('404 - Not Found');
});

app.use(function(err, req, res, next) {
	console.error(err.stack);
	res.type('text/plain');
	res.status(500);
	res.send('500 - Server Error');
});

app.listen(app.get('port'), function() {
	console.log("Express started on http://localhost:" + app.get('port') + "; press Ctrl-C to terminate");
});