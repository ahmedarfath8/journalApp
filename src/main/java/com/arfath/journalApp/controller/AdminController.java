package com.arfath.journalApp.controller;

import com.arfath.journalApp.entity.User;
import com.arfath.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-admin")
    public void createAdmin(@RequestBody User user){
        userService.saveNewAdmin(user);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers = userService.getAll();
        if(allUsers != null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
