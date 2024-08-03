package app.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bean.Authorization;
import app.bean.User;
import app.dao.AuthorizationDAO;
import app.dao.UserDAO;

@Controller
public class LoginController {
    
    private AuthorizationDAO authorizationDAO;
    private UserDAO userDAO;
    
    @Autowired
    public LoginController(AuthorizationDAO authorizationDAO, UserDAO userDAO) {
        this.authorizationDAO = authorizationDAO;
        this.userDAO = userDAO;
    }
    
    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("views/login/Login");
    }
    
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            User u = userDAO.getUser(email, password);
            if (u != null) {
            	
                session.setAttribute("user", u);
                List<Authorization> listAuths = authorizationDAO.getAllAuthorization();
                session.setAttribute("listAuths", listAuths);
                return "redirect:/home";
            } else {
                model.addAttribute("message", "*Tài khoản hoặc mật khẩu không đúng!");
                return "views/login/Login";
            }
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute("message", "*Tài khoản hoặc mật khẩu không đúng!");
            return "views/login/Login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Đã xảy ra lỗi: " + e.getMessage());
            return "views/login/Login";
        }
    }
    
    
    @GetMapping("/test-database")
    public String testDatabaseConnection(Model model) {
        try {
            // Thực hiện một truy vấn đơn giản để kiểm tra kết nối
            List<User> users = userDAO.getAllUsers();
            model.addAttribute("message", "Database connection is successful! Number of users: " + users.size());
            return "views/login/database-test";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Database connection failed: " + e.getMessage());
            return "views/login/database-test";
        }
    }
}
