package com.document.documentauth.Services;

import com.document.documentauth.Domain.Entity.User;
import com.document.documentauth.Domain.Models.LoginModel;
import com.document.documentauth.Domain.Models.RegisterModel;
import com.document.documentauth.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {
    @Mock
    private  UserRepository repository;
    @Mock
    private  JWTService jwtService;
    @Mock
    private  PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthorizationService service;

    //Test Object
    private RegisterModel registerModel;
    private LoginModel loginModel;

    @BeforeEach
    void initModel(){
        registerModel = new RegisterModel(
                "Test",
                "Password",
                "Password");
        loginModel = new LoginModel(
                "Test",
                "Password");
    }

    @Test
    void userRegistrationTest(){

        User testUser = new User();{
            testUser.setId(0);
            testUser.setLogin(registerModel.getLoginRegister());
            testUser.setPassword("encoded password");
        }

        Mockito.when(repository.existsByUsername("Test")).thenReturn(true);
        Mockito.when(jwtService.generateToken(testUser)).thenReturn("jwt");
        Mockito.when(passwordEncoder.encode(registerModel.getPassword())).thenReturn("encoded password");

        var result = service.userRegistration(registerModel);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, "jwt");
    }
    @Test
    void userRegistrationUserExistsTest(){

        Mockito.when(repository.existsByUsername("Test")).thenReturn(false);

        var result = service.userRegistration(registerModel);
        Assertions.assertNull(result);
    }
    @Test
    void userRegistrationErrorPasswordTest(){

        registerModel.setConfirmPassword("Another password");

        var result = service.userRegistration(registerModel);
        Assertions.assertNull(result);
    }

    @Test
    void userLoginTest(){

        User testUser = new User();{
            testUser.setId(0);
            testUser.setLogin(loginModel.getLogin());
            testUser.setPassword("encoded password");
        }
        Optional<User> optionalUser = Optional.of(testUser);


        Mockito.when(repository.getUserByLogin("Test")).thenReturn(optionalUser);
        Mockito.when(jwtService.generateToken(testUser)).thenReturn("jwt");

        var result = service.userLogin(loginModel);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result,"jwt");
    }
    @Test
    void userLoginNotFoundTest(){

        Mockito.when(repository.getUserByLogin("Test")).thenReturn(Optional.empty());

        var result = service.userLogin(loginModel);

        Assertions.assertNull(result);
    }
    @Test
    void userLoginNullTest(){

        var result = service.userLogin(null);

        Assertions.assertNull(result);
    }
}
