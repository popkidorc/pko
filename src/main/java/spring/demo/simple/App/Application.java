package spring.demo.simple.App;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import spring.demo.simple.MessageService.MessagePrinter;

//@Configuration 用于标注1个类，表示bean定义的源文件（source）；
//@ComponentScan 用于标注1个类，表示扫描指定包中的@Component标注的类，并将这些类注册为Spring IoC容器的bean,亦相当于1个<bean/>元素；
@Configuration
@ComponentScan(basePackages = { "spring.demo.simple.MessageService" })
public class Application {

	/**
	 * 匿名内部类实现了MessageService接口，并在getMessage()方法中返回了“hello,world!”字符串，
	 * 
	 * @Bean 用于标注1个方法，表示1个方法实例化、配置或初始化1个新的对象(Object)，这个对象被Spring的控制反转(IoC)容器管理，
	 *       相当于Spring <bean/>XML配置文件中<bean/>元素。（详见官方文档）
	 * 
	 * @return
	 */
	// @Bean
	// MessageService mockMessageService() {
	// return new MessageService() {
	// public String getMessage() {
	// return "Hello World!";
	// }
	// };
	// }

	public static void main(String[] args) {
		/*
		 * ApplicationContext是对1个利用（Application)提供配置的核心接口，
		 * AnnotationConfigApplicationContext是它的1个实现，
		 * 
		 * 可用来处理用@Configuration,@Component和JSR⑶30标注的类。
		 * 
		 * 由于App.java使用@Configuration进行了标注
		 * 故App.class可以作为AnnotationConfigApplicationContext构造器的的参数。
		 */
		ApplicationContext context = new AnnotationConfigApplicationContext(
				Application.class);
		/*
		 * 这句代码通过context的getBean(Class<T>)方法得到了MessagePrinter的1个实例。
		 * 为何呢得到MessagePrinter类的1个实例呢？
		 * 
		 * 看getBean(Class<T>)的API解释：
		 * 
		 * Return the bean instance that uniquely matches the given object type,
		 * if any. （如果给定的对象唯1匹配，就返回1个bean实例。）
		 */
		MessagePrinter printer = context.getBean(MessagePrinter.class);
		/*
		 * 这就表示在通过contexnt.getBean(MessagePrinter.class)得到MessagePrinter的1个实例时，
		 * 会将对MessageService的依赖自动绑定到MessageService类，
		 * 
		 * 将查找所有能作为<bean/>元素的类(@Component @Configuration标注)或方法(@Bean标注)
		 */
		printer.printMessage();
	}
}
