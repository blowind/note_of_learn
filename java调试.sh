
# 未使用manifest指定主类时运行使用命令行打出来的jar包，其中 com.manning.gia.todo.ToDoApp是main所在package路径和主类
java -cp .\build\libs\todo-app.jar com.manning.gia.todo.ToDoApp

# gradle编译时使用jar manifest指定过主类的运行方式
java -jar todo-app-customized-0.1.jar

#  设置堆Heap最大值20MB和最小值20MB，当堆内存溢出时，生成dump文件
$ java -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -jar Main.jar

#  设置虚拟机栈和本地方法栈大小为128k，由于Hot Spot实现这两个栈是不区分的，因此只需要设置一处
$ java -Xss128k -jar Main.jar

#  设置直接内存容量大小，不设置时，默认与Java堆最大值(-Xmx指定)一样
$ java -Xmx20M -XX:MaxDirectMemorySize=10M  -jar Main.jar


# 设置方法区和运行时常量池大小，由于java 8之后已经移除永久代(PermGen Space)，因此本设置仅在1.7之前的java有效
$ java -XX:PermSize=10M -XX:MaxPermSize=10M -jar  Main.jar

#  设置元空间的大小，由于元空间使用本地内存，因此仅受本地内存限制。java 1.8及以上版本支持
#  MetaspaceSize 设置初始空间大小，达到该值出发GC进行类型卸载，同时GC会调整该值
#  MaxMetaspaceSize  最大元空间，默认没有限制(由本地内存大小来限制)
$ java -XX:MetaspaceSize=8M  -XX:MaxMetaspaceSize=8M  -jar Main.jar


-Xnoclassgc   ##  控制对方法区不用的类进行回收

-verbose:class   -XX:+TraceClassLoading   ##  查看类加载和卸载信息

#  查看堆栈dump文件，[]里面是可选参数
$ jhat  [-J-Xmx2048m]  [-port 7000]  java_pid16456.hprof  


#  将pid为11951的java进程的heap以二进制的格式dump到session.bin文件中
$ jmap -dump:format=b,file=/root/session.bin  11951

#  查看11851 java进程中各个内存占用情况
#  其中：
#  [C is a char[]
#  [S is a short[]
#  [I is a int[]
#  [B is a byte[]
#  [[I is a int[][]
$ jmap -histo 11951

 num     #instances         #bytes  class name
----------------------------------------------
   1:        115423      289196816  [B
   2:        216777      105718392  [C
   3:        129251        6857256  [Ljava.lang.Object;
   4:        202671        4864104  java.lang.String
   5:        121098        2906352  java.util.ArrayList
   6:         87825        2810400  java.util.HashMap$Node
   7:          7799        2612960  [I
   8:         33348        1600704  java.nio.HeapByteBuffer
   9:         62877        1509048  java.util.Collections$UnmodifiableRandomAccessList

   
   
   
##  在*inx系统上使用快速返回的伪随机数运行java程序，注意其中的./不能少   
-Djava.security.egd=file:/dev/./urandom