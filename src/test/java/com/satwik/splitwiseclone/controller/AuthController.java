package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.user.LoginResponse;
import com.satwik.splitwiseclone.persistence.dto.user.PhoneDTO;
import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import org.mockito.Mock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

//    @Mock
//    private User

//    @RequestMapping("/")
//    public @ResponseBody LoginResponse

    private RegisterUserRequest givenRegisterUserRequest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("satwikbhardwaj123@gmail.com");
        request.setPassword("fun123");
        request.setUsername("satwik-bhardwaj");
        request.setPhone(new PhoneDTO("+91", 7_877_878_343L));
        return request;
    }

}
