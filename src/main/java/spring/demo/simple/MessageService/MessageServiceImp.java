package spring.demo.simple.MessageService;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component("MessageServiceImp1")

@Service("MessageServiceImp1")
public class MessageServiceImp implements MessageService {

	public String getMessage() {
		return "1Hello World!";
	}

}
