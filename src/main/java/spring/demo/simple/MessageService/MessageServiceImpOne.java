package spring.demo.simple.MessageService;

import org.springframework.stereotype.Component;

@Component("MessageServiceImp2")
public class MessageServiceImpOne implements MessageService {

	public String getMessage() {
		return "2Hello World!";
	}

}
