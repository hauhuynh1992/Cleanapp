package com.example.huynhphihau.cleanservice.account.editphone;

import com.example.huynhphihau.cleanservice.base.BaseView;

/**
 * Created by phihau on 5/15/2017.
 */

public interface ChangeMobileNumberContract {

    interface ChangeMobileNumberView extends BaseView {
        void verifySMS(String newMobileNumber);
    }

    interface ChangeMobileNumberPresenter {
        void changeMobileNumber(final String phone);
    }
}
