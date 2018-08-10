/************************* 全局变量  *************************/
Node.js中全局对象是global，所有全局变量都是global的属性

1、process 全局变量用于描述当前Node.js进程状态

process.argv 命令行参数数组，第一个元素是node，第二个元素是脚本名，第三个元素开始是运行参数，参数以空格（包括\t等类空格）分隔
process.stdout 标准输出流 process.stdout.write() 函数比 console.log() 更底层

process.stdin 标准输入流，初始时暂停，使用前必须恢复流

process.stdin.resume();
process.stdin.on('data', function(data) {
	process.stdout.write('read from console: ' + data.toString());
});

process.nextTick(callback) 为时间循环设置一项任务，nodejs在下次事件循环响应时调用callback

// 针对下述场景，doSomething调用中两个高CPU消耗的函数somethingComplicated()和compute()依次执行时，
// 由于单线程原因会导致nodejs响应速度变慢，通过process.nextTick()达到减少单个事件执行时间，提高响应速度的效果
function doSomething(args, callback) {
	somethingComplicated(args);  // 高cpu使用的耗时函数
	process.nextTick(callback);  // 连续高cpu占用的费时写法  callback();
	                             // setTimeout(callback, 0) 效率低很多，不是好的写法
}

doSomething(function onEnd() {
	compute();  // 高cpu使用的耗时函数
	process.nextTick(callback);
});


process.platform
process.pid
process.execPath
process.memoryUsage()


2、console 全局变量用于提供控制台标准输出

console.log()  向标准输出流打印字符，自带换行符
console.error()  向标准错误流输出
console.trace()  想标准错误刘输出当前的调用栈


/*************************  常用工具  *************************/

///////////////    实现对象间原型继承的函数
util.inherits(constructor, superConstructor) 

var util = require('util');
function Base() {
	this.name = 'base';
	this.base = 1991;
	this.sayHello = function() {
		console.log('Hello ' + this.name);
	};
}

Base.prototype.showName = function() {
	console.log(this.name);
};

function Sub() {
	this.name = 'sub';
}

util.inherits(Sub, Base);   // 通过util.inherits继承 Sub仅仅继承了Base在原型中定义的函数

var objBase = new Base();
objBase.showName();   //  base
objBase.sayHello();   //  Hello base
console.log(objBase);  // Base { name: 'base', base: 1991, sayHello: [Function] }

var objSub = new Sub();
objSub.showName();    // sub
// objSub.sayHello();
console.log(objSub);  //  Sub { name: 'sub' }


///////////////    将任意对象转换为字符串
// showHidden为true输出更多隐藏信息
// depth表达最大递归的层数，不指定默认递归2层，指定为null无限递归直到完整遍历对象
// colors为ture会以ANSI颜色编码
util.inspect(object, [showHidden], [depth], [colors])

console.log(util.inspect(objBase, true));

util.isArray()
util.isRegExp()
util.isDate()
util.isError()

util.format()
util.debug()



/************************* 网络编程  *************************/

http.Server是http模块中的HTTP服务器对象，最上层功能的底层实现依赖，提供以下几个事件
1、request  客户请求到来时触发，提供req和res两个参数
2、connection  TCP连接建立时触发，提供一个参数socket，事件粒度大于request，因为Keep-Alive模式下一个连接有多个请求
3、close  服务器关闭时，该事件触发

var http = require('http')

http.createServer(function(req, res) {
	res.writeHead(200, {'Content-type': 'text/html'});
	res.write('<h1>Node.js</h1>');
	res.end('<p>Hello World</p>');
}).listen(3000);
console.log("HTTP server is listening at port 3000.");

等价于

var server = new http.Server();
server.on('request', function(req, res) {
	res.writeHead(200, {'Content-type': 'text/html'});
	res.write('<h1>Node.js</h1>');
	res.end('<p>Hello World</p>');
});
server.listen(3000);

============= 分割线 ===========

http.ServerRequest  HTTP请求信息，一般由http.Server的request事件发送，一般包含请求头和请求体，提供以下几个事件
1、data 当请求体数据到来时触发，提供一个chunk参数，表示接收到的数据（请求头由于较短基本是实时可以获取）
2、end  当请求体数据传输完成时触发，此后不会有数据到来
3、close 当请求结束时触发。不同于end，如果用户强制终止传输，也还是调用close

ServerRequest属性：
complete   客户端请求是否已经发送完成
httpVersion   HTTP协议版本，通常是1.0或1.1
method        HTTP请求方法，诸如GET、POST
url           请求的原始路径  如 /static/image/x.jpg
headers       HTTP请求头
trailers      HTTP请求尾
connection    当前HTTP连接套接字，为net.Socket的实例
client        client属性的别名

http.ServerResponse  HTTP请求应答，有三个重要的成员函数用来返回响应头、响应内容以及结束请求
1、response.writeHead(statusCode, [headers])  headers是类似关联数组的对象，表示响应头的每个属性值
2、response.write(data, [encoding])   发送响应内容，data是Buffer或字符串，如果是字符串要指定encoding编码
3、response.end([data], [encoding])   结束响应，必须显示调用，否则客户端永远处于等待状态


【处理GET请求中的内容】
使用 url.parse 解析请求中原始的path为一个对象，一般GET请求用此方式获取请求参数

var http = require('http');
var url = require('url');
var util = require('util');

http.createServer(function(req, res) {
	res.writeHead(200, {'Content-Type': 'text/plain'});
	res.end(util.inspect(url.parse(req.url, true)));
}).listen(3000);

对 http://127.0.0.1:3000/user?name=byvoid&email=byvoid@byvoid.com ，解析成
{	search: '?name=byvoid&email=byvoid@byvoid.com',
	query: { name: 'byvoid', email: 'byvoid@byvoid.com' },
	pathname: '/user',
	path: '/user?name=byvoid&email=byvoid@byvoid.com',
	href: '/user?name=byvoid&email=byvoid@byvoid.com'  }

【处理POST请求中的内容】此处只是原理示例，本实现有严重的效率和安全问题
var http = require('http')
var querystring = require('querystring');
var util = require('util');

http.createServer(function(req, res) {
	var post = '';
	req.on('data', function(chunk) {
		post += chunk;
	});
	req.on('end', function() {
		post = querystring.parse(post);
		res.end(util.inspect(post))
	});
}).listen(3000);


============= 分割线 ===========

HTTP客户端方法使用

http.request(options, callback)  发起HTTP请求，option类似关联数组的对象，表示请求的参数
options常用参数如下：
1、host  域名或者IP
2、port  端口
3、method  请求方法，默认GET
4、path   请求相对于根的路径，默认是/，包含QueryString即请求参数
5、headers  请求头的内容，为一个关联数组对象

http.get(options, callback)   相对request更精简的GET请求方法

var http = require('http');
var querystring = require('querystring');

var contents = querystring.stringify({
	name: 'zxf',
	email: '126@163.com',
	address: 'Hangzhou, Zhejiang',
});

var options = {
	host: 'www.bing.com',
	path: '/application/node',
	method: 'POST',
	headers: {
		'Content-type': 'application/x-www-form-urlencoded',
		'Content-Length': contents.length
	}
};

var req = http.request(options, function(res) {
	res.setEncoding('utf8');
	res.on('data', function(data) {
		console.log(data);
	});
});

req.write(contents);
req.end();     //  此处不能漏，否则客户端不会发送请求


http.get({host: 'www.bing.com'}, function(res) {
	res.setEncoding('utf8');
	res.on('data', function(data) {
		console.log(data);
	});
});


http.ClientRequest 是由 http.request 或者 http.get 返回的对象，表示一个待发送的HTTP请求
返回回调函数也可以对象生成后绑定，如下：

var req = http.get({host: 'www.bing.com'});
req.on('response', function(res) {
	res.setEncoding('utf8');
	res.on('data', function(data) {
		console.log(data);
	});
});


http.ClientRequest主要函数如下
req.write(content)
req.end();
req.on('event', [callback])
req.abort()       // 中止正在发送的请求
req.setTimeout(timeout, [callback])  设置请求超时时间，timeout单位为毫秒，超时后callback被回调
req.setNoDelay([noDelay])
req.setSocketKeepAlive([enable], [initialDelay])

http.ClientResponse 是 http.request 或者 http.get 入参中回调函数的参数，
有data, end, close三个事件，
有statusCode，httpVersion，headers，tailers等属性
res.setEncoding([encoding])
res.pause()     暂停接收数据和发送事件，方便实现下载功能
res.resume()    从暂停状态中恢复


/************************* 文件操作  *************************/

所有fs模块的函数都有加Sync结尾的同步函数，但是不建议使用

// 如果指定encoding，data是一个解析后字符串，否则以buffer形式表示的二进制数据，err表示有无错误发生，data是文件内容
fs.readFile(filename, [encoding], [callback(err, data)])
// 同步版本
fs.readFileSync(filename, [encoding])
// 写入文件
fs.writeFile(filename, data, [encoding], [callback(err)])


var fs = require('fs')

//  异步读文件并输出
fs.readFile('file.txt', 'utf-8', function(err, data) {
        if(err) {
                console.error(err);
        }else{
                console.log(data);
        }
});
console.log('end async.');

//  同步读取文件并输出
var data = fs.readFileSync('file.txt', 'utf-8');
console.log(data);
console.log('end sync.');


// path为文件的路径，flags是诸如 rwa+ 的模式标记，mode用于创建文件时指定全年，默认是0666
fs.open(path, flags, [mode], [callback(err, fd)])
// 从指定的文件描述符fd中读取数据写入buffer指向的缓冲区，offset是写入偏移量，
// length是从文件中读取的字节数，position是文件读取的起始位置，从当前位置读填null
fs.read(fd, buffer, offset, length, position, [callback(err, bytesRead, buffer)])
fs.close(fd, [callback(err)])    // 关闭文件描述符
// 写入文件描述符
fs.write(fd, buffer, offset, length, position, [callback(err, bytesWritten, buffer)])

fs.unlink(path, [callback(err)])         // 删除文件
fs.mkdir(path, [mode], [callback(err)])  // 创建目录
fs.rmdir(path, [callback(err)])          // 删除目录
fs.readdir(path, [callback(err, files)]) // 读取目录
fs.readPath(path, [callback(err, resolvedPath)]) // 获取真实路径
fs.rename(path1, path2, [callback(err)])    //  更名
fs.truncate(fd, len, [callback(err)])       //  截断

var fs = require('fs');
fs.open('content.txt', 'r', function(err, fd) {
	if(err) {
		console.error(err);
		return;
	}
	var buf = new Buffer(8);
	fs.read(fd, buf, 0, 8, null, function(err, bytesRead, buffer) {
		if(err) {
			console.error(err);
			return;
		}
		console.log('bytesRead: ' + bytesRead);
		console.log(buffer);
	})
});


可能的坑：循环读取文件时，文件索引不能直接用i，因为函数回调时循环早结束了

var fs = require('fs');
var files = ['1.txt', '2.txt', '3.txt'];
for(var i=0; i<files.length; i++) {
	// 建立一个匿名函数，将循坏迭代变量i作为参数传入
	// 由于运行时闭包的存在，匿名函数中定义的变量在它内部的函数执行完毕前都不会释放
	(function(i) {          //  此处要用function(i) 简历闭包，否则内部拿到的i都是3，即循环结束的i值
		fs.readFile(files[i], 'utf-8', function(err, contents) {
			console.log(files[i] + ': ' + contents);
		});
	})(i);
}

更一般的写法：

files.forEach(function(filename) {
	fs.readFile(filename, 'utf-8', function(err, contents) {
		console.log(filename + ': ' + contents);
	});
});


/************************* 事件  *************************/

EventEmitter.on(event, listener)  为指定事件注册监听器，接收一个字符串event和一个回调函数
EventEmitter.emit(event, [arg1], [arg2], [...])  发射event事件，传递若干可选参数到监听器的参数表
EventEmitter.once(event, listener)  为指定事件注册单次监听器，触发后立即解除该监听器
EventEmitter.removeListener(event, listener) 移除指定事件的某个监听器
EventEmitter.removeAllListeners([event])  移除所有事件的所有监听器，如果指定event，则移除指定事件的所有监听器


var EventEmitter = require('events').EventEmitter;
var event = new EventEmitter();

// event对象注册事件some_event的监听器
event.on('some_event', function() {
	console.log('some_event occured.');
});

setTimeout(function() {
	// 向event对象发送some_event事件
	event.emit('some_event');
}, 1000);


var events = require('events');
var emitter = new events.EventEmitter();

emitter.on('someEvent', function(arg1, arg2) {
	console.log('listener1', arg1, arg2);
});
emitter.on('someEvent', function(arg1, arg2) {
	console.log('listener2', arg1, arg2);
})
emitter.emit('someEvent', 'byvoid', 2018);

// 特殊事件error，类似其他语言的异常
emitter.emit('error');


/*************************  模块  *************************/

// exports本身是个空对象{}，所以要么是通过下面第一种情况给空对象加属性，要么像第二种情况使用新对象替换空对象

// 示例一
// 创建模块，用exports对象声明外部调用时的访问接口，存储下面内容到文件 module.js
var name;
exports.setName = function(thyName) {
	name = thyName;
};

exports.sayHello = function() {
	console.log('Hello ' + name);
};

// 在其他js文件中使用模块
var myModule = require('./module');
myModule.setName("nodejs");
myModule.sayHello();


// 示例二
// 把对象封装到模块中供外部使用，存储下面内容到文件 hello.js
function Hello() {
	var name;

	this.setName = function(thyName) {
		name = thyName;
	};

	this.sayHello = function() {
		console.log('Hello ' + name);
	};
};
module.exports = Hello;

// 在其他js文件中使用模块对象
var Hello = require('./hello');
hello = new Hello();   // 注意，因为从模块中拿到的是对象，所以要实例化
hello.setName('BYVoid');
hello.sayHello();


/*************************  包  *************************/

// Node.js的包是一个目录，有对应的规范
1、包含一个JSON格式的包说明文件 package.json，且该文件必须在包的顶层目录下（必须！！！）；
2、二进制文件应该在bin目录下
3、JavaScript代码应该在lib目录下；
4、文档应该在doc目录下；
5、单元测试应该在test目录下；


/*************************  调试  *************************/
$ node debug debug.js      // 以调试模式运行debug.js文件

$ node --debug[=port] script.js     // 远程调试，脚本正常执行不暂停，默认不指定端口时使用5858端口
$ node --debug-brk[=port] script.js   // 远程调试，调试器启动后会立刻暂停执行脚本

// 在一个终端中
$ node --debug-brk debug.js
// 在另一个终端中
$ node debug 127.0.0.1:5858