package com.arfath.journalApp.service;

import com.arfath.journalApp.entity.User;
import com.arfath.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // this is done for logging
    //private static final Logger log = LoggerFactory.getLogger(UserService.class);  //this whole line can be skipped by using @slf4j on the class //you can name instance anything but logger is better  // but i renamed it to log cuz log is instance when you use slf4j

    //creating an instance of BCrytpEncodeer to encode password sent by user
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));//for temp we will set the user role as role
            userRepository.save(user);
            return true;
        } catch (Exception e) {
//            log.info("error occurred"); //normal log
//            log.info("error occurred",e); //normal log with error
//            log.info("error occurred for {} ",user.getUserName()); //log with placeholder
//            log.info("error occurred for {} ",user.getUserName(),e); //log with placeholder and error
//
//            this is to check weather higher severity logs are enabled or not
//            by default info and higher is enabled
//            severity increases from low to high from top to bottom
//            only info and above is getting printed for all to be printed we must enable the trace cuz least severity ie log level
//            enabled trace in application.yml -- success all the levels are getting printed
              log.error("error occurred");

            return false;
        }
    }

    public void saveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));// we are giving both roles
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
    
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public void deleteUser(ObjectId id,String userName){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }
}
