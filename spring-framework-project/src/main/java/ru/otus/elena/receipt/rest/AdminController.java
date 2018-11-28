package ru.otus.elena.receipt.rest;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.elena.receipt.dao.RoleRepository;
import ru.otus.elena.receipt.domain.Role;
import ru.otus.elena.receipt.domain.User;
import ru.otus.elena.receipt.service.ReceiptService;
import ru.otus.elena.receipt.service.UserService;

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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MessageSource messageSource;
       @Autowired
    private ReceiptService receiptService;

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @RequestMapping("/login")
    public String loginPage(@RequestParam Optional<String> error, Model model) {
        String err = error.orElse("");
        model.addAttribute("message", err);
        loginCounter.increment();
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("message", "");
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
                        model.addAttribute("message", messageSource.getMessage("check.exists", new String[]{}, Locale.getDefault()));
                        logger.warn(messageSource.getMessage("check.exists", new String[]{}, Locale.getDefault()));
                        warnCounter.increment();
                        return "signup";
                    }
                    List<User> users = userService.findAll();
                    if (users.isEmpty()) {
                        userService.saveAdmin(username, password);
                        model.addAttribute("message", messageSource.getMessage("check.admin", new String[]{}, Locale.getDefault()));
                    } else {
                        Role roleAdmin = roleRepository.findByRole("ROLE_ADMIN");
                        User opt = users.stream().filter(u -> u.getRoles().contains(roleAdmin)).findAny().orElse(null);
                        if (opt != null) {
                            userService.saveUser(username, password);
                            model.addAttribute("message", "");
                        } else {
                            userService.saveAdmin(username, password);
                            model.addAttribute("message", messageSource.getMessage("check.admin", new String[]{}, Locale.getDefault()));
                        }
                    }
                    logger.info(messageSource.getMessage("check.success", new String[]{}, Locale.getDefault()));
                    model.addAttribute("message", "success");
                    signupCounter.increment();
                    return "login";
                }

            } else {
                model.addAttribute("message", messageSource.getMessage("check.invalid", new String[]{}, Locale.getDefault()));
                return "signup";
            }
        } catch (Exception e) {
            logger.error(messageSource.getMessage("check.exception", new String[]{e.getMessage()}, Locale.getDefault()));
            model.addAttribute("message", messageSource.getMessage("check.invalid", new String[]{}, Locale.getDefault()));
            errorCounter.increment();
            return "signup";
        }
    }

    @GetMapping("/logout")
    public String logOut(Model model) {
        loginCounter.increment(-1);
        return "login";
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        try {
            List<User> userlist = userService.findAll();
            model.addAttribute("check", receiptService.isCheckPicture());
            model.addAttribute("userlist", userlist);
            model.addAttribute("login", String.valueOf(loginCounter.count()));
            model.addAttribute("signup", String.valueOf(signupCounter.count()));
            model.addAttribute("warn", String.valueOf(warnCounter.count()));
            model.addAttribute("error", String.valueOf(errorCounter.count()));
            model.addAttribute("timer", String.valueOf(findAllTimer.totalTime(TimeUnit.MILLISECONDS)));
            logger.info(messageSource.getMessage("userlist.success", new String[]{}, Locale.getDefault()));
            return "admin";
        } catch (Exception e) {
            logger.error(messageSource.getMessage("userlist.exception", new String[]{e.getMessage()}, Locale.getDefault()));
            errorCounter.increment();
            model.addAttribute("userlist", new ArrayList<>());
            return "admin";
        }
    }

}
