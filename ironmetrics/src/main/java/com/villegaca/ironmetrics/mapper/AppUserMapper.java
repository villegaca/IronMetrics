package com.villegaca.ironmetrics.mapper;

import com.villegaca.ironmetrics.dto.AppUser.AppUserResponse;
import com.villegaca.ironmetrics.model.AppUser;

public class AppUserMapper {
    public static AppUserResponse appUserResponse (AppUser user) {
        return new AppUserResponse (
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail()
        ); 
    }
}
