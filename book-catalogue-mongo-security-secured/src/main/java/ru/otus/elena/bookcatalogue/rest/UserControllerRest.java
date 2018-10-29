package ru.otus.elena.bookcatalogue.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.bookcatalogue.service.UserService;

@RestController
public class UserControllerRest {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/delete")
    public ResponseEntity<List<String>> deleteUser(@RequestParam(value = "id") String id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

    @RequestMapping("/user/refresh")
    public ResponseEntity<List<String>> refreshUser(@RequestParam(value = "id") String id) {
        try {
            userService.refreshUser(id);
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<String>(), HttpStatus.OK);
        }
    }

}
