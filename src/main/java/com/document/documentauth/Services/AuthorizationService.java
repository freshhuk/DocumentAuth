package com.document.documentauth.Services;

import com.document.documentauth.Domain.Models.LoginModel;
import com.document.documentauth.Domain.Models.RegisterModel;
import com.document.documentauth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UserRepository repository;

    @Autowired
    public AuthorizationService(UserRepository repository){
        this.repository = repository;
    }

    /**
     * Method for register new User
     * @param registerModel registration model
     * @return status
     */
    public String userRegistration(RegisterModel registerModel){
        return null;
    }

    /**
     * Method for authorize User
     * @param loginModel login model
     * @return status
     */
    public String userLogin(LoginModel loginModel){
        return null;
    }
}
