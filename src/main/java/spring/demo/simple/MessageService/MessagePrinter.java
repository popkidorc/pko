package spring.demo.simple.MessageService;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

//@Component  用于标注1个类
@Component
public class MessagePrinter {

	// @Resource 用于标注setter方法，类成员等等，用于指定绑定注入；javax.annotation.Resource
	@Resource(name = "MessageServiceImp2")
	private MessageService service;

	// @Autowired
	// 用于标注setter方法，构造器，包括多个参数的方法，集合等等，用于自动绑定注入；org.springframework.beans.factory.annotation.Autowired
	// @Autowired
	public MessagePrinter(MessageService service) {
		this.service = service;
	}

	public MessagePrinter() {
	}

	public void printMessage() {
		System.out.println(this.service.getMessage());
	}
}
