/*Promise是抽象异步处理对象以及对其进行各种操作的组件*/
使用new Promise实例化的promise对象有三种状态：
1、has-resolution - FulFilled    resolve成功时的状态
2、has-rejecttion - Rejected     reject失败时的状态
3、unresolved - Pending          promise对象刚被创建后的初始化状态


var promise = new Promise(function(resolve, reject) {
	// 异步处理结束
	// 处理结束后，调用resolve或reject
});

【【【【Instance Method】】】】

promise.then(onFulfilled, onRejected)
resolve(成功)时，onFulfilled被调用，reject(失败)时，onRejected被调用，这两个参数都可选

只处理异常时的两种写法：
promise.then(undefined, onRejected);
promise.catch(onRejected);


【【【【Static Method】】】】
Promise.all()


Promise.resolve()  // new Promisea()方法的一种快速简写，主要用于promise对象初始化的简写或者编写测试代码
Promise.resolve(42);  
等价于
new Promise(function(resolve) {
	resolve(42);
});


Promise.reject() // 同上的静态方法
Promise.reject(new Error('出错了'))
等价于
new Promise(function(resolve, reject) {
	reject(new Error("出错了"));
});


Promise.thenable()  // 将thenable对象转换为promise对象(具有.then方法即相似then行为的对象)，如jQuery.ajax()返回值，一个具有.then方法的jqXHR Object
var promise = Promise.resolve($.ajax('/json/comment.json'));
promise.then(function(value) {
	console.log(value);
});


Promise.all([])  //  在数组中回调函数全部执行完毕后（变为FulFilled或Rejected状态），才会调用then()方法，数组内函数都是并行执行的
function timerPromisefy(delay) {
	return new Promise(function(resolve) {
		setTimeout(function() {
			resolve(delay);
		}, delay);
	});
}
var startDate = Date.now();
Promise.all([timerPromisefy(1), timerPromisefy(32), timerPromisefy(64), timerPromisefy(128)])
.then(function(values) {
	console.log(Date.now() - startDate + 'ms');  // 约 130ms
	console.log(values);    //    得到  [ 1, 32, 64, 128 ]
});



Promise.face([])  //  数组中回到函数只要有一个promise进入FulFilled或者Rejected状态就执行then()方法，落后的每个函数依然会执行完毕，只是不再执行then()
function timerPromisefy(delay) {
	return new Promise(function(resolve) {
		setTimeout(function() {
			resolve(delay);
		}, delay);
	});
}
Promise.race([timerPromisefy(1), timerPromisefy(32), timerPromisefy(64), timerPromisefy(128)])
.then(function(value) {
	console.log(value);    // 得到 1
});



减少匿名函数嵌套的小技巧：
jsonParse:bind(null, callback);
等价于
function bindJSONParse(error, value) {
	jsonParse(callback, error, value);
}


===============================================基本示例   nodejs执行===============================================
function asyncFunction() {
	// 生成Promise实例时设置执行的回调函数，在内部使用resolve时则成功返回，否则按失败返回
	// 即处理结果正常的话，调用 resolve(returnValue) 处理结果错误的话，调用 reject(ErrorObject)
	return new Promise(function(resolve, reject) { 
		// 设置在1秒后 resolve，即成功返回，此时后面的then里的回调函数被执行
		setTimeout(function() {
			/*resolve("Async Hello World!");*/
			reject("sorry to fail");
		}, 1000);
	});
}

asyncFunction().then(function(value) {  // 设置resolve时的回调函数
	console.log("success: " + value);
}).catch(function(error) {  // 设置发生错误时的回调函数
	console.log("failed: " + error);
});


===============================================基本示例2   浏览器中执行===============================================
function getURL(URL) {
	return new Promise(function(resolve, reject) {
		var req = new XMLHttpRequest();
		req.open('GET', URL, true);
		req.onload = function() {
			if(req.status === 200) {
				resolve(req.responseText);
			}else{
				reject(new Error(req.statusText));
			}
		};
		req.onerror = function() {
			reject(new Error(req.statusText));
		};
		req.send();
	});
}

var URL = "http://www.bing.com";
getURL(URL).then(function onFulFilled(value) {
	console.log(value);
}).catch(function onRejected(error) {
	console.error(error);
});


=============================================== promise chain ===============================================
function taskA() { 
	console.log("Task A");  // 走 taskA -> taskB -> finalTask
	// throw new Error('throw Error @ task A');  // 走taskA -> onRejected -> finalTask
}
function taskB() { console.log("Task B"); }
function onRejected(error) { console.log("Catch Error: A or B", error); }
function finalTask() { console.log("Final Task"); }
Promise.resolve().then(taskA).then(taskB).catch(onRejected).then(finalTask);

可能执行路线：
finalTask<----  
   ^         | 
   |         |
   |         |
 taskB--->onRejected
   ^         ^
   |         |
   |         |
 taskA -------


链式调用中互相传值，通过return
 function doubleUp(value) { return value*2; }
 function increment(value) { return value+1; }
 function output(value) { console.log(value); }
 Promise.resolve(1).then(increment).then(doubleUp).then(output).catch(function(error) { console.log(error); }); // 得到4


 =============================================== promise chain ===============================================
 function throwError(value) {
 	throw new Error(value);
 }
 function badMain(onRejected) {
 	return Promise.resolve(42).then(throwError, onRejected); // throwError中的异常抓不住，因为onRejected捉的是所在then之前的异常
 }
 function goodMain(onRejected) {
 	return Promise.resolve(42).then(throwError).catch(onRejected); // throwError中的异常抓得住，因为异常在catch之前抛出
 	// return Promise.resolve(42).then(throwError).then(null, onRejected); // 等价写法
 }
 badMain(function() {
 	console.log("BAD"); // 打印不出BAD
 });
 goodMain(function() {
 	console.log("GOOD"); // 能正常捕获异常从而打印出GOOD
 });