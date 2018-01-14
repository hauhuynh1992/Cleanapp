package com.example.huynhphihau.cleanservice.auth.login;

import com.example.huynhphihau.cleanservice.base.BaseView;

/**
 * Created by phihau on 5/7/2017.
 */

public interface LoginContract {

    interface LoginView extends BaseView {
        void loginSuccess();
    }

    interface LoginPresenter {
        void login(String phone, String pass, String device_id, String device_token);
    }
}
