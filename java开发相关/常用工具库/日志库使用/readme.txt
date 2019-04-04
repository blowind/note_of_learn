
已知使用比较多的日志门面系统： commons-logging 、 slf4j
1、commons-logging  简称jcl, 最早的日志门面接口，早期Spring使用这套门面系统
2、slf4j 译为简单日志门面（simple logging facade for java），当前最常用的门面系统


简单来说是所有日志实现框架共同抽象出来的接口层。而log4j和logback是众多日志框架中的几种。



【slf4j】
公共接口包

<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-api</artifactId>
	<version>1.7.26</version>
</dependency>


几乎所有其他日志框架的API，包括jcl的API，都能够随意的转调回slf4j。
但是有一个唯一的限制就是转调回slf4j的日志框架不能跟slf4j当前桥接到的日志框架相同！！！！！！！！！！！！
具体的回调依赖关系，请查看随附的《应用程序使用其他现存日志框架接口转调slf4j示意图.png》




============================   具体日志框架及相关桥接器   ============================

【logback】
logback从出生开始就是其作者奔着取代log4j的目的开发的，因此一方面logback继承了log4j大量的用法，使得学习和迁移的成本不高，另一方面logback在性能上要明显优于log4j，尤其是在大量并发的环境下，并且新增了一些log4j所没有的功能（如将日志文件压缩成zip包等）。是slf4j的原生实现。（Native implementations）


依赖包说明：
包含了logback本身所需的logback-core.jar及logback-classsic.jar
<dependency>
	<groupId>ch.qos.logback</groupId>
	<artifactId>logback-classic</artifactId>
	<version>1.2.3</version>
</dependency>


调用层次：
applicatin -> slf4j-api -> logback-classic


其他：
由官方提供的对Spring的支持，它的作用就相当于log4j中的Log4jConfigListener；
这个listener，网上大多都是用的自己实现的，原因在于这个插件似乎并没有出现在官方文档的显要位置导致大多数人并不知道它的存在；
实际好像很少看到有用这个包的，Spring Boot框架默认没继承也不影响使用logback
<dependency>
    <groupId>org.logback-extensions</groupId>
    <artifactId>logback-ext-spring</artifactId>
    <version>0.1.5</version>
</dependency>


【log4j】
log4j是apache实现的一个开源日志组件，
早已被托管给Apache基金会维护，并且自从2012年5月之后就没有更新了。


log4j的正常运行需要配置文件，配置文件类型：log4j配置文件可以是log4j.xml也可以是log4j.properties。需要为其配置root、appender、layout等信息。
配置关键：
1、必须配置root logger和一个appender。
2、日志输出级别，由高到低为FATAL、ERROR、WARN、INFO、DEBUG  


依赖包说明：
包含了logback本身所需的slf4j-log4j12.jar及log4j.jar
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-log4j12</artifactId>
	<version>1.7.26</version>
</dependency>


调用层次：
applicatin -> slf4j-api -> slf4j-log4j12 -> log4j



【jdk自带日志系统】

依赖包说明：
将jdk自带的日志系统桥接到slf4j接口，自带日志系统在rt.jar包中java.util.logger目录下
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jdk14</artifactId>
    <version>1.7.26</version>
</dependency>

调用层次：
applicatin -> slf4j-api -> slf4j-jdk14 -> jvm


【common-logging】


依赖包说明： 引入依赖commons-logging.jar包，该包是另一个日志门面接口
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jcl</artifactId>
    <version>1.7.26</version>
</dependency>

调用层次：
                                                         log4j（如果classpath存在相关jar包，优先）
                                                        /
applicatin -> slf4j-jcl -> slf4j-api -> commons-logging 
                                                        \
                                                         jvm（没有其他日志包时，默认兜底日志系统）


【【【应用程序已有日志接口桥接】】】
基本原则是都转到slf4j接口，再通过slf4j调用底层具体的日志实现框架，要求桥接模块入口接口不能与实际日志框架接口一致，因为一致的话可以直接调用没必要桥接，并且桥接会导致A->B->A->B的死循环桥接


【jcl-over-slf4j】 与slf4j-jcl.jar包互斥，一个项目不能共存
用来将应用程序的commons-logging接口桥接到slf4j接口调用的jar包

老的Spring源代码中大量使用到的commons-logging，使用本jar包替换成slf4j，只有在添加了这个依赖之后才能看到Spring框架本身打印的日志，否则只能看到开发者自己打印的日志
（此说法最新应该不成立了，截止2019年Spring默认日志框架是logback）
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>1.7.26</version>
</dependency>


【log4j-over-slf4j】 和slf4j-log4j12.jar包互斥，一个项目不能共存，实测classpath中log4j.jar在前，log4j-over-slf4j.jar包在后时，可以正常运行，
用来将应用程序的log4j接口调用桥接到slf4j接口调用的jar包
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>log4j-over-slf4j</artifactId>
    <version>1.7.26</version>
</dependency>


【jul-to-slf4j】  和slf4j-jdk14.jar包互斥，一个项目不能共存
用来将应用程序的jdk自带日志接口调用桥接到slf4j接口调用的jar包
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jul-to-slf4j</artifactId>
    <version>1.7.26</version>
</dependency>




