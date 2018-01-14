package com.example.huynhphihau.cleanservice.account;

import android.content.Context;

import com.example.huynhphihau.cleanservice.base.BaseView;

/**
 * Created by phihau on 5/15/2017.
 */

public class EditAccountContract {

    public interface EditAccountView extends BaseView {

        void showUserInfo(String fullName, String photoURL, String emailAdrress, String phoneNumber);

        void uploadAvatarSuccess();

        void openCamera();

        void openGallery();

    }

    public interface EditAccountPresenter {
        void loadUserInfo();

        void uploadAvatar(Context context, String path);

        void createTempFileWithSampleSize(Context context, String originPath);
    }
}