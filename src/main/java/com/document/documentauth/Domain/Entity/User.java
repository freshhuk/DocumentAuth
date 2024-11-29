package com.document.documentauth.Domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doc_user")
public class User {

    @Id
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

}
