package com.example.huynhphihau.cleanservice.account.editpassword;

import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.data.response.BaseRespone;
import com.example.huynhphihau.cleanservice.data.response.Login;
import com.example.huynhphihau.cleanservice.data.response.User;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phihau on 5/14/2017.
 */

public class ChangeNewPasswordPresenter implements ChangeNewPasswordContract.ChangeNewPasswordPresenter {
    ChangeNewPasswordActivity mView;

    public ChangeNewPasswordPresenter(ChangeNewPasswordActivity mView) {
        this.mView = mView;
    }

    @Override
    public void changeNewPassword(String newPassword, String confirmPassword) {
        final API mAPIService = GetAPIService.getAPIService();

        mView.showProgressDialog();
        if (TextUtils.isEmpty(newPassword)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.new_pass_empty));
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.confirm_pass_empty));
            return;
        }

        mAPIService.updatePassword(newPassword, confirmPassword).enqueue(new Callback<BaseRespone<User>>() {
            @Override
            public void onResponse(Call<BaseRespone<User>> call, Response<BaseRespone<User>> response) {
                mView.hideProgressDialog();
                if (response.isSuccessful()) {
                    /*Store User info and token in Share preference*/
                    BaseApplication.getInstance().setUser(response.body().getData());
                    mView.showChangeNewPasswordSuccess();
                } else {
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<User>> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }

    @Override
    public void verifyPassword(String phone, String pass, String device_id, String device_token) {
        final API mAPIServrice = GetAPIService.getAPIService();

        mView.showProgressDialog();
        if (TextUtils.isEmpty(pass)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.new_pass_empty));
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

        mAPIServrice.submitAccount(phone, pass, device_id, device_token, "Anddroid").enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                mView.hideProgressDialog();
                if (response.isSuccessful()) {
                    /*Store User info and token in Share preference*/
                    BaseApplication.getInstance().setToken(response.body().getToken_type() + response.body().getToken());
                    BaseApplication.getInstance().setUser(response.body().getUser());
                    mView.showVerifyPassSuccess();
                } else {
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
