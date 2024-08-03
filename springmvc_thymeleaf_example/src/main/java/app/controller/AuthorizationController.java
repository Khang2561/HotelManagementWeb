package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthorizationController {
	@GetMapping("/authorizition")
    public ModelAndView showAuthorzitionPage() {
		ModelAndView model = new ModelAndView("views/Authorization/Authorization");
		return model;
    }
}
