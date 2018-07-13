


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


                              /****************      控制器Controller   ****************/
							    
注意：控制器的范围最大不超过线程，即设定都是对每个线程生效的
							  
							  
【ForEach Controller】
1、一般使用中输入变量前缀同用户定义变量的变量名共用前缀
2、起始索引（不包含）一般是第一个变量名中索引部分减1
3、结束索引（包含）一般是最后一个变量名索引部分内容
4、输出变量名（关键）d，使用forEach控制器的目的

5、使用正则表达式提取正则匹配时，提出的变量使用“varName_num”形式输出，可以用作ForEach输入，此时起始索引和结束索引不填，因为不能确定正则匹配有多少结果，不填进行全匹配

【Simple Controller】
仅用来指定一个执行单元，相当于分组的作用，无其他功能

【Include Controller】
用来包含其他jmx存储的基本执行操作，模块化共用的目的

【Runtime Controller】
控制其子元件的执行时长，默认为1（秒），去掉1默认为0，此时不执行其下元件

【Switch Controller】
Switch控制语句控制根据value选择其下执行的取样器，Switch Value可以填数字和字符，填数字时其下取样器编号从0开始，无法匹配到取样器节点时运行第0个取样器。填字符时进行取样器名称匹配，大小写和空格敏感，无法匹配时不运行或者运行名为default的取样器

【While Controller】
Condition中循环判断条件。三个可用判断常量：
1、Blank：当循环中有取样器失败后停止；
2、LAST： 当循环前有取样器失败则不进入循环；
3、Otherwise： 当判断条件为false时停止循环

【Loop Controller】
循环控制器，如果线程组中也设置了执行次数，则总次数两者想乘


【Interleave Controller】
其下取样器交替执行（原则上按出现顺序，次数范围内不多不漏），例如其下两个http request 1和2，当Thread Group设置1个线程运行3次时，分别执行1、2、1各一次
ignore sub-controller blocks 设置其下所有其他控制器失效（仅控制器失效，其他元件正常）

【Random Controller】
节点下元件随机执行，设定一个线程执行多次时，可能会发生仅一个元件执行多次的情况
ignore sub-controller blocks使其下所有子控制器功能失效

【Random Order Controller】
节点下元件随机执行，不过每个元件只执行一次，顺序随机，在4个子元件且设定执行4次的场景下，每个元件都会保证执行(有点交替执行的意思了，但顺序随机)


【Once Only Controller】
子元件仅运行一次，即使放在循环控制器下也只运行一次，模块控制器的执行也要计算。作用域到线程为止，即每个线程都会执行一次仅一次控制器

【Throughput Controller】
Percent Executions 按执行次数（所有线程次数和threads*counts）的百分比来计算执行次数，取值范围[0-100]，对Per User勾选免疫
Total Executions   按Throughput的值来指定执行次数，小于等于0不执行，所有线程的次数和都不满足Throughput设定次数要求时，按外部设定的线程执行次数运行
Per User           勾选的话指定每个线程数分别独立达到吞吐量要求，仅对Total Executions生效

【If Controller】
如果此处是一个判断表达式（如：${var}==8），一定不要勾选Interpret Condition as Variable Expression
仅在Condition是 ${__javaScript(8==8)} 这种作为整体的求值形式时才勾选Interpret Condition as Variable Expression

【Transaction Controller】
事务控制器，合并其下取样器作为一体进行统计，同时事务成功条件为其下所有取样器都成功
Generate parent sample 生成父取样点，用于统计时作为观察点

【Module Controller】
选择Thread Group或者Test Fragment下面的Controller来执行，用于模块功能复用

【Recording Controller】
录制时使用，用于在录制时默认存放脚本，可用简单控制器代替


                              /****************      配置元件Config Element   ****************/
							  
【FTP Request Defaults】
FTP的公共配置，通过Get和Put选择下载还是上传

【HTTP Authorization Manager】
配置http认证信息，主要是用户名和密码

【HTTP Request Defaults】
配置http请求的共用信息