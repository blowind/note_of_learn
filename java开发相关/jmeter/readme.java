


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