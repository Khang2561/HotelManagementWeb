package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecieptConTroller {
	@GetMapping("/reciept")
    public ModelAndView recieptPage() {
		ModelAndView model = new ModelAndView("views/Reciept/Reciept");
		return model;
    }
}
