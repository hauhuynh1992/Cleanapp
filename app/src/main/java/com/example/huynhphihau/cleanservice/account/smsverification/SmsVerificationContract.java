package com.example.huynhphihau.cleanservice.account.smsverification;

import com.example.huynhphihau.cleanservice.base.BaseView;

/**
 * Created by phihau on 5/16/2017.
 */

public interface SmsVerificationContract {

    interface SmsVerificationView extends BaseView {

        void showSmsVerificationSuccess();
    }

    interface SmsVerificationPresenter {
        void submitOtp(String otb, String phone);

        void onStop();
    }
}