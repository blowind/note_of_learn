package com.zxf.myspring;


import com.zxf.spring.demo.SimpleBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/10/29 09:29
 *
 * 执行结果：
 * 类【EventListenerMethodProcessor】的对象org.springframework.context.event.internalEventListenerProcessor调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【EventListenerMethodProcessor】的对象org.springframework.context.event.internalEventListenerProcessor调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * 类【DefaultEventListenerFactory】的对象org.springframework.context.event.internalEventListenerFactory调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【DefaultEventListenerFactory】的对象org.springframework.context.event.internalEventListenerFactory调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * 类【BeanConfig$$EnhancerBySpringCGLIB$$e4a8e56c】的对象beanConfig调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【BeanConfig$$EnhancerBySpringCGLIB$$e4a8e56c】的对象beanConfig调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * 类【DisposableBeanImpl】的对象disposableBeanImpl调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【DisposableBeanImpl】的对象disposableBeanImpl调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * 类【SimpleBean】的构造函数执行
 * 类【SimpleBean】调用BeanNameAware接口的setBeanName()方法
 * 类【SimpleBean】调用BeanFactoryAware接口的setBeanFactory()方法
 * 类【SimpleBean】调用ApplicationContextAware接口的setApplicationContext()方法
 * 类【SimpleBean】的对象getSimpleBean调用BeanPostProcessor接口的postProcessBeforeInitialization方法
 * 类【SimpleBean】的对象执行本对象中@PostConstruct注解的方法
 * 类【SimpleBean】调用InitializingBean接口的afterPropertiesSet()方法
 * 类【SimpleBean】的对象执行@Bean注解中init属性指定的方法
 * 类【SimpleBean】的对象getSimpleBean调用BeanPostProcessor接口的postProcessAfterInitialization方法
 * ############# before context.start ##############
 * ############# after context.start ##############
 * ############# before context.close ##############
 * 类【SimpleBean】的对象执行本对象中@PreDestroy注解的方法
 * 类【SimpleBean】的对象执行@Bean注解中destroy属性指定的方法
 * 【Spring IOC框架】调用接口DisposableBean的destroy方法
 * ############# after context.close ##############
 *
 
 总结：
 1、要对所有bean做处理的时候，专门用一个新Bean实现BeanPostProcessor接口做相关操作，
	通过操作接口的两个方法对每个Bean对象在Bean初始化开始和结束时加入相关自定义处理
 2、要在单个Bean初始化时做自定义处理，有三种方法：
	1）、可以在@Bean注解中通过initMethod参数指定自定义函数（该函数一般放在该Bean类中）
	2）、在Bean的类实现中通过注解@PostConstruct指定一个自定义方法
	3）、让该Bean的类实现InitializingBean接口，在接口方法afterPropertiesSet()中放入处理代码
	4）、定义一个无参构造函数（会覆盖Spring默认生成的无参构造函数），在其中实现自定义处理，使用很少
 3、要在单个Bean销毁时做自定义处理，有两种方法：
	1）、可以在@Bean注解中通过destroyMethod参数指定自定义函数（该函数一般放在该Bean类中）
	2）、在Bean的类实现中通过注解@PreDestroy指定一个自定义方法
 4、在Bean初始化过程中需要获取容器BeanFactory对象进行特定操作时，让该Bean实现BeanFactoryAware接口
	可以将BeanFactory保存在Bean本地以便后续运行时使用，由于与Spring强耦合不推荐
 5、在Bean初始化过程中需要获取容器ApplicationContext对象进行特定操作时，让该Bean实现ApplicationContextAware接口
	可以将ApplicationContext保存在Bean本地以便后续运行时使用，一般用于获取其他Bean，由于与Spring强耦合不推荐
 6、在Spring容器整个关闭时，需要做自定义处理时（一般是自有公共资源释放），
	专门用一个新Bean实现DisposableBean接口做相关操作，
 7、BeanNameAware接口感觉没什么用，因为只是传入Bean的名字字符串，便宜行事的时候可以用做第2点的一种实现
 8、ResourceLoaderAware接口将Spring框架的ResourceLoader保存到本地以便运行过程中加载文件资源，暂时看不到什么大用
 */
public class SpringMain {

	public static void main(String[] argv) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.zxf.myspring");
		System.out.println("############# before context.start ##############");
		context.start();
		System.out.println("############# after context.start ##############");

		SimpleBean simpleBean = context.getBean(SimpleBean.class);
		simpleBean.outputResult();

		/* 不执行下面这条语句时，Bean的destroy方法和DisposableBean接口的destroy方法可能不会执行 */
		System.out.println("############# before context.close ##############");
		context.close();
		System.out.println("############# after context.close ##############");
	}
}
