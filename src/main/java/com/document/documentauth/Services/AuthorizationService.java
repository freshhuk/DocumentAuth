package com.document.documentauth.Services;

import com.document.documentauth.Domain.Entity.User;
import com.document.documentauth.Domain.Models.LoginModel;
import com.document.documentauth.Domain.Models.RegisterModel;
import com.document.documentauth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService {

    private final UserRepository repository;
    private final JWTService jwtService;
    private final PasswordEncoder  passwordEncoder;

    @Autowired
    public AuthorizationService(UserRepository repository, PasswordEncoder passwordEncoder, JWTService jwtService){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Method for register new User
     * @param registerModel registration model
     * @return jwt token
     */
    public String userRegistration(RegisterModel registerModel){
        try{
            if(registerModel != null && registerModel.getPassword().equals(registerModel.getConfirmPassword())){

                Optional<User> user = repository.getUserByLogin(registerModel.getLoginRegister());
                if(user.isEmpty()){
                    String encodedPassword = passwordEncoder.encode(registerModel.getPassword());

                    User newUser = new User();
                    newUser.setLogin(registerModel.getLoginRegister());
                    newUser.setPassword(encodedPassword);

                    repository.add(newUser);

                    return jwtService.generateToken(newUser);
                } else{
                    System.out.println("User already created");
                }
            }
            System.out.println("Error with data");
            return null;
        } catch (Exception ex){
            System.out.println("Error with method " + ex);
            return null;
        }

    }

    /**
     * Method for authorize User
     * @param loginModel login model
     * @return jwt token
     */
    public String userLogin(LoginModel loginModel){

       try{
           if (loginModel != null){

               Optional<User> user = repository.getUserByLogin(loginModel.getLogin());
               if(user.isPresent()){
                  return jwtService.generateToken(user.get());
               } else{
                   System.out.println("User wasn't found");
               }
           }
           return null;

       } catch (Exception ex){
           System.out.println("Error with login " + ex);
           return  null;
       }
    }
}
