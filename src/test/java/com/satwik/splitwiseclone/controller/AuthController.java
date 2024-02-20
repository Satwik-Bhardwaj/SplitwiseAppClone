package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.user.LoginResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    @RequestMapping("/")
    public @ResponseBody LoginResponse
}
