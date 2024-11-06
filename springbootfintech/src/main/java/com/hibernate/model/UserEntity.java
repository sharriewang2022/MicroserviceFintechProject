package com.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
   
    @Id
    @Column(length = 20)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gg")
    @GenericGenerator(name = "gg", strategy = "top.lingkang.springboothibernate.config.PrimaryGenerator")
    private String id;
   
    @Column(length = 40)
    private String username;
    
    @Column(length = 64)
    private String password;
    
    @Column(length = 20)
    private String nickname;
    
    @Column(name = "create_time")
    private Date createTime;
}
