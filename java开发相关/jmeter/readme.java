


                              /****************    处理json字符串   ****************/  
							  
/* 方法一：将alibaba的fastjson-1.2.44.jar包放到apache-jmeter-4.0\lib\ext目录下，重启jmeter
**         (相比在跟路径里Add directory or jar to classpath的好处是重启后仍然有效，坏处是lib目录越来越臃肿)
** 方法二：设置插件依赖路径，此时需要在jmeter.properties(位于bin目录下)中添加依赖的路径，比如：
           plugin_dependency_paths=../dependencies;  此处dependencies是个人创建的第三方依赖库文件夹
*/
import com.alibaba.fastjson.JSONObject;
import org.apache.jmeter.util.JMeterUtils;

String response = prev.getResponseDataAsString();
JSONObject data = JSONObject.parseObject(response);
String num = data.get("data").get("number");
String numSig = data.get("data").get("numberSig");

log.info("num: {}", num);
log.info("numSig: {}", numSig);

//  vars仅在当前请求下有效
//vars.put("number", num); 
//vars.put("numberSig", numSig);

//  可以在线程组内有效
//props.put("number", num); 
//props.put("numberSig", numSig);

JMeterUtils.setProperty("number", num);
JMeterUtils.setProperty("numberSig", numSig);



                              /****************      vars的使用   ****************/
							  
//  注意使用函数时参数列表中的逗号，这种写法了省略了部分参数
//  使用__setProperty函数必须先定义变量，使用vars.put不用预先定义


（1）User Defined Variables中添加变量名值对:

	myVar   ${__P(mySetProp,)}    //  第二个参数是返回的默认值    
    newVar	
	
（2）第一个取样器Sample的postProcessor中，添加处理代码：

	${__setProperty(mySetProp, "123456",)}// 第三个参数是返回属性默认值
	vars.put("newVar", "987654");

（3）两种使用方式：  

	1、接下来的取样器Sample中的preProcessor中，添加获取代码  
	    log.info(vars.get("myVar") + "已获取");
		log.info(vars.get("newVar") + "已获取");
	2、接下来的取样器Sample正文定义中，直接使用变量  
       	${myVar}
		${newVar}
	
	
                              /****************      引入外部java类方法   ****************/
（1）引用外部java文件	

	//  已有add.java文件在D:目录下，文件内容如下：
	package test;
	public class MyClass
	{
		public int add(int a, int b)
		{
			return a + b;
		}    
	}
	//   jmeter中使用方法如下，使用source引入.java文件
	source("d:/add.java");
	

（2）引用外部class文件
	将前述java文件编译成class文件，使用方法如下：
	addClassPath("D:\\");
	import add.MyClass;
	int res = (new MyClass()).add(2,3);
	vars.put("add", res.toString());
	
（3）引用外部jar文件 （参见处理json字符串）

                              /****************      BeanShell Sampler使用   ****************/
							  
定义三个用户定义变量  u1  u2  u3
在 BeanShell Sampler的Parameters中配置 ${u1} ${u2} ${u3}

// 配置入参的使用
log.info("u1: {}", bsh.args[0]);
log.info("u2: {}", bsh.args[1]);
log.info("u3: {}", bsh.args[2]);
log.info("Parameters: {}", Parameters);

// 返回值的设置
ResponseCode=500;        //  设置BeanShell Sampler返回码，默认是200
ResponseMessage="test ResponseMessage set";   // 设置BeanShell Sampler返回消息，默认是OK
isSucces=false;                               // 设置BeanShell Sampler返回状态
SampleResult.setResponseData("Hello, Jmeter");   // 设置BeanShell Sampler的Response Data值