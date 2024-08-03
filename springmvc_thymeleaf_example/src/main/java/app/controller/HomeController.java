package app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.bean.User;



@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String handleHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // Redirect to login if no user is found in session
            return "redirect:/login";
        } else {
            // Forward to the index.jsp view
            return "views/Index/Index"; // Ensure that "Index" corresponds to /WEB-INF/views/Index.jsp or the equivalent view resolver configuration
        }
    }
}
