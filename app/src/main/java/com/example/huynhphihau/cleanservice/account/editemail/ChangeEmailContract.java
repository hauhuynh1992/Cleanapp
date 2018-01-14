package com.example.huynhphihau.cleanservice.account.editemail;

import com.example.huynhphihau.cleanservice.base.BaseView;

/**
 * Created by phihau on 5/15/2017.
 */

public interface ChangeEmailContract {

    interface ChangeEmailView extends BaseView {

        void showChangeEmailSuccess();
    }

    interface ChangeNewPasswordPresenter {
        void changeEmail(String email);
    }
}