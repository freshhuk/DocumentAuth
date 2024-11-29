package com.document.documentauth.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Value("${queueAuthModel.name}")
    private String queueAuthModelName;

    private final AuthorizationService authService;

    @Autowired
    public MessageService(AuthorizationService authService){
        this.authService = authService;
    }

    public void registration(){

    }

    public void login(){

    }


}
