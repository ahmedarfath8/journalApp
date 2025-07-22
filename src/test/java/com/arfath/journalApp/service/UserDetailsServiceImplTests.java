package com.arfath.journalApp.service;

import com.arfath.journalApp.entity.User;
import com.arfath.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*; //this import must be explicitly written

//here we are learning mockito this is the faster way of doing it
// how ?  by using @InjectMocks and @Mock here what happen is spring doesnt start at all instead it uses injectmock to create a copy of  all the dependencies and  in the field  which you have created as mocks and then uses we inject all the mocks here  ie why its mocks not mock (cuz all the dependencies and everything could be differnt mock)
@Disabled
public class UserDetailsServiceImplTests {

    @InjectMocks //
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;


    @BeforeEach //this method is key very important in both the methods
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsernameTest(){
        User mockUser = User.builder()
                .userName("dummy")
                .password("asdfg")
                .roles(new ArrayList<>())
                .build();
        //when(userRepository.findByUserName(anyString())).thenReturn(User.builder().userName("ram").password("asfgfadsgfdsgfgf").roles(new ArrayList<>()).build()); //mockito is static so you need to call methods directly ArgumentMatcher.anyString()
        when(userRepository.findByUserName(anyString())).thenReturn(mockUser); //mockito is static so you need to call methods directly anyString() instead of this ArgumentMatcher.anyString()
        UserDetails user = userDetailsService.loadUserByUsername("dummy"); //now this loadUBU will call userRepo that we will mock
        Assertions.assertNotNull(user);  //here we need to use Assertions.assertNotNull() not assertNotNull() cuz junit stattic class is not imported  import static org.junit.jupiter.api.Assertions.*;


    }
}
