package com.example.huynhphihau.cleanservice.account.editpassword;

import com.example.huynhphihau.cleanservice.base.BaseView;

/**
 * Created by phihau on 5/14/2017.
 */

public interface ChangeNewPasswordContract {

    interface ChangeNewPasswordView extends BaseView {

        void showChangeNewPasswordSuccess();

        void showVerifyPassSuccess();
    }

    interface ChangeNewPasswordPresenter {
        void changeNewPassword(String newPassword, String confirmPassword);

        void verifyPassword(String phone, String pass, String device_id, String device_token);
    }
}
