package test.demo.spring.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.aop.demo.advice.service.IAopDemoService;

public class AopTest {

	@Test
	public void doTest() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:config/spring/context/applicationContext-root.xml");
		IAopDemoService appAopDemoService = (IAopDemoService) applicationContext
				.getBean("testAOP");
		appAopDemoService.doSomethings();
	}
}
