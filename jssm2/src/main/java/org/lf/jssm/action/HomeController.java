package org.lf.jssm.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	@RequestMapping("home.do")
	public String home() {
		return "home";
	}

	@RequestMapping("welcome.do")
	public String welcome() {
		return "welcome";
	}
}
