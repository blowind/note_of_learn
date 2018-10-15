基本用法：
对于3.0版本的mybatis，主要通过JAVA接口和SQL语句的XML文件匹配进行处理，不需要过多映射相关的Java实现代码。



【注意点】
1、model（或者说数据模型对象DO）中不要使用基本类型，因为基本类型字段会有默认值，因此在某些情况下无法实现字段为null的场景。此外在动态SQL编写时，无法使用field != null的test判断（结果永远为true），所以DO对象中不要使用byte、int、short、long、float、double、char、boolean基本类型，要使用类类型。
2、使用MyBatis3.0的功能，一般接口名和XML文件名一一对应，即src/main/java目录下com\zxf\zxfbatis\simple路径里mapper子目录中放置CustomerMapper.java接口定义文件，则相应在src/main/resources目录下com\zxf\zxfbatis\simple\路径里mapper子目录放置CustomerMapper.xml映射文件，当然如果在config配置中指定了相应目录，则不需要这种严格对应关系。

【mybatis-config.xml】
显示了在src/main/resources目录下放置该文件作为mybatis基本配置的方法，当前可以用java代码配置后，此方法不常用，此处用作基本展示。