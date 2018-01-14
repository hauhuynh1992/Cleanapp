package com.example.huynhphihau.cleanservice.account.editemail;

import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.data.response.BaseRespone;
import com.example.huynhphihau.cleanservice.data.response.User;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;
import com.example.huynhphihau.cleanservice.util.Validator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phihau on 5/15/2017.
 */

public class ChangeEmailPresenter
        implements ChangeEmailContract.ChangeNewPasswordPresenter {
    ChangeEmailActivity mView;

    public ChangeEmailPresenter(ChangeEmailActivity mView) {
        this.mView = mView;
    }


    @Override
    public void changeEmail(String email) {
        final API mAPIService = GetAPIService.getAPIService();

        mView.showProgressDialog();
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
        mAPIService.updateEmail(email).enqueue(new Callback<BaseRespone<User>>() {
            @Override
            public void onResponse(Call<BaseRespone<User>> call, Response<BaseRespone<User>> response) {
                mView.hideProgressDialog();
                if (response.isSuccessful()) {
                    BaseApplication.getInstance().setUser(response.body().getData());
                    mView.showChangeEmailSuccess();
                } else {
                    //Handle Error
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<User>> call, Throwable t) {
                //Handle Error
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }
}
