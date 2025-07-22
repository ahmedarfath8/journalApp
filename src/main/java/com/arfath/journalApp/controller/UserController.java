package com.arfath.journalApp.controller;


import com.arfath.journalApp.entity.User;
import com.arfath.journalApp.repository.UserRepository;
import com.arfath.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //this will get the auth details
        String userName = authentication.getName(); //here using auth details we can get username
        User userInDb = userService.findByUserName(userName);
            userInDb.setUserName(user.getUserName()); //no need of if here cuz once the user is auth there no need for if to verify weather the user is present or not
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}





    /*
    //mycode
    @Autowired
    private JournalEntryService journalEntryService;


    //mycode version2 (with just userName parameter)
    //this method will delete all the entries of the user and then  the user also
    @DeleteMapping("/{id}/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName){
        journalEntryService.deleteAllEntriesOfUser(userName);
        User userInDb = userService.findByUserName(userName);
        userService.deleteById(userInDb.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    //mycode version1 (id,userName as parameter)
//    @DeleteMapping("/{id}/{userName}")
//    public ResponseEntity<?> deleteUser(@PathVariable ObjectId  id,@PathVariable String userName){
//        journalEntryService.deleteAllEntriesOfUser(userName);
//        userService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
    @DeleteMapping("id/{id}")
    public void deleteUserById(@PathVariable ObjectId id) {
        userService.deleteById(id);
    }
    */


