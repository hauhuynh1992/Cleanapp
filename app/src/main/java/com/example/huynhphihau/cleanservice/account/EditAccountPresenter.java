package com.example.huynhphihau.cleanservice.account;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.data.response.BaseRespone;
import com.example.huynhphihau.cleanservice.data.response.User;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;
import com.example.huynhphihau.cleanservice.util.AppLog;
import com.example.huynhphihau.cleanservice.util.BitmapUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phihau on 5/15/2017.
 */

public class EditAccountPresenter implements EditAccountContract.EditAccountPresenter {

    EditAccountContract.EditAccountView mView;

    public EditAccountPresenter(EditAccountContract.EditAccountView mView) {
        this.mView = mView;
    }

    @Override
    public void loadUserInfo() {
        final String fullName, mobilePhone, email, photoURL;

        mView.showProgressDialog();

        fullName = BaseApplication.getInstance().getUser().getFirstName().toString() + " " + BaseApplication.getInstance().getUser().getLastName().toString();
        photoURL = BaseApplication.getInstance().getUser().getPhotoUrl().toString();
        mobilePhone = BaseApplication.getInstance().getUser().getPhone_number().toString();
        email = BaseApplication.getInstance().getUser().getEmail().toString();

        mView.hideProgressDialog();
        mView.showUserInfo(fullName, photoURL, email, mobilePhone);
    }

    @Override
    public void uploadAvatar(final Context context, String path) {
        mView.showProgressDialog();
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part[] fileToUploads = new MultipartBody.Part[1];
        fileToUploads[0] = MultipartBody.Part.createFormData("files", file.getName(), requestBody);

        API mAPIService = GetAPIService.getAPIService();
        mAPIService.uploadAvatar(fileToUploads).enqueue(new Callback<BaseRespone<User>>() {
            @Override
            public void onResponse(Call<BaseRespone<User>> call, Response<BaseRespone<User>> response) {
                if (response.isSuccessful()) {
                    BaseApplication.getInstance().setUser(response.body().getData());
                    mView.uploadAvatarSuccess();
                    mView.hideProgressDialog();
                    // delete temp file
                    BitmapUtils.deleteImageTemp(context);
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

    @Override
    public void createTempFileWithSampleSize(Context context, String originPath) {
        // show progress bar
        mView.showProgressDialog();

        if (TextUtils.isEmpty(originPath)) {
            mView.showDialogMessage("Can not found Image");
            mView.hideProgressDialog();
            return;
        }

        // reduce image size
        Bitmap bitmap = BitmapUtils.decodeFile(originPath, 200);

        if (bitmap == null) {
            mView.showDialogMessage("Can not found Image");
            mView.hideProgressDialog();
            return;
        }

        AppLog.e("AAA", "BITMAP size " + bitmap.getByteCount());

        // get time
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());

        // create the temp file to upload
        File childFile = BitmapUtils.createImageFromBitmap(context, bitmap, "ValetAngles_" + timeStamp);

        if (childFile == null) {
            mView.showDialogMessage("Can not found Image");
            mView.hideProgressDialog();
            return;
        }
        // release bitmap
        bitmap = null;

        // upload temp file
        this.uploadAvatar(context, childFile.getAbsolutePath());
    }
}
