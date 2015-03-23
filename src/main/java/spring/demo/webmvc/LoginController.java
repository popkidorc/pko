package spring.demo.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//@Controller
public class LoginController implements Controller {
	static Logger logger = LoggerFactory.getLogger(LoginController.class);

	// // @RequestMapping(value = "login")
	// public ModelAndView login(HttpServletRequest request,
	// HttpServletResponse response, LoginForm command) {
	// String username = command.getUsername();
	// logger.error("message " + username);
	// ModelAndView mv = new ModelAndView("/login/form");
	// mv.addObject("command_user", "LOGIN SUCCESS, " + username);
	// return mv;
	// }

	// @Override
	// public ModelAndView handle(HttpServletRequest request,
	// HttpServletResponse response, Object command) throws Exception {
	// String username = ((LoginForm) command).getUsername();
	// logger.error("message " + username);
	// ModelAndView mv = new ModelAndView("/login/form");
	// mv.addObject("command_user", "LOGIN SUCCESS, " + username);
	// return mv;
	// }

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/login/form");
		mv.addObject("command_user", "LOGIN SUCCESS, ");
		return mv;
	}

}
