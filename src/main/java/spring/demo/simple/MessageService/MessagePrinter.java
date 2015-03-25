package spring.demo.simple.MessageService;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

//@Component  ���ڱ�ע1����
@Component
public class MessagePrinter {

	// @Resource ���ڱ�עsetter���������Ա�ȵȣ�����ָ����ע�룻javax.annotation.Resource
	@Resource(name = "MessageServiceImp2")
	private MessageService service;

	// @Autowired
	// ���ڱ�עsetter�������������������������ķ��������ϵȵȣ������Զ���ע�룻org.springframework.beans.factory.annotation.Autowired
	// @Autowired
	public MessagePrinter(MessageService service) {
		this.service = service;
	}

	public MessagePrinter() {
	}

	public void printMessage() {
		// System.out.println(this.service.getMessage());
	}
}
