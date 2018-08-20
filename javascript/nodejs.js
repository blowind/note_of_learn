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


process.dlopen()  // 一般加载C/C++模块(.node后缀)使用的方法
process.binding('natives')  //  从核心模块数组二进制形式数组natives中加载核心模块代码
 

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

复杂示例：
 
 var http = require('http'), fs = require('fs');
 function serveStaticFile(res, path, contentType, responseCode) {
 	if(!responseCode) responseCode = 200;
 	fs.readFile(__dirname + path, function(err, data) {  // __dirname 表示当前脚本所在的目录
 		if(err) {
 			res.writeHead(500, {'Content-type': 'text/plain'});
 			res.end('500 - Internal Error');
 		}else{
 			res.writeHead(responseCode, {'Content-type': contentType});
 			res.end(data);
 		}
 	});
 }
 http.createServer(function(req, res) {
 	var path = req.url.replace(/\/?(?:\?.*)?$/, '').toLowerCase();
 	switch(path) {
 		case '': serveStaticFile(res, '/public/home.html', 'text/html');
 				break;
 		case '/about': serveStaticFile(res, '/public/about.html', 'text/html');
 				break;
 		case '/img/logo.jpg': serveStaticFile(res, '/public/img/logo.jpg', 'image/jpeg');
 				break;
 		default: serveStaticFile(res, '/public/404.html', 'text/html', 404);
 				break;
 	}
 }).listen(3000);

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

/************************* 异步IO  *************************/

在node.js启动时，创建了一个类似while(true)的循环体，每次执行一次循环体称为一次tick，每个tick的过程就是查看是否有事件等待处理，如果有，则取出事件极其相关的回调函数并执行，然后执行下一次tick


// setTimeout和setInterval的延时异步操作是在观察者内部使用红黑树实现，不需要I/O线程池的参与
//调用setTimeout和setInterval创建的定时器会被插入到定时器观察者内部的一个红黑树中。每次Tick执行时，会从该红黑树中迭代取出定时器对象，检查是否超过定时时间，如果超过，就形成一个事件，它的回调函数立即执行。
//缺陷在于不精确，因为上一次事件循环的执行时间是未知的，可能已经超过设定时间点很久
setTimeout(function() {  // 定时调用一次
	doSomething();
}, 0);
setInterval(function() {  // 定时重复执行
	doSomething();
}, 1000);

// 每次调用process.nextTick()方法，只会将回调函数放入队列中，在下一轮Tick时取出执行。
//定时器中采用红黑树的操作时间复杂度为O(lg(n))，nextTick()的时间复杂度为O(1)
process.nextTick(function() {});

// 类似process.nextTick()，但是process.nextTick()回调函数执行的优先级高于setImmediate()
// process.nextTick()属于idle观察者，setImmediate()属于check观察者，setTimeout采用的是类似IO观察者
// 在每轮循环检查中，idle观察者先于I/O观察者，I/O观察者先于check观察者，即 idle观察者>>io观察者>check观察者
// 实现上，process.nextTick()的回调函数保存在一个数组中，setImmediate()的回调函数保存在链表中
// 运行上，process.nextTick()在每轮循环中都会将数组中的回调函数全部执行完，setImmediate()在每轮循环中执行链表中的一个回调函数
setImmediate(function() {});

process.nextTick(function() {
	console.log('nextTick延迟执行1');
});
process.nextTick(function() {
	console.log('nextTick延迟执行2');
});
setImmediate(function() {
	console.log('setImmediate延迟执行1');
	// 进入下次循环
	process.nextTick(function() {
		console.log('强势插入');
	});
});
setImmediate(function() {
	console.log('setImmediate延迟执行2');
});
console.log('正常执行');

实际执行结果： （与书上讲的不一样，要调查一下原因）
正常执行
nextTick延迟执行1
nextTick延迟执行2
setImmediate延迟执行1
setImmediate延迟执行2
强势插入


setImmediate()：当poll阶段完成后执行
setTimeout(): 当时间达到后，有机会就执行
两者执行顺序区别
1、因被调用时上下文不同而不同
2、在非I/O循环(主模块)中，顺序不固定（此处setTimeout时间要设置为0）
3、在I/O循环中setImmdiate回调总是先执行

process.nextTick()        会在eventloop继续执行前被调用
process.nextTickQueue()   在处理完当前操作后调用，而不管eventloop走到了哪个阶段
为了防止轮询阶段持续时间太长，libuv 会根据操作系统的不同设置一个轮询的上限

nextTick()在eventloop当前阶段生效，即当前操作执行完，就执行nextTick。执行后，在继续evnetLoop
setimmediat在poll阶段空闲时生效

使用nextTick的主要原因: 
1、允许处理错误，清理不需要的资源，或，在事件循环结束前再次尝试发送请求
2、让回调函数，在调用栈unwound（已清除)后，且事件循环继续前执行。


举例：
A();
B();
C();
           Event Loop
   当前执行栈  |   等待队列
   A B C          X X X X X

A();
process.nextTick(B);
C();
   当前执行栈  |   等待队列
   A C        B   X X X X 

A();
setImmediate(B);  // 或者 setTimeout(B,0);
C();
   当前执行栈  |   等待队列
   A C            X X X X B




总结：
1、尽量使用setImmediate()
2、setTimeout() 在某个时间值后尽快执行函数，精度不高，有延迟执行的可能，动用了红黑树，资源消耗大
3、setImmediate() 一旦轮询阶段完成，执行回调函数，消耗的资源小，不会造成阻塞，但是效率也是最低的
4、process.nextTick() 在当前调用栈结束后立即处理，效率最高，消费资源小，但会阻塞CPU的后序调用；




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


注意点：
1、对一个事件添加超过10个侦听器，会得到警告，可以使用emitter.setMaxListeners(0)去掉这个限制
2、EventEmitter对error事件进行特殊对待，运行期间有显示添加过侦听器真调用，否则作为异常抛出，外部无不活则引起线程退出。


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


【继承events模块】
var events = require('events');
function Stream() {
	events.EventEmitter.call(this);
}
util.inherits(Stream, events.EventEmitter);

【利用事件队列觉得雪崩问题】  即短时间大量请求导致的后端资源响应恶化累积成无相应问题
// 此处针对的是启动时第一次请求没有内存缓存，导致同一时刻SQL并发的重复查询多次引起的无意义性能问题
var proxy = new events.EventEmitter();
var status = "ready";
var select = function(callback) {
	proxy.once("selected", callback);   // 此处所有的调用都会进入事件队列，并与事件selected关联
	if(status === "ready") {            // 单线程特性，所以此处不会存在交叉执行的情况，仅有并发请求中第一个执行的会进入if
		status = "pending";
		db.select("SQL", function(results) {   // 此处调用后会释放当前执行，但由于状态已改为pending，并发的其他请求进入不了
			proxy.emit("selected",  results);   // SQL查询结果出来后，触发selected事件，所有已挂载的侦听器都会调用回到函数
			status = "ready";           //  恢复状态，后序来的查询照常执行
		});
	}
};

【多对一的事件处理】 即回调函数依赖多个资源ready后才执行

var after = function(times, callback) {   // 使用偏函数做资源完成次数的判断封装
	var count=0, result={};
	return function(key, value) {
		result[key] = value;
		count++;
		if(count === times) {
			callback(results);
		}
	};
};
var emitter = new events.Emitter();
var done = after(3, render);  // 获取的资源达到三次后，调用render进行渲染
emitter.on("done", done);   // 声明监控事件和回调函数done

fs.readFile(template_path, "utf8", function(err, template) {
	emitter.emit("done", "template", template);   // 获取模板资源后，触发done事件
});
db.query(sql, function(err, data) {
	emitter.emit("done", "data", data);   // 获取数据库资源后，触发done事件
});
l10n.get(function(err, resources) {
	emitter.emit("done", "resources", resources);  // 获取字库资源后，触发done事件
});


【Promise/Deferred模式】   先执行异步调用，延迟传递处理
JQuery中的典型用例(ajax请求)：
$.get('./api').success(onSuccess).error(onError).complete(onComplete);
等价于
$.get('./api', {    // 使用事件方式而不是Promise/Deferred模式执行调用时，要通过一个匿名对象将所有可能的执行分支传入，哪怕业务上这个回调肯定不用；
	success: onSuccess,
	error: onError,
	complete: onComplete
});

【Promises/A】  
定义：
1、Promise操作只会处在3中状态中的一种：未完成态、完成态、失败态；
2、Promise的状态只会从未完成态向完成态或者失败态转化，不能逆反，完成态和失败态不能互相转化；
3、Promise的状态一旦转化，不能被更改；

一个Promise对象只要具备then()方法即可，对then()方法有以下要求：
1、接受完成态、错误态的回调方法。在操作完成或出现错误时，将会调用对应方法；
2、可选的支持progress事件回调作为第三个方法；
3、then()方法只接受function对象，其余对象被忽略；
4、then()方法继续返回Promise对象，以实现链式调用；

Deferred主要用于内部，用户维护异步模型的状态；Promise作用于外部，通过then()方法暴露给外部添加自定义逻辑
Promise/Deferred模式将业务中不可变的部分封装在了Deferred中，将可变的部分交给了Promise。
对于不同的场景需要去封装和改造Deferred部分，然后才能得到简洁的接口；如果场景不常用，封装花费的时间与带来的简洁相比不一定划算。


一个典型的简单实现：

var Promise = function() {
	EventEmitter.call(this);
};
util.inherits(Promise, EventEmitter);
//  then()方法所做的事情是将回调函数存放起来。
Promise.prototype.then = function(fulfilledHandler, errorHandler, progressHandler) {
	if(typeof fulfilledHandler === 'function') {
		this.once('success', fulfilledHandler);  // 利用once()方法保证成功回调只执行一次
	}
	if(typeof errorHandler === 'function') {
		this.once('error', errorHandler);  // 利用once()方法保证异常回调只执行一次
	}
	if(typeof progressHandler === 'function') {
		this.on('progress', progressHandler);
	}
	return this;
};

// 触发执行前面Promise里回调函数的地方，实现这些功能的对象被称为Deferred，即延迟对象
var Deferred = function() {
	this.state = 'unfulfilled';
	this.promise = new Promise();
};
Deferred.prototype.resolve = function(obj) {
	this.state = 'fulfilled';
	this.promise.emit('success', obj);
};
Deferred.prototype.reject = function(err) {
	this.state = 'failed';
	this.promise.emit('error', err);
};
Deferred.prototype.progress = function(data) {
	this.promise.emit('progress', err);
};

简单改造一个调用为Promise/A形式
var promisify = function(res) {
	var deferred = new Deferred();
	var result = '';
	res.on('data', function(chunk) {
		result += chunk;
		deferred.progress(chunk);
	});
	res.on('end', function() {
		deferred.resolve(result);
	});
	res.on('error', function(err) {
		deferred.reject(err);
	});
	return deferred.promise;  // 不让外部程序调用resolve()和reject()
};

使用：
promisify(res).then(function(){done();},   function(err){err();},   function(chunk){console.log("BODY:" + chunk);});


【Promise中的多异步协作】
Deferred.prototype.all = function(promises) {
	var count = promises.length;
	var that = this;
	var results = [];
	promises.forEach(function(promise, i) {  // 对于每个传入的异步回调，将其和下标一起传入
		promise.then(function(data) {    
			count--;                // 完成一份依赖的异步调用，则将计数减一，表示回调数据中任务完成一个
			result[i] = data;       // 将回调结果作为任务数组对应下标指向的元素存入
			if(count === 0) {
				that.resolve(results);  // 所有回调函数都执行完毕时，完成执行
			}
		},           function(err) {       // 对每个函数调用的异常处理操作
			that.reject(err);
		});
	});
	return this.promise;
}
使用：
var promise1 = readFile("foo.txt", "utf-8");
var promise2 = readFile("bar.txt", "utf-8");
var deferred = new Deferred();
// 传入两个依赖任务，当最后一个任务执行完毕时，前述that.resolve(results);调用，传入结果results数组作为此处入参
deferred.all([promise1, promise2]).then(function(results) {  
	// TODO
}, function(err) {
	// TODO
});

要让Promise支持链式执行，主要通过以下两个步骤：
1、将所有回调都存入到队列中；
2、Promise完成时，逐个执行回调，一旦检测到返回了新的Promise对象，停止执行，然后将当前Deferred对象的promise引用改变为新的Promise对象，并将队列中余下的回调转交给它；


/************************* async模块(用于流程控制)  *************************/

【异步的串行执行】
// 此处隐含了特殊的逻辑，每个callback()执行时都会将结果保存起来，然后执行下一个调用，知道结束所有调用
// 最终的回调函数function(err, results)执行时，队列里的异步调用保存的结果以数组的方式传入
async.series([
	function(callback) {
		fs.readFile('file1.txt', 'utf-8', callback);
	},
	function(callback) {
		fs.readFile('file2.txt', 'utf-8', callbakc);
	}], function(err, results) {
		// results => [file1.txt, file2.txt]
	}
);
等价于
fs.readFile('file1.txt', 'utf-8', function(err, content) {
	if(err) {
		return callback(err);
	}
	fs.readFile('file1.txt', 'utf-8', function(err, data) {
		if(err) {
			return callback(err);
		}
		callback(null, [content, data]);  // 将两次读取的内容作为参数传给回调函数
	});
});

【异步的并行执行】
async.parallel([
	function(callback) {
		fs.readFile('file1.txt', 'utf-8', callback);
	},
	function(callback) {
		fs.readFile('file2.txt', 'utf-8', callback);
	}], function(err, results) {
		// results => [file1.txt, file2.txt]
	}
);
等价于
var counter = 2;
var results = [];
var done = function(index, value) {
	results[index] = value;
	counter--;
	if(counter === 0) {
		callback(null, results);
	}
};
var hasErr = false;
var fail = function(err) {
	if(!hasErr) {
		hasErr = true;
		callback(err);
	}
};
fs.readFile('file1.txt', 'utf-8', function(err, content) {
	if(err) {
		return fail(err);
	}
	done(0, content);
});
fs.readFile('file2.txt', 'utf-8', function(err, data) {
	if(err) {
		return fail(err);
	}
	done(1, data);
});


【异步调用的依赖处理】
async.waterfall([
	function(callback) {
		fs.readFile('file1.txt', 'utf-8', function(err, content) {
			callback(err, content);
		});
	},
	function(arg1, callback) { // arg1 => file2.txt
		fs.readFile(arg1, 'utf-8', function(err, content) {
			callback(err, content);
		});
	}, 
	function(arg1, callback) {  // arg1 => file3.txt
		fs.readFile(arg1, 'utf-8', function(err, content) {
			callback(err, content);
		});
	}
	], function(err, result) {
		// result => file4.txt
	}
);
等价于
fs.readFile('file1.txt', 'utf-8', function(err, data1) {
	if(err) { return callback(err); }
	fs.readFile(data1, 'utf-8', function(err, data2) {
		if(err) { return callback(err); }
		fs.readFile(data2, 'utf-8', function(err, data3) {
			if(err) { return callback(err); }
			callback(null, data3);
		});
	});
});

【自动依赖处理】
var deps = {
	readConfig: function(callback) {
		// read config file
		callback();
	},
	connectMongoDB: ['readConfig', function(callback) {  // 一个依赖
		// connect to mongodb
		callback();
	}],
	connectRedis: ['readConfig', function(callback) {  // 一个依赖
		// connect to redis
		callback();
	}],
	compileAsserts: function(callback) {
		// compile asserts
		callback();
	},
	uploadAsserts: ['compileAsserts', function(callback) {  // 一个依赖
		// upload to assert
		callback();
	}],
	startup: ['connectMongoDB', 'connectRedis', 'uploadAsserts', function(callback) {   // 三个依赖
		// startup
	}]
};
async.auto(deps);
等价于
proxy.assp('readtheconfig', function() {
	// read config file
	proxy.emit('readConfig');
}).on('readConfig', function() {
	// connect to mongodb
	proxy.emit('connectMongoDB');
}).on('readConfig', function() {
	// connect to redis
	proxy.emit('connectRedis');
}).assp('compiletheasserts', function() {
	// compile asserts
	proxy.emit('compileAsserts');
}).on('uploadasserts', function() {
	// upload to assert
	proxy.emit('uploadAsserts');
}).all('connectMongoDB', 'connectRedis', 'uploadAsserts', function() {
	// startup
});


/************************* step模块(用于流程控制)  *************************/

安装
npm install step

Step接受任意数量的任务，所有的任务都会串行执行
Step(
	function readFile1()
	)


/*************************  模块  *************************/

模块分为两类：一类是核心模块；另一类是文件模块

Node会缓存模块编译和执行后的对象，对相同模块的二次加载都一律采用缓存优先的方式（第一优先级）


module对象的属性：
1、 exports 
2、 paths  是模块查找的路径数组，诸如：
    [ '/root/nodeExec/node_modules',
	  '/root/node_modules',
	  '/node_modules' ]

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
2、bin目录下放置可执行二进制文件
3、lib目录下放置JavaScript代码；
4、doc目录下放置文档；
5、test目录下放置单元测试用例代码；


package.json字段说明：
1、name  包名，必须唯一
2、description  包简介
3、version  版本号，格式为 major.minor.revision
4、keywords  关键词数组
5、dependencies  依赖包列表
6、repositories  托管代码位置列表
7、maintainers  包维护者列表
8、contributors
9、bugs
10、licenses
// Node相比于NPM规范多的4个字段
11、author  包作者
12、bin   将包作为命令行工具使用时，通过npm install package_name -g安装时将此目录添加到执行路径，之后其下脚本可以在命令行中直接执行
13、main   指定主模块入口文件  默认是 index  按照js、node、json的扩展名依次搜索文件
14、devDependencies  开发时需要的依赖，指导后续维护或者扩展开发


/*************************  调试  *************************/
$ node debug debug.js      // 以调试模式运行debug.js文件

$ node --debug[=port] script.js     // 远程调试，脚本正常执行不暂停，默认不指定端口时使用5858端口
$ node --debug-brk[=port] script.js   // 远程调试，调试器启动后会立刻暂停执行脚本

// 在一个终端中
$ node --debug-brk debug.js
// 在另一个终端中
$ node debug 127.0.0.1:5858


/*************************  异步编程  *************************/

偏函数（本质上一种模板思想，工厂模式的体现）
var toString = Object.prototype.toString();

var isString = function(obj) {
	return toString.call(obj) == '[object String]';
};
var isFunction = function(obj) {
	return toString.call(obj) == '[object Function]';
};
提炼成偏函数
var isType = function(type) {
	return function(obj) {
		return toString.call(obj) == '[object ' + type +']';
	};
};


编写异步方法异常处理注意点：
1、必须执行调用者传入的回调函数；
2、正确传递回异常供调用者判断；
var async = function(callback) {
	process.nextTick(function() {
		var results = something;
		if(error) {
			return callback(error);
		}
		callback(null, result):
	});
};


异常捕获的正确写法：(正确执行的流程callback调用不要写在try里面，否则callback()调用抛出的异常会进入catch，又会执行callback(err)，导致回调函数被执行两遍)
try{
	req.body = JSON.parse(buf, options.reviver);
}catch(err) {
	err.body = buf;
	err.status = 400;
	return callback(err);
}
callback();