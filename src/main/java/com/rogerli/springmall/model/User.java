package com.rogerli.springmall.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Integer userId;
    private String email;
    private String password;
    private Date createdDate;
    private Date lastModifuedDate;
}
