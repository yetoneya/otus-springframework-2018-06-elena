package ru.otus.elena.receipt.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.elena.receipt.dao.RoleRepository;
import ru.otus.elena.receipt.domain.Role;
import ru.otus.elena.receipt.domain.User;
import ru.otus.elena.receipt.service.UserService;

@Controller
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

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
    public String check(@RequestParam String username,
            @RequestParam String password, @RequestParam String repeatpassword, Model model) {
        try {
            if (((username != "") && (password != "")
                    && (repeatpassword != "")) && (password.equalsIgnoreCase(repeatpassword))) {
                {
                    User user = userService.findByUsername(username);
                    if (user != null) {
                        model.addAttribute("label", "username already exists");
                        logger.warn("user registration wasn't successful");
                        return "signup";
                    }
                    List<User> users = userService.findAll();
                    if (users.isEmpty()) {
                        userService.saveAdmin(username, password);
                        model.addAttribute("message", "You are admin!");
                    } else {
                        Role roleAdmin = roleRepository.findByRole("ROLE_ADMIN");
                        User opt = users.stream().filter(u -> u.getRoles().contains(roleAdmin)).findAny().orElse(null);
                        if (opt != null) {
                            userService.saveUser(username, password);
                            model.addAttribute("message", "");
                        } else {
                            userService.saveAdmin(username, password);
                            model.addAttribute("message", "You are admin!");
                        }
                    }
                    logger.info("user's registration was successful");
                    model.addAttribute("label", "Log in:");
                    return "login";
                }

            } else {
                model.addAttribute("label", "invalid data");
                return "signup";
            }
        } catch (Exception e) {
            logger.error("exception in check");
            model.addAttribute("label", "invalid data");
            return "signup";
        }
    }

    @GetMapping("/logout")
    public String logOut(Model model) {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        try {
            List<User> userlist = userService.findAll();
            model.addAttribute("userlist", userlist);
            return "admin";
        } catch (Exception e) {
            logger.error("exception in userList");
            model.addAttribute("userlist", new ArrayList<>());
            return "admin";
        }
    }

}
