package com.example.huynhphihau.cleanservice.auth.login;

import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.data.response.Login;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by phihau on 4/26/2017.
 */

public class LoginPresenter implements LoginContract.LoginPresenter {

    private LoginActivity mView;

    public LoginPresenter(LoginActivity loginView) {
        this.mView = loginView;
    }

    @Override
    public void login(String phone, String pass, String device_id, String device_token) {
        API mAPIService;
        mAPIService = GetAPIService.getAPIService();
        mView.showProgressDialog();

        if (TextUtils.isEmpty(phone)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.msg_input_username));
            return;
        }

        if (TextUtils.isEmpty(device_id)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.msg_device_id_empty));
            return;
        }

        if (TextUtils.isEmpty(device_token)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.msg_device_token_empty));
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.msg_input_pass));
            return;
        }

        mAPIService.submitAccount(phone, pass, device_id, device_token, "Anddroid").enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    /*Store User info and token in Share preference*/
                    BaseApplication.getInstance().setToken(response.body().getToken_type() + response.body().getToken());
                    BaseApplication.getInstance().setUser(response.body().getUser());
                    mView.hideProgressDialog();
                    mView.loginSuccess();
                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }
}