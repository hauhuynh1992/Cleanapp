package com.example.huynhphihau.cleanservice.auth.signup;

import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;
import com.example.huynhphihau.cleanservice.util.Validator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huynhphihau on 12/25/17.
 */

public class SignUpPresenter implements SignUpContract.SignUpPresenter {

    private SignUpActivity mView;

    public SignUpPresenter(SignUpActivity SignUpView) {
        this.mView = SignUpView;
    }

    @Override
    public void doSignUp(String fistName, String lastName, String userName, String email, String pass, String confirmPass) {
        API mAPIService;
        mAPIService = GetAPIService.getAPIService();
        mView.showProgressDialog();

        if (TextUtils.isEmpty(fistName)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.msg_input_fist_name));
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.msg_input_last_name));
            return;
        }

        if (TextUtils.isEmpty(userName)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.msg_input_username));
            return;
        }

        if (TextUtils.isEmpty(email)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.email_empty));
            return;
        }

        if (!Validator.isValidEmail(email)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.email_invalid));
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.new_pass_empty));
            return;
        }

        if (TextUtils.isEmpty(confirmPass)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.confirm_pass_empty));
            return;
        }


        mAPIService.registerAccount(fistName, lastName, userName, email, pass, confirmPass).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                mView.hideProgressDialog();
                if (response.isSuccessful()) {
                    mView.showSignUpSuccess();
                } else {
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }
}
