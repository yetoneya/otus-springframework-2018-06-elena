package ru.otus.elena.bookcatalogue.rest;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.elena.bookcatalogue.domain.User;
import ru.otus.elena.bookcatalogue.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @RequestMapping("/login")
    public String loginPage(@RequestParam Optional<String> error, Model model) {
        String err = error.orElse("privet");
        model.addAttribute("label", err);
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("label", "try");
        return "signup";
    }

    @RequestMapping("/check")
    public String check(@RequestParam String username, @RequestParam String birthday,
            @RequestParam String password, @RequestParam String repeatpassword, Model model) {
        try {
            if (((username != "") && (birthday != null) && (password != "")
                    && (repeatpassword != "")) && (password.equalsIgnoreCase(repeatpassword))) {
                {
                    User user = userService.findByUsername(username);
                    if (user != null) {
                        model.addAttribute("label", "username already exists");
                        return "signup";
                    }

                    userService.saveUser(username, birthday, password);
                    model.addAttribute("label", "Log in:");
                    return "login";
                }

            } else {
                model.addAttribute("label", "invalid data");
                return "signup";
            }
        } catch (Exception e) {
            model.addAttribute("label", "invalid data");
            return "signup";
        }
    }

    @GetMapping("/logout")
    public String logOut(Model model) {
        return "login";
    }

    @GetMapping("/userlist")
    public String userList(Model model) {
        List<User> userlist = userService.findAll();
        model.addAttribute("userlist", userlist);
        return "users";
    }

}
