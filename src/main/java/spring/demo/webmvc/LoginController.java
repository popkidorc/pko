package spring.demo.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybatis.demo.config.User;
import mybatis.demo.config.UserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserDao mapper;

	@ModelAttribute("user")
	public User getUser(
			@RequestParam(value = "username", defaultValue = "") String username) {
		// TODO 去数据库根据用户名查找用户对象
		User user = mapper.findByUserName(username);
		user.setUserName(username);
		user.setPassword("123");
		return user;
	}

	@RequestMapping(value = "login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("user") User user) {
		String username = user.getUserName();
		logger.error("message " + username);

		ModelAndView mv = new ModelAndView("/login/form");
		mv.addObject("command_user", "LOGIN SUCCESS, " + user.getPassword());
		return mv;
	}

	// @Override
	// public ModelAndView handle(HttpServletRequest request,
	// HttpServletResponse response, Object command) throws Exception {
	// String username = ((LoginForm) command).getUsername();
	// logger.error("message " + username);
	// ModelAndView mv = new ModelAndView("/login/form");
	// mv.addObject("command_user", "LOGIN SUCCESS, " + username);
	// return mv;
	// }

	// @Override
	// public ModelAndView handleRequest(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// String name = request.getParameter("username");
	// ModelAndView mv = new ModelAndView("/login/form");
	// mv.addObject("command_user", "LOGIN SUCCESS, " + name);
	// return mv;
	// }

}
