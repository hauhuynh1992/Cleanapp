package com.example.huynhphihau.cleanservice.auth.signup;

import com.example.huynhphihau.cleanservice.base.BaseView;

/**
 * Created by huynhphihau on 12/25/17.
 */

public class SignUpContract {

    interface SignUpView extends BaseView {
        void showSignUpSuccess();
    }

    interface SignUpPresenter {
        void doSignUp (String fistName, String lastName, String userName, String email, String pass, String confirmPass);

    }
}
