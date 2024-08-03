package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BillForRentController {
	@GetMapping("/bill-for-rent")
    public ModelAndView BillForRentPage() {
		ModelAndView model = new ModelAndView("views/BillForRent/BillForRent");
		return model;
    }
}
