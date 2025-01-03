package com.document.documentauth.Domain.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginModel {
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;
}
