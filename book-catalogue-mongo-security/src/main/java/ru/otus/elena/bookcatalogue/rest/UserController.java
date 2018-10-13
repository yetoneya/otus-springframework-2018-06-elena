package ru.otus.elena.bookcatalogue.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.elena.bookcatalogue.domain.User;
import ru.otus.elena.bookcatalogue.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userlist")
    public String userList(Model model) {
        List<User> userlist = userService.findAll();
        model.addAttribute("userlist", userlist);
        return "users";
    }

}
