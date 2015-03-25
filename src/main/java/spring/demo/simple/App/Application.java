package spring.demo.simple.App;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import spring.demo.simple.MessageService.MessagePrinter;

//@Configuration ���ڱ�ע1���࣬��ʾbean�����Դ�ļ���source����
//@ComponentScan ���ڱ�ע1���࣬��ʾɨ��ָ�����е�@Component��ע���࣬������Щ��ע��ΪSpring IoC������bean,���൱��1��<bean/>Ԫ�أ�
@Configuration
@ComponentScan(basePackages = { "spring.demo.simple.MessageService",
		"demo.mybatis.dao" })
public class Application {

	/**
	 * �����ڲ���ʵ����MessageService�ӿڣ�����getMessage()�����з����ˡ�hello,world!�
	 * ��ַ�
	 * 
	 * @Bean 
	 *       ���ڱ�ע1����������ʾ1������ʵ�����û��ʼ��1���µĶ���(Object)���������Spring
	 *       �Ŀ��Ʒ�ת(IoC)�������? �൱��Spring
	 *       <bean/>XML�����ļ���<bean/>Ԫ�ء������ٷ��ĵ���
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
		 * ApplicationContext�Ƕ�1�����ã�Application)�ṩ���õĺ��Ľӿڣ�
		 * AnnotationConfigApplicationContext�����1��ʵ�֣�
		 * 
		 * ������������@Configuration,@Component��JSR��30��ע���ࡣ
		 * 
		 * ����App.javaʹ��@Configuration�����˱�ע
		 * ��App.class������ΪAnnotationConfigApplicationContext�������ĵĲ���
		 */
		ApplicationContext context = new AnnotationConfigApplicationContext(
				Application.class);
		/*
		 * ������ͨ��context��getBean(Class<T>)�����õ���MessagePrinter��1��ʵ��
		 * Ϊ���صõ�MessagePrinter���1��ʵ���أ�
		 * 
		 * ��getBean(Class<T>)��API���ͣ�
		 * 
		 * Return the bean instance that uniquely matches the given object type,
		 * if any. ������Ķ���Ψ1ƥ�䣬�ͷ���1��beanʵ��
		 */
		MessagePrinter printer = context.getBean(MessagePrinter.class);
		/*
		 * ��ͱ�ʾ��ͨ��contexnt.getBean(MessagePrinter.class)�õ�MessagePrinter��1��
		 * ʵ��ʱ�� �Ὣ��MessageService�������Զ��󶨵�MessageService�࣬
		 * 
		 * ��������������Ϊ<bean/>Ԫ�ص���(@Component
		 * @Configuration��ע)�򷽷�(@Bean��ע)
		 */
		printer.printMessage();
	}
}
