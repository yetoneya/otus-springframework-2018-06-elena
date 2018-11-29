package ru.otus.elena.receipt.rest;

import io.micrometer.core.instrument.Counter;
import java.util.Collection;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.elena.receipt.domain.ReceiptDto;

@Controller
@ConfigurationProperties("application")
public class ReceiptController {

    Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private static final String IMAGE = "image/cucumber.jpg";
    @Autowired
    private Counter errorCounter;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/receipt")
    public String startPage(Model model) {
        try {
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
            ReceiptDto receipt = new ReceiptDto("", "", "", "", "", "");
            receipt.setImage(IMAGE);
            receipt.setUrl("");
            model.addAttribute("receipt", receipt);
            model.addAttribute("admin", isAdmin);
            logger.info(messageSource.getMessage("startpage.success", new String[]{}, Locale.getDefault()));
            return "rlist";
        } catch (Exception e) {
            logger.error(messageSource.getMessage("startpage.exception", new String[]{e.getMessage()}, Locale.getDefault()));
            errorCounter.increment();
            return "home";
        }

    }

    @RequestMapping("/manual")
    public String manualPage(Model model) {
        return "manual";
    }

}
