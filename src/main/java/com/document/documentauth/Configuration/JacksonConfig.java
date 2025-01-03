package com.document.documentauth.Configuration;

import com.document.documentauth.Domain.Models.MessageWrapper;
import com.document.documentauth.Services.MessageWrapperDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(MessageWrapper.class, new MessageWrapperDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
