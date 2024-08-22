package com.sujithwork.trainTicketing.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="Users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
}
