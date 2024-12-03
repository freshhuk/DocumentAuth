package com.document.documentauth.Services;

import com.document.documentauth.Domain.Enums.MessageAction;
import com.document.documentauth.Domain.Enums.QueueStatus;
import com.document.documentauth.Domain.Models.LoginModel;
import com.document.documentauth.Domain.Models.MessageWrapper;
import com.document.documentauth.Domain.Models.RegisterModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Value("${queueAuthStatus.name}")
    private String queueAuthStatusName;

    private final RabbitTemplate rabbitTemplate;

    private final AuthorizationService authService;

    @Autowired
    public MessageService(AuthorizationService authService, RabbitTemplate rabbitTemplate){
        this.authService = authService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void registration(RegisterModel registerModel){
        try{
            if(registerModel != null){

                String result = authService.userRegistration(registerModel);

                MessageWrapper<String> messageOk = new MessageWrapper<>(
                        MessageAction.REGISTER.toString(),
                        result);
                MessageWrapper<String> messageBad = new MessageWrapper<>(
                        MessageAction.REGISTER.toString(),
                        QueueStatus.BAD.toString());
                if(!result.isEmpty()){
                    sendMessage(messageOk);
                } else {
                    sendMessage(messageBad);
                }
            }
        } catch (Exception ex){
            MessageWrapper<String> messageBad = new MessageWrapper<>(
                    MessageAction.REGISTER.toString(),
                    QueueStatus.BAD.toString());
            System.out.println("Error with registration  " + ex);
            sendMessage(messageBad);
        }
    }

    public void login(LoginModel loginModel){
        try{
            if(loginModel != null){
                String result = authService.userLogin(loginModel);

                MessageWrapper<String> messageOk = new MessageWrapper<>(
                        MessageAction.LOGIN.toString(),
                        result);
                MessageWrapper<String> messageBad = new MessageWrapper<>(
                        MessageAction.LOGIN.toString(),
                        QueueStatus.BAD.toString());
                if(!result.isEmpty()){
                    sendMessage(messageOk);
                } else {
                    sendMessage(messageBad);
                }
            }
        } catch (Exception ex){
            MessageWrapper<String> messageBad = new MessageWrapper<>(
                    MessageAction.LOGIN.toString(),
                    QueueStatus.BAD.toString());
            System.out.println("Error with login  " + ex);
            sendMessage(messageBad);
        }
    }

    /**
     * Method sends message on queueAuthStatusName queue
     * @param object - wrapper message
     */
    private void sendMessage( MessageWrapper<?> object) {
        rabbitTemplate.convertAndSend(queueAuthStatusName, object);
    }
}
