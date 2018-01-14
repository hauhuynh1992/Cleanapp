package com.example.huynhphihau.cleanservice.account.editname;

import com.example.huynhphihau.cleanservice.base.BaseView;

/**
 * Created by huynhphihau on 11/24/17.
 */

public interface ChangeNameContract {

    interface ChangeNameView extends BaseView {

        void showChangeNameSuccess();
    }

    interface ChangeNamePresenter {
        void changeName(String name);
    }
}