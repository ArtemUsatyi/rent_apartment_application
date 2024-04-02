package com.example.rent_apartment.service;

import com.example.rent_apartment.model.AuthModel;
import com.example.rent_apartment.model.RegisterUserInfoModel;

public interface AuthService {
    public String getRegistration(RegisterUserInfoModel registerModel);

    public String getAuth(AuthModel model);

    public void checkValidToken(String token);
}
