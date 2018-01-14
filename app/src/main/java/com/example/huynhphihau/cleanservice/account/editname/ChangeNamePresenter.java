package com.example.huynhphihau.cleanservice.account.editname;

import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;

/**
 * Created by huynhphihau on 11/24/17.
 */

public class ChangeNamePresenter
        implements ChangeNameContract.ChangeNamePresenter {
    ChangeNameActivity mView;

    public ChangeNamePresenter(ChangeNameActivity mView) {
        this.mView = mView;
    }


    @Override
    public void changeName(String name) {
        final API mAPIService = GetAPIService.getAPIService();

        mView.showProgressDialog();
        if (TextUtils.isEmpty(name)) {
            mView.hideProgressDialog();
            mView.showDialogMessage(mView.getString(R.string.name_empty));
            return;
        }
    }
}
