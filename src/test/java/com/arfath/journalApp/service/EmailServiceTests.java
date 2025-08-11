package com.arfath.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testMail(){
        emailService.sendEmail("arfathahmed0077@gmail.com","testing email using spring boot","well well well its working");
    }
}
