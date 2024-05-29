package com.foody.data.model;

import org.springframework.data.annotation.Id;


//@Table("users")
public record User(@Id Long userId, String username, String email, String passwordHash) {}
