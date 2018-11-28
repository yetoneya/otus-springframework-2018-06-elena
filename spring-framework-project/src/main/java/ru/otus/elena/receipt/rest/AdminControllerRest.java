package ru.otus.elena.receipt.rest;

import io.micrometer.core.instrument.Counter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.receipt.service.ReceiptService;
import ru.otus.elena.receipt.service.UserService;

@RestController
public class AdminControllerRest {
    
    Logger logger = LoggerFactory.getLogger(AdminControllerRest.class);
    
    @Autowired
    private UserService userService;
    @Autowired
    private Counter warnCounter;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ReceiptService receiptService;
    
    @RequestMapping("/user/delete")
    public ResponseEntity<List<String>> deleteUser(@RequestParam(value = "id") String id) {
        try {
            userService.deleteUserById(id);
            logger.info(messageSource.getMessage("deleteuser.success", new String[]{}, Locale.getDefault()));
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn(messageSource.getMessage("deleteuser.exception", new String[]{e.getMessage()}, Locale.getDefault()));
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/check/picture")
    public ResponseEntity<List<String>> setCheck(@RequestParam(value = "checked") boolean checked) {
        receiptService.setCheckPicture(checked);
        return new ResponseEntity<>(new ArrayList<String>() {
            {
                add("checkPicture changed");
            }
        }, HttpStatus.OK);
    }
    
}
