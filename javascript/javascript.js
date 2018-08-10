

/*************************  作用域  *************************/

因为javascript作用域由函数来决定，所以javascript的作用域不是以花括号包围的块级作用域，而是通过函数来定义
一个函数中定义的变量只对这个函数内部可见


if(true) {
	var somevar = 'value';
}
console.log(somever);  // 输出 value


按照作用域搜索顺序，先搜索函数f的作用域，发现变量scope，因此屏蔽外层的scope变量
但是执行到log语句时，scope还未被定义，因此得到undefined
此处本质上是javascript变量声明的提升作用起效果了，即javascript默认将函数内所有变量的声明提升到函数最前部，不管其在函数内定义/赋值位置
var scope = 'global';
var f = function() {
	console.log(scope);   // 输出 undefined
	var scope = 'f';
}
f();  


函数作用域的嵌套关系时定义时决定的，而不是调用时决定的，因此javascript的作用域是静态作用域，又叫词法作用域

var scope = 'top';
var f1 = function() {
	console.log(scope);   // scope的作用域是此处f1定义时确定，因此f1里面找不到时，找f1定义的外层，即top对应的scope
};
f1();  // 输出top

var f2 = function() {
	var scope = 'f2';
	f1();
};
f2();  // 输出top


全局作用域，浏览器中的全局对象是window对象，Node.js中的全局对象是global对象，全局对象的所有属性在任何地方可见

满足以下条件的变量属于全局作用域：
1、在最外层定义的变量；
2、全局对象的属性；
3、在任何地方隐式定义的变量（未定义直接赋值的变量，即不通过var声明直接赋值的变量）；


/*************************  闭包  *************************/

当一个函数返回它内部定义的一个函数时，就产生一个闭包。
闭包不但包括被返回的函数，还包括这个函数的定义环境（对外来说主要是同一个函数内的局部变量）


下面
var generateClosure = function() {
	var count = 0;
	var get = function() {
		count++;
		return count;
	};
	return get;
};
var counter = generateClosure();
console.log(counter());  // 输出1
console.log(counter());  // 输出2
console.log(counter());  // 输出3

// 生成一个闭包的新实例
var counter1 = generateClosure();
console.log(counter1());  // 输出1
console.log(counter1());  // 输出2

闭包的用途
1、实现嵌套的回调函数；
2、隐藏对象的细节；
3、实现私有成员 （前述counter实现中，只有counter()才能返回闭包内的count变量）

// 因为闭包的存在，最内层的回调函数也可以引用最外层的变量如uid，user_info，达到回调函数的嵌套
exports.add_user = function(user_info, callback) {
	var uid = parseInt(user_info['uid']);
	mongodb.open(function(err, db) {
		if (err) {callback(err); return;}
		db.collection('users', function(err, collection) {
			if (err) {callback(err); return;}
			collection.ensureIndex("uid", function(err) {
				if (err) {callback(err); return;}
				collection.ensureIndex("username", function(err) {
					if (err) {callback(err); return;}
					collection.findOne({uid: uid}, function(err) {
						if (err) {callback(err); return;}
						if (doc) {
							callback('occupied');
						} else {
							var user = {
								uid: uid,
								user: user_info,
							};
							collection.insert(user, function(err) {
								callback(err);
							});
						}
					});
				});
			});
		});
	});
};


/*************************  对象  *************************/
 
句点运算符和关联数组引用是等价的，使用关联数组的好处是不知道对象属性名称时，可以用变量作为关联数组的索引
var some_prop = 'prop2';
foo[some_prop] = false;


var foo = {};   // 通过{}对象字面量创建对象，等价于  var foo = new Object();
foo.prop1 = 'bar';  // 等价于 foo['prop1'] = 'bar';
foo.prop2 = false;
foo.prop3 = function() {
	return 'hello world';
}
console.log(foo.prop3());

一次性把对象定义完毕
var foo = {   // 对象的初始化器
	'prop1' : 'bar',
	prop2 : 'false',   // 属性名是否加引号是可选的
	prop3 : function() {
		return 'hello world';
	}
};


对象构造函数：
function User(name, uri) {
	this.name = name;
	this.uri = uri;
	this.display = function() {
		console.log(this.name);
	}
}
var someuser = new User('zxf', 'http://www.bing.com');  // 初始化对象


上下文对象this的使用：上下文对象的作用是在一个函数内部引用调用它的对象本身

例1：
var someuser = {
	name : 'microsoft',
	display : function() {
		console.log(this.name);   // this指针不属于某个函数，而是函数调用时所属的对象
	}
};
someuser.display();  // 输出microsoft
var foo = {
	bar : someuser.display,
	name : 'foobar'
};
foo.bar();  // 输出foobar

例2:
var someuser = {
	name : 'google',
	func : function() {
		console.log(this.name);
	}
};
var foo = {
	name : 'foobar'
};
someuser.func();  // 输出google，因为this指向的对象是someuser
foo.func = someuser.func;
foo.func();       // 输出foobar，因为this指向的对象是foo

name = 'globalscope'
func = someuser.func;
func();   // 输出globalscope，因为this指向的是全局对象global