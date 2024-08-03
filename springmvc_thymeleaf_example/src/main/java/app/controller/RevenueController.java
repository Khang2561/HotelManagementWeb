package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RevenueController {
	@GetMapping("/revenue")
    public ModelAndView RevenuePage() {
		ModelAndView model = new ModelAndView("views/Revenue/Revenue");
		return model;
    }
}
