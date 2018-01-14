package com.example.huynhphihau.cleanservice.account;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.account.editemail.ChangeEmailActivity;
import com.example.huynhphihau.cleanservice.account.editpassword.ChangeNewPasswordActivity;
import com.example.huynhphihau.cleanservice.base.BaseActivity;
import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.dialog.GetPhotoDialogFragment;
import com.example.huynhphihau.cleanservice.util.AppLog;
import com.example.huynhphihau.cleanservice.util.FileManager;
import com.example.huynhphihau.cleanservice.util.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.huynhphihau.cleanservice.base.BaseConfig.PERMISSIONS_REQUEST_CAMERA;
import static com.example.huynhphihau.cleanservice.base.BaseConfig.PERMISSIONS_REQUEST_GALLERY;

/**
 * Created by phihau on 5/10/2017.
 */

public class EditAccountActivity extends BaseActivity
        implements EditAccountContract.EditAccountView
        , View.OnClickListener {

    @BindView(R.id.img_edit_account_edit_avatar)
    ImageView img_edit_account_edit_avatar;
    @BindView(R.id.toolbar_edit_account)
    Toolbar toolbar_edit_account;
    @BindView(R.id.txt_edit_account_driver_name)
    TextView txt_edit_account_driver_name;
    @BindView(R.id.txt_edit_account_phone_number)
    TextView txt_edit_account_phone_number;
    @BindView(R.id.txt_edit_account_email_address)
    TextView txt_edit_account_email_address;
    @BindView(R.id.txt_edit_account_password)
    TextView txt_password;

    EditAccountPresenter mPresenter;

    File photoFile = null;
    private static String mCurrentPhotoPath;


    private static final String PHOTO_PATH = "photopath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        ButterKnife.bind(this);

        mPresenter = new EditAccountPresenter(this);

        /*Set tool bar*/
        setSupportActionBar(toolbar_edit_account);
        /*Show home button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txt_edit_account_driver_name.setOnClickListener(this);
        txt_edit_account_phone_number.setOnClickListener(this);
        txt_edit_account_email_address.setOnClickListener(this);
        txt_password.setOnClickListener(this);
        img_edit_account_edit_avatar.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load User Information.
        if (mPresenter != null) {
            mPresenter.loadUserInfo();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.txt_edit_account_driver_name:
//                intent = new Intent(EditAccountActivity.this, ChangeNameActivity.class);
//                startActivity(intent);
                break;
            case R.id.txt_edit_account_phone_number:
//                intent = new Intent(EditAccountActivity.this, ChangeMobileNumberActivity.class);
//                startActivity(intent);
                break;
            case R.id.txt_edit_account_email_address:
                intent = new Intent(EditAccountActivity.this, ChangeEmailActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_edit_account_password:
                intent = new Intent(EditAccountActivity.this, ChangeNewPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.img_edit_account_edit_avatar:
                GetPhotoDialogFragment dialogFragment = GetPhotoDialogFragment.newInstance();
                dialogFragment.setListener(new GetPhotoDialogFragment.OnGetPhotoDialogListener() {
                    @Override
                    public void OnPickPhotoClicked() {
                        if (ContextCompat.checkSelfPermission(EditAccountActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            openGallery();
                        } else {
                            ActivityCompat.requestPermissions(EditAccountActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_GALLERY);
                        }
                    }

                    @Override
                    public void OnTakePhotoClicked() {
                        if (ContextCompat.checkSelfPermission(EditAccountActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            openCamera();
                        } else {
                            ActivityCompat.requestPermissions(EditAccountActivity.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), GetPhotoDialogFragment.class.getSimpleName());
                break;
        }

    }

    @Override
    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            Uri photoURI = null;
            try {
                photoFile = createImageFile();
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, PERMISSIONS_REQUEST_CAMERA);
                }
            } catch (Exception ex) {
                // Error occurred while creating the File
            }
        }
    }

    @Override
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PERMISSIONS_REQUEST_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, getResources().getText(R.string.msg_permission_denied), Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case PERMISSIONS_REQUEST_GALLERY: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(this, getResources().getText(R.string.msg_permission_denied), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PERMISSIONS_REQUEST_CAMERA:
                    mPresenter.createTempFileWithSampleSize(EditAccountActivity.this, mCurrentPhotoPath);
                    break;
                case PERMISSIONS_REQUEST_GALLERY:
                    Uri selectedImageUri = data.getData();
                    mPresenter.createTempFileWithSampleSize(EditAccountActivity.this, FileManager.getPath(this, selectedImageUri));
                    break;
            }
        }
    }

    @Override
    public void showUserInfo(String fullName, String photoUrl, String emailAdrress, String phoneNumber) {

        //Set name for driver
        txt_edit_account_driver_name.setText(fullName);
        //Set avatar for driver
        Picasso.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.circle_bg)
                .transform(new RoundedImageView())
                .error(R.drawable.circle_bg)
                .into(img_edit_account_edit_avatar);
        //Set mobile phone
        txt_edit_account_phone_number.setText(phoneNumber);
        //Set email
        txt_edit_account_email_address.setText(emailAdrress);
    }

    @Override
    public void uploadAvatarSuccess() {
        // Re-load new Avatar
        Picasso.with(this)
                .load(BaseApplication.getInstance().getUser().getPhotoUrl())
                .placeholder(R.drawable.circle_bg)
                .transform(new RoundedImageView())
                .error(R.drawable.circle_bg)
                .into(img_edit_account_edit_avatar);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * In some devices, lunching camera requires a lot of memory, so on devices with low memory, OS will
     * close activity running and recreate, sometime the photo path will be release and become null
     **/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        AppLog.e("PHOTO", "onSaveInstanceState" + System.currentTimeMillis());
        outState.putString(PHOTO_PATH, mCurrentPhotoPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        AppLog.e("PHOTO", "onRestoreInstanceState" + System.currentTimeMillis());
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PHOTO_PATH)) {
                mCurrentPhotoPath = savedInstanceState.getString(PHOTO_PATH);
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
    /***************** END  ************/

}


