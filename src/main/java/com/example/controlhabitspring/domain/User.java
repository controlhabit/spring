package com.example.controlhabitspring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="user_tb")

public class User {
    @Id    
    @Column(name = "u_id")
    private String userId;

    @Column(name = "u_email")
    private String userEmail;

    @Column(name = "u_passwd")
    private String userPw;

    @Column(name = "u_name")
    private String userName;

    @Column(name = "u_date")
    private Timestamp userDate;
    
}
