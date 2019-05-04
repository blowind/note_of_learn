本项目需要使用JiBX生成pojo和xml的绑定关系文件，主要是bind.xml和pojo.xsd，然后在此基础上compile出有绑定关系的class文件，具体操作如下：

1、首先需要build项目生成POJO类的class文件

2、在项目的 target\classes 目录下运行如下命令：
java -cp bin;D:\jibx\lib\jibx-tools.jar org.jibx.binding.generator.BindGen -b bind.xml com.zxf.nio.httpxml.pojo.Order

3、然后在同一个目录下运行，本命令用于根据绑定文件bind.xml和POJO对象的映射关系和规则动态修改POJO类，得到相关POJO的.class文件(不修改POJO.java文件)，不执行本操作会报Unable to access binding information for class错误：
java -cp bin;D:\jibx\lib\jibx-bind.jar org.jibx.binding.Compile -v bind.xml

4、查看已生成bind、schema文件信息的命令（可选，也是在target\classes目录下执行）：
java -cp bin;D:\jibx\lib\jibx-run.jar org.jibx.runtime.PrintInfo -c com.zxf.nio.httpxml.pojo.Order



注意：如果项目重新rebuild了，使用JiBX compile出来的class文件会被清理，需要运行上面的命令重新生成