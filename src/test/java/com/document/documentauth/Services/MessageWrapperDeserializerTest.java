package com.document.documentauth.Services;

import com.document.documentauth.Domain.Models.LoginModel;
import com.document.documentauth.Domain.Models.MessageWrapper;
import com.document.documentauth.Domain.Models.RegisterModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageWrapperDeserializerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(MessageWrapper.class, new MessageWrapperDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    void testDeserializeWithLoginModel() throws JsonProcessingException {
        // Arrange
        String json = """
                {
                    "action": "LOGIN",
                    "payload": {
                        "login": "testLogin",
                        "password": "testPassword"
                    }
                }
                """;

        // Act
        MessageWrapper<?> result = objectMapper.readValue(json, MessageWrapper.class);

        // Assert
        assertNotNull(result);
        assertEquals("LOGIN", result.getAction());
        assertTrue(result.getPayload() instanceof LoginModel);

        LoginModel payload = (LoginModel) result.getPayload();
        assertEquals("testLogin", payload.getLogin());
        assertEquals("testPassword", payload.getPassword());
    }

    @Test
    void testDeserializeWithRegisterModel() throws JsonProcessingException {
        // Arrange
        String json = """
                {
                    "action": "REGISTER",
                    "payload": {
                        "loginRegister": "testUser",
                        "password": "password123",
                        "confirmPassword": "password123"
                    }
                }
                """;

        // Act
        MessageWrapper<?> result = objectMapper.readValue(json, MessageWrapper.class);

        // Assert
        assertNotNull(result);
        assertEquals("REGISTER", result.getAction());
        assertTrue(result.getPayload() instanceof RegisterModel);

        RegisterModel payload = (RegisterModel) result.getPayload();
        assertEquals("testUser", payload.getLoginRegister());
        assertEquals("password123", payload.getPassword());
        assertEquals("password123", payload.getConfirmPassword());
    }

    @Test
    void testDeserializeWithStringPayload() throws JsonProcessingException {
        // Arrange
        String json = """
                {
                    "action": "ECHO",
                    "payload": "Simple string payload"
                }
                """;

        // Act
        MessageWrapper<?> result = objectMapper.readValue(json, MessageWrapper.class);

        // Assert
        assertNotNull(result);
        assertEquals("ECHO", result.getAction());
        assertTrue(result.getPayload() instanceof String);
        assertEquals("Simple string payload", result.getPayload());
    }

    @Test
    void testDeserializeWithNullPayload() throws JsonProcessingException {
        // Arrange
        String json = """
                {
                    "action": "PING"
                }
                """;

        // Act
        MessageWrapper<?> result = objectMapper.readValue(json, MessageWrapper.class);

        // Assert
        assertNotNull(result);
        assertEquals("PING", result.getAction());
        assertNull(result.getPayload());
    }

    @Test
    void testDeserializeWithUnknownPayload() throws JsonProcessingException {
        // Arrange
        String json = """
                {
                    "action": "UNKNOWN",
                    "payload": {
                        "unexpectedField": "someValue"
                    }
                }
                """;

        // Act
        MessageWrapper<?> result = objectMapper.readValue(json, MessageWrapper.class);

        // Assert
        assertNotNull(result);
        assertEquals("UNKNOWN", result.getAction());
        assertNotNull(result.getPayload());
        assertTrue(result.getPayload() instanceof Object); // Default case for unknown payload
    }
}