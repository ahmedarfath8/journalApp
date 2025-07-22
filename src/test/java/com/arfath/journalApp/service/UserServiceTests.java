package com.arfath.journalApp.service;

import com.arfath.journalApp.entity.User;
import com.arfath.journalApp.repository.UserRepository;
import org.apache.logging.log4j.message.Message;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
@SpringBootTest //this annotation is very crucial to do component scan and create beans store it in application context and which can be used here like this bean userRepository
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void sampletests(){
        assertEquals(5,3+2);
        assertTrue(5>2);
    }

    @Disabled
    @Test
    public void testfindByUserName(){ //output we are getting is test passed 1  whole method is considered as one test
        //you can have as many lines of assertsomthing  you want all will be considered one test
        assertNotNull(userRepository.findByUserName("oggy")); //testing if the value is not null
    }


    @Test
    public void checkUserEnties(){
        User user = userRepository.findByUserName("oggy");
        assertTrue(user.getJournalEntries() !=null);
        User user1 = userRepository.findByUserName("admin");
        assertTrue(user1.getJournalEntries().isEmpty()); //its should be empty cuz in realty admin has no jounalEntries
    }
    //the test above we will do it in parameterized format

    @ParameterizedTest
    @CsvSource({
            "jack",
            "oggy",
            "olivia", //this user is not available in db
            "admin"
    })
    public void checkingUser(String username){
        assertNotNull(userRepository.findByUserName(username),"failed for : "+username); //you can also send a message here by putting comma and sending string
    }
    //this is example for parametrized testing

    @ParameterizedTest
    @CsvSource({
            "10,5,5",
            "5,2,3",
            "12,6,2", //these are all the parameters which will be given as input in every iteration
            "13,8,5"
    })
    public void sampletest2(int expected , int a,int b){
        assertEquals(expected,a+b);
    }
}
