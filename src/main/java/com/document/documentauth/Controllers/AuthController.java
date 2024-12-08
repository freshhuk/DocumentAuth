package com.document.documentauth.Controllers;

import com.document.documentauth.Domain.Enums.MessageAction;
import com.document.documentauth.Domain.Models.LoginModel;
import com.document.documentauth.Domain.Models.MessageWrapper;
import com.document.documentauth.Domain.Models.RegisterModel;
import com.document.documentauth.Services.MessageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthController {

    private final MessageService messageService;
    @Autowired
    public AuthController(MessageService messageService){
        this.messageService = messageService;
    }

    @RabbitListener(queues = "queueAuthModel")
    public void receiveAuthRequest(MessageWrapper<?> message){
        if (message.getAction().equals(MessageAction.LOGIN.toString()) && message.getPayload() instanceof LoginModel) {
            messageService.login((LoginModel) message.getPayload());
        } else if (message.getAction().equals(MessageAction.REGISTER.toString()) && message.getPayload() instanceof RegisterModel) {
            messageService.registration((RegisterModel) message.getPayload());
        }
    }
}
