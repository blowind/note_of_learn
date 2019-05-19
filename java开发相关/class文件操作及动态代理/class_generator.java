
/*******************          手动加载.class二进制文件到JVM并实例化功能演示             *********************/
public class Programer {
    public void code() {
        System.out.println("I'm a programer, Just coding...");
    }

    public void showNumber(Integer number) {
        System.out.println("input number is " + number);
    }
}
// 自定义类加载器，继承JDK1.0就有的ClassLoader
public class MyClassLoader extends ClassLoader{
    public Class<?> defineMyClass(byte[] b, int off, int len) {
        return super.defineClass(null, b, off, len);
    }
}
// .class加载操作类
public class LoadClassFile {
    public static void main(String[] args) throws IOException {
        /* 读取Programer.class文件字节码到byte数组result */
        File file = new File(".");
        InputStream input = new FileInputStream(file.getCanonicalPath() + "\\target\\classes\\samples\\Programer.class");
        byte[] result = new byte[1024];
        int count = input.read(result);

        /* 使用自定义类加载器将byte字节码数组转换成对应的class对象 */
        MyClassLoader loader = new MyClassLoader();
        Class clazz = loader.defineMyClass(result, 0, count);
        // 通过打印类名看类定义是否加载成功
        System.out.println(clazz.getCanonicalName());

        try{
            // 获取类实例
            Object o = clazz.newInstance();
            // 获取类方法并调用指定目标对象的该方法
            clazz.getMethod("code", null).invoke(o, null);
            clazz.getMethod("showNumber", Integer.class).invoke(o, 33);
        }catch ( IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException
                | InstantiationException e) {
            e.printStackTrace();
        }
    }
}

/*******************          ASM --- class文件操作工具之一             *********************/

ASM 是一个 Java 字节码操控框架。它能够以二进制形式修改已有类或者动态生成类。ASM 可以直接产生二进制 class 文件，也可以在类被加载入 Java 虚拟机之前动态改变类行为。ASM 从类文件中读入信息后，能够改变类行为，分析类信息，甚至能够根据用户要求生成新类。

不过ASM在创建class字节码的过程中，操纵的级别是底层JVM的汇编指令级别，这要求ASM使用者要对class组织结构和JVM汇编指令有一定的了解。

<dependency>
	<groupId>org.ow2.asm</groupId>
	<artifactId>asm</artifactId>
	<version>7.1</version>
</dependency>

/* 使用ASM直接生成Program.class文件示例演示（不通过Program.java文件定义并编译，直接生成字节码） */
public class MyASMGenerator {
    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        /* 确定类的头部信息，参数分别是：java版本，类修饰符，类名，签名，父类名，接口名数组 */
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Programer", null, "java/lang/Object", null);

        /* 创建构造函数，参数分别是函数修饰符，函数名，函数描述符，签名，异常名数组 */
        MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>","()V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        /* 定义code方法 */
        MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "code", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("I'm a Programmer,Just use asm to generate .class file");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V");
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();

        // 完成Programer类class文件内容的定义
        classWriter.visitEnd();

        // 将Programer类字节码内容转换成字节数组写到文件里
        byte[] data = classWriter.toByteArray();
        File file = new File("D://Programer.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }
}
// 加载演示ASM直接生成的Program.class文件的操作
public class LoadClassFile {
    public static void main(String[] args) throws IOException {
        InputStream input = new FileInputStream("D:\\Programer.class");
        byte[] result = new byte[1024];
        int count = input.read(result);

        /* 使用自定义类加载器将二进制形式的类定义字节码加载到内存 */
        MyClassLoader loader = new MyClassLoader();
        Class clazz = loader.defineMyClass(result, 0, count);
        // 通过打印类名看类定义是否加载成功
        System.out.println(clazz.getCanonicalName());

        try{
            // 获取类实例
            Object o = clazz.newInstance();
            // 获取类方法并调用指定目标对象的该方法
            clazz.getMethod("code", null).invoke(o, null);  // 输出I'm a Programmer,Just use asm to generate .class file
        }catch ( IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException
                | InstantiationException e) {
            e.printStackTrace();
        }
    }
}


/*******************          Javassist --- class文件操作工具之二             *********************/

Javassist是一个开源的分析、编辑和创建Java字节码的类库。是由东京工业大学的数学和计算机科学系的 Shigeru Chiba （千叶 滋）所创建的。
它已加入了开放源代码JBoss应用服务器项目，通过使用Javassist对字节码操作为JBoss实现动态AOP框架。
javassist是jboss的一个子项目，其主要的优点，在于简单，而且快速。
直接使用java编码的形式，而不需要了解虚拟机指令，就能动态改变类的结构，或者动态生成类。

1. Javassist不支持要创建或注入的类中存在泛型参数
2. Javassist对@类型的注解（Annotation）只支持查询，不支持添加或修改

<dependency>
	<groupId>org.javassist</groupId>
	<artifactId>javassist</artifactId>
	<version>3.25.0-GA</version>
</dependency>

/* 使用Javassist直接生成Program.class文件示例演示（不通过Program.java文件定义并编译，直接生成字节码） */
public class MyJavassistGenerator {
    public static void main(String[] args) {
        try{
			// 获取class定义的容器ClassPool
            ClassPool pool = ClassPool.getDefault();
            //创建Programmer类
            CtClass cc= pool.makeClass("samples.Programer");
            //定义code方法
            CtMethod method = CtNewMethod.make("public void code(){}", cc);
            //插入方法代码
            method.insertBefore("System.out.println(\"I'm a Programer,Just use javassist to generate .class file\");");
            cc.addMethod(method);

            //保存生成的字节码
            cc.writeFile("d://temp");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
// 加载演示javassist直接生成的Programer.class文件的操作
public class LoadClassFile {
    public static void main(String[] args) throws IOException {
        InputStream input = new FileInputStream("D:\\temp\\samples\\Programer.class");
        byte[] result = new byte[1024];
        int count = input.read(result);

        /* 使用自定义类加载器将二进制形式的类定义字节码加载到内存 */
        MyClassLoader loader = new MyClassLoader();
        Class clazz = loader.defineMyClass(result, 0, count);
        // 通过打印类名看类定义是否加载成功
        System.out.println(clazz.getCanonicalName());

        try{
            // 获取类实例
            Object o = clazz.newInstance();
            // 获取类方法并调用指定目标对象的该方法
            clazz.getMethod("code", null).invoke(o, null);
        }catch ( IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException
                | InstantiationException e) {
            e.printStackTrace();
        }
    }
}

/** 使用javassist生成Station代理类StationProxy示例，
*   注意到实现比较麻烦，而且包含了所有具体处理的业务代码，封装性也很差，完全不具备扩展性和通用性
**/
public interface TicketService {
    void sellTicket();
    void inquire();
    void withdraw();
}

public class Station implements TicketService {
    @Override
    public void sellTicket() {
        System.out.println("\n\r售票...\n");
    }

    @Override
    public void inquire() {
        System.out.println("\n\t问询...\n");
    }

    @Override
    public void withdraw() {
        System.out.println("\n\r退票...\n");
    }
}

public class StationProxyGenerator {

    private static void createProxy() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        // 指定类名
        CtClass cc = pool.makeClass("samples.StationProxy");

        // 设置接口
        CtClass interfaceA = pool.get("samples.TicketService");
        cc.setInterfaces(new CtClass[]{interfaceA});

        // 设置域
        CtField field = CtField.make("private samples.Station station;", cc);
        cc.addField(field);

        // 指定构造函数
        CtClass stationClass = pool.get("samples.Station");
        CtClass[] arrays = new CtClass[] {stationClass};
        // 参数：1、入参类型数组；2、异常类型数组；3、；4、body；5、参数；6、绑定类
        CtConstructor ctc = CtNewConstructor.make(arrays, null, CtNewConstructor.PASS_NONE, null, null, cc);
        // 设置函数体信息
        ctc.setBody("{this.station = $1;}");
        cc.addConstructor(ctc);

        // 创建takeHandlingFee()私有方法
        CtMethod takeHandlingFee = CtMethod.make("private void takeHandlingFee() {}", cc);
        takeHandlingFee.setBody("System.out.println(\"收取手续费，打印发票'\");");
        cc.addMethod(takeHandlingFee);

        // 创建showAlertInfo()私有方法
        CtMethod showInfo = CtMethod.make("private void showAlertInfo(String info) {}", cc);
        showInfo.setBody("System.out.println($1);");
        cc.addMethod(showInfo);

        // 添加sellTicket()方法实现
        CtMethod sellTicket = CtMethod.make("public void sellTicket() {}", cc);
        sellTicket.setBody("{ this.showAlertInfo(\" 您正在使用车票代售点购票，每张收5元手续费\"); " + "station.sellTicket(); " + "this.takeHandlingFee(); " + "this.showAlertInfo(\"购票完毕，再见\");}");
        cc.addMethod(sellTicket);

        // 添加inquire()方法实现
        CtMethod inquire = CtMethod.make("public void inquire() {}", cc);
        inquire.setBody("{ this.showAlertInfo(\"本问询信息仅供参考\");" + " station.inquire(); " + "this.showAlertInfo(\"再见\");}");
        cc.addMethod(inquire);

        // 添加withdraw()方法实现
        CtMethod withdraw = CtMethod.make("public void withdraw() {}", cc);
        withdraw.setBody("{ this.showAlertInfo(\"退票除了扣除票额的20%外，本代售点额外加收2元手续费\"); " + "station.withdraw(); " + "this.takeHandlingFee(); }");
        cc.addMethod(withdraw);

        // 生成类信息，注意是toClass()
        Class c = cc.toClass();
        // 获取类定义中入参类型为Station的构造函数
        Constructor constructor = c.getConstructor(Station.class);
        TicketService o = (TicketService)constructor.newInstance(new Station());
        // 指定对象的inquire()方法
        o.inquire();

        // 输入生成类的二进制信息到.class文件
        cc.writeFile("d://temp");
    }

    public static void main(String[] args) throws Exception {
        createProxy();
    }
}


javassist使用注意点：
1、因为tomcat和jboss使用的是独立的classloader，而Javassist是通过默认的classloader加载类，因此直接对tomcat context中定义的类做toClass会抛出ClassCastException异常，可以用tomcat的classloader加载字节码。
	CtClass cc = ...;
	Class c = cc.toClass(bean.getClass().getClassLoader());
2、发现在简单的测试中可以load的类，在tomcat中无法load。这是因为，ClassPool.getDefault()查找的路径和底层的JVM路径。而tomcat中定义了多个classloader，因此额外的class路径需要注册到ClassPool中。
	pool.insertClassPath(new ClassClassPath(this.getClass()));
3、我想在运行时修改类的一个方法，但是JVM是不允许动态的reload类定义的。一旦classloader加载了一个class，在运行时就不能重新加载这个class的另一个版本，调用toClass()会抛LinkageError。因此需要绕过这种方式定义全新的class。而toClass()其实是当前thread所在的classloader加载class。
4、Javassist生成的字节码由于没有class声明，字节码创建变量及方法调用都需要通过反射。这点在在线的应用上的性能损失是不能接受的，受到NBeanCopyUtil实现的启发，可以定义一个Interface，Javassist的字节码实现这个Interface，而调用方通过这个接口调用字节码，而不是反射，这样避免了反射调用的开销。还有一点字节码new一个变量也是通过反射，因此通过代理的方法，将每个pv都需要new的字节码对象改为每次new一个代理对象，代理到常驻内存的字节码对象中，这样避免了每次反射的开销。
