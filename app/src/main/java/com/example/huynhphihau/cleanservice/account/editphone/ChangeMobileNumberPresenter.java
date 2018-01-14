package com.example.huynhphihau.cleanservice.account.editphone;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;
import com.example.huynhphihau.cleanservice.util.Validator;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by phihau on 5/15/2017.
 */

public class ChangeMobileNumberPresenter
        implements ChangeMobileNumberContract.ChangeMobileNumberPresenter {
    ChangeMobileNumberActivity mView;

    public ChangeMobileNumberPresenter(ChangeMobileNumberActivity mView) {
        this.mView = mView;
    }


    @Override
    public void changeMobileNumber(final String phone) {
        final API mAPIService = GetAPIService.getAPIService();

        mView.showProgressDialog();
        if (TextUtils.isEmpty(phone)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.msg_input_username));
            return;
        }

        if (!Validator.isValidPhone(phone)) {
            mView.showDialogMessage(mView.getString(R.string.msg_phone_invalid));
        }
    }

    private Boolean isValidPhone(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

}
