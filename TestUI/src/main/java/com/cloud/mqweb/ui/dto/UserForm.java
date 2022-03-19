package com.cloud.mqweb.ui.dto;

import lombok.Data;

@Data
public class UserForm {
    private final String userId;
    private final String userPw;

    public UserForm(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

}
