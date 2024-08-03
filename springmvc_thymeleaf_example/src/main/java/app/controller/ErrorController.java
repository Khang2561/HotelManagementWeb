package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
	@GetMapping("/error")
	public ModelAndView ErrorPage() {
		ModelAndView model = new ModelAndView("views/Error/Error");
		return model;
	}
}
