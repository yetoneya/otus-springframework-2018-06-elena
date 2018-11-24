package ru.otus.elena.receipt.rest;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReceiptController {

    Logger logger = LoggerFactory.getLogger(ReceiptController.class);

    @RequestMapping("/receipt")
    public String startPage(Model model) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
        boolean isAdmin = false;
        for (GrantedAuthority authority : authorities) {
            String au = authority.getAuthority();
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        model.addAttribute("admin", isAdmin);
        logger.info("startPage was successful");
        return "rlist";

    }

    @RequestMapping("/manual")
    public String manualPage(Model model) {
        return "manual";
    }

}
