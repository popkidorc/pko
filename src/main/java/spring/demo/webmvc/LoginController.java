package spring.demo.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping(value = "login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, LoginForm command) {
		String username = command.getUsername();
		System.out.println(username);
		ModelAndView mv = new ModelAndView("/login/form");
		mv.addObject("command_user",
				"LOGIN SUCCESS, " + username);
		return mv;
	}
}
