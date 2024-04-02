package com.example.rent_apartment.controller;

import com.example.rent_apartment.model.AuthModel;
import com.example.rent_apartment.model.RegisterUserInfoModel;
import com.example.rent_apartment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.rent_apartment.constans.RentApartmentConst.AUTH;
import static com.example.rent_apartment.constans.RentApartmentConst.REGISTRATION;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(REGISTRATION)
    public String registration(@RequestBody RegisterUserInfoModel registerModel) {

        return authService.getRegistration(registerModel);
    }

    @PostMapping(AUTH)
    public String authorization(@RequestBody AuthModel authModel){

        return authService.getAuth(authModel);
    }

}
