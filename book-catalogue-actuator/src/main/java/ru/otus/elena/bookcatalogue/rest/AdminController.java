package ru.otus.elena.bookcatalogue.rest;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.elena.bookcatalogue.domain.User;
import ru.otus.elena.bookcatalogue.service.UserService;

@Controller
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private Counter loginCounter;
    @Autowired
    private Counter signupCounter;
    @Autowired
    private Counter errorCounter;
    @Autowired
    private Counter warnCounter;
        @Autowired
    private Timer findAllTimer;
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
        loginCounter.increment();
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
                        logger.warn("user registration wasn't successful");
                        warnCounter.increment();
                        return "signup";
                    }

                    userService.saveUser(username, birthday, password);
                    model.addAttribute("label", "Log in:");
                    logger.info("user's registration was successful");
                    signupCounter.increment();
                    return "login";
                }

            } else {
                model.addAttribute("label", "invalid data");
                return "signup";
            }
        } catch (Exception e) {
            logger.error("exception in check");
            errorCounter.increment();
            model.addAttribute("label", "invalid data");
            return "signup";
        }
    }

    @GetMapping("/logout")
    public String logOut(Model model) {
        return "login";
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        try {
            List<User> userlist = userService.findAll();
            model.addAttribute("userlist", userlist);
            model.addAttribute("login", String.valueOf(loginCounter.count()));
            model.addAttribute("signup", String.valueOf(signupCounter.count()));
            model.addAttribute("warn", String.valueOf(warnCounter.count()));
            model.addAttribute("error", String.valueOf(errorCounter.count()));
            model.addAttribute("timer", String.valueOf(findAllTimer.totalTime(TimeUnit.MILLISECONDS)));
            return "admin";
        } catch (Exception e) {
            logger.error("exception in userList");
            errorCounter.increment();
            model.addAttribute("userlist", new ArrayList<User>());
            return "admin";
        }
    }

}
