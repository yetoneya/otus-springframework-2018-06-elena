package ru.otus.elena.receipt.rest;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.receipt.service.UserService;

@RestController
public class AdminControllerRest {

    Logger logger = LoggerFactory.getLogger(AdminControllerRest.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/user/delete")
    public ResponseEntity<List<String>> deleteUser(@RequestParam(value = "id") String id) {
        try {
            userService.deleteUserById(id);
            logger.info("userDelete was successful");
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("exception in deleteUser "+e.getMessage());         
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

}
