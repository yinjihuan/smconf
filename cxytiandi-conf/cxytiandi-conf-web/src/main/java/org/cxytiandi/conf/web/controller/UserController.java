package org.cxytiandi.conf.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cxytiandi.conf.web.domain.User;
import org.cxytiandi.conf.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 用户控制器
 * @author yinjihuan
 * @date 2017-02-21
 */
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/login")
	public Object login() {
		return "user/login";
	}
	
	@PostMapping("/user/login")
	public Object login(String username, String pass, Map<String, String> model, HttpServletRequest request) {
		User user = userService.getUser(username, pass);
		if (user == null) {
			model.put("msg", "账号或者密码错误");
			return "user/login"; 
		}
		request.getSession().setAttribute("login_user_name", user.getUsername());
		request.getSession().setAttribute("login_user_envs", user.getEnvs());
		return "redirect:/";
	}
	
	@GetMapping("/user/logout")
	public Object logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/user/login";
	}
}
