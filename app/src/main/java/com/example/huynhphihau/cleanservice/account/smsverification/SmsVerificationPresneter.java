package com.example.huynhphihau.cleanservice.account.smsverification;

import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by phihau on 5/16/2017.
 */

public class SmsVerificationPresneter implements SmsVerificationContract.SmsVerificationPresenter {

    SmsVerificationActivity mView;
    private CompositeDisposable mCompositeDisposable;

    public SmsVerificationPresneter(SmsVerificationActivity mView) {
        this.mView = mView;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void submitOtp(String otb, String phone) {
        final API mAPIService = GetAPIService.getAPIService();

        mView.showProgressDialog();
        if (TextUtils.isEmpty(otb) == true) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.otp_empty));
            return;
        }

        if (TextUtils.isEmpty(phone) == true) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.msg_input_username));
            return;
        }

//        mCompositeDisposable.add(mAPIService.updatePhone(otb, phone)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(Partner -> {
//                    BaseApplication.getInstance().setUser(Partner.getData());
//                    mView.showSmsVerificationSuccess();
//                }, e -> mView.handleError(e), () -> mView.hideProgressDialog())
//        );
    }

    @Override
    public void onStop() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
