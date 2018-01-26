package com.example.huynhphihau.cleanservice.dashboard.report;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRatingBar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseActivity;
import com.example.huynhphihau.cleanservice.base.BaseConfig;
import com.example.huynhphihau.cleanservice.data.response.ReportData;
import com.example.huynhphihau.cleanservice.dialog.GetPhotoDialogFragment;
import com.example.huynhphihau.cleanservice.util.FileManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.huynhphihau.cleanservice.base.BaseConfig.PERMISSIONS_REQUEST_CAMERA;
import static com.example.huynhphihau.cleanservice.base.BaseConfig.PERMISSIONS_REQUEST_GALLERY;

/**
 * Created by huynhphihau on 12/18/17.
 */

public class ReportPageActivity
        extends BaseActivity
        implements
        ReportPageContact.ReportPageView,
        ViewPagerImageAdapter.ViewImageListener {

    @BindView(R.id.txt_ic_close)
    Button txt_ic_close;
    @BindView(R.id.rel_loading)
    RelativeLayout rel_loading;
    @BindView(R.id.req_vp_img_after)
    ViewPager req_vp_img_after;
    @BindView(R.id.req_vp_img_before)
    ViewPager req_vp_img_before;
    @BindView(R.id.txt_rep_company)
    TextView txt_rep_company;
    @BindView(R.id.txt_rep_building)
    TextView txt_rep_building;
    @BindView(R.id.txt_rep_building_level)
    TextView txt_rep_building_level;
    @BindView(R.id.txt_rep_note)
    TextView txt_rep_note;
    @BindView(R.id.txt_rep_option)
    TextView txt_rep_option;
    @BindView(R.id.btn_add_dest)
    Button btn_check_complete;
    @BindView(R.id.txt_view_job_title)
    TextView txt_view_job_title;
    @BindView(R.id.rating_bar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.rep_edt_remark)
    EditText edtRemark;
    @BindView(R.id.btn_add_image)
    ImageView btnAddImage;


    ReportPagePresenter mPresenter;
    ReportData reportData;
    ViewPagerImageAdapter imageAfterAdapter;
    ViewPagerImageAdapter imaBeforeAdapter;

    File photoFile = null;
    private static String mCurrentPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        /* Init FerryPagePresenter */
        mPresenter = new ReportPagePresenter(this);

        /* Set color background for Progress bar */
        rel_loading.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_black_80));

        imageAfterAdapter = new ViewPagerImageAdapter(ReportPageActivity.this, this);
        imaBeforeAdapter = new ViewPagerImageAdapter(ReportPageActivity.this, this);
        req_vp_img_after.setAdapter(imageAfterAdapter);
        req_vp_img_before.setAdapter(imaBeforeAdapter);

        txt_ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

         /*Set ClickListener */
        btn_check_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.updateReportStatus(reportData.getId(), BaseConfig.REP_STATUS_COMPLETED);
            }
        });


        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetPhotoDialogFragment dialogFragment = GetPhotoDialogFragment.newInstance();
                dialogFragment.setListener(new GetPhotoDialogFragment.OnGetPhotoDialogListener() {
                    @Override
                    public void OnPickPhotoClicked() {
                        if (ContextCompat.checkSelfPermission(ReportPageActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            openGallery();
                        } else {
                            ActivityCompat.requestPermissions(ReportPageActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_GALLERY);
                        }
                    }

                    @Override
                    public void OnTakePhotoClicked() {
                        if (ContextCompat.checkSelfPermission(ReportPageActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            openCamera();
                        } else {
                            ActivityCompat.requestPermissions(ReportPageActivity.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), GetPhotoDialogFragment.class.getSimpleName());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get Ferry ID
        long id = getIntent().getLongExtra("REPORT_ID", -1);

        if (id == -1) {
            // Back to Dashboard
            finish();
        } else {
            // View detail job
            getReport(id);
        }
    }

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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PERMISSIONS_REQUEST_GALLERY);
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

    @Override
    public void showProgress() {
        if (rel_loading != null) {
            rel_loading.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideProgress() {
        if (rel_loading != null) {
            rel_loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PERMISSIONS_REQUEST_CAMERA:
                    mPresenter.createTempFileWithSampleSize(ReportPageActivity.this, mCurrentPhotoPath, reportData.getId());
                    break;
                case PERMISSIONS_REQUEST_GALLERY:
                    Uri selectedImageUri = data.getData();
                    mPresenter.createTempFileWithSampleSize(ReportPageActivity.this, FileManager.getPath(this, selectedImageUri), reportData.getId());
                    break;
            }
        }
    }


    private void loadData() {

        this.reportData = mPresenter.getReport();
        if (reportData == null) {
            return;
        }

        // Check Image report
        ArrayList<String> imagesBefore = new ArrayList<>();
        ArrayList<String> imagesAfter = new ArrayList<>();
        if (reportData.getImages().size() != 0 && reportData.getImages() != null) {
            for (int i = 0; i < reportData.getImages().size(); i++) {

                if (reportData.getImages().get(i).getIsBefore() == BaseConfig.IMAGE_BEFORE) {
                    imagesBefore.add(reportData.getImages().get(i).getPhotoUrl());
                } else {
                    imagesAfter.add(reportData.getImages().get(i).getPhotoUrl());
                }
            }

            if (imagesBefore.size() > 0) imaBeforeAdapter.setImages(imagesBefore);
            if (imagesAfter.size() > 0) imageAfterAdapter.setImages(imagesAfter);

        }

        //Set Company name
        if (!TextUtils.isEmpty(reportData.getCompanyName())) {
            txt_rep_company.setText(reportData.getCompanyName() + " " + getResources().getString(R.string.rep_txt_company));
        }

        //Set Building name
        if (!TextUtils.isEmpty(reportData.getBuildingName())) {
            txt_rep_building.setText(reportData.getBuildingName() + " " + getResources().getString(R.string.rep_txt_building));
        }

        //Set Building level name
        if (!TextUtils.isEmpty(reportData.getBuildingLevelName())) {
            txt_rep_building_level.setText(reportData.getBuildingLevelName() + " " + getResources().getString(R.string.rep_txt_building_level));
        }

        //Set option
        if (!TextUtils.isEmpty(reportData.getReportOptionName())) {
            txt_rep_option.setText(reportData.getReportOptionName() + " " + getResources().getString(R.string.rep_txt_option));
        }

        //Set Note:
        if (!TextUtils.isEmpty(reportData.getRemark())) {
            txt_rep_note.setText("Remark: " + reportData.getRemark());
        }

        // Set title
        if (!TextUtils.isEmpty(reportData.getTitle())) {
            txt_view_job_title.setText(reportData.getTitle());
        }

        // Mark read
        if (mPresenter != null) {
            mPresenter.markRead(reportData.getId());
        }
    }

    @Override
    public void getReport(long id) {
        if (mPresenter != null) {
            mPresenter.viewReport(id);
        }
    }

    @Override
    public void showCompleteButton() {
        // Show data
        loadData();

        // Enable edit
        btn_check_complete.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.GONE);
        edtRemark.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        req_vp_img_after.setEnabled(true);
        req_vp_img_before.setEnabled(true);
    }

    @Override
    public void hideCompleteButton() {
        // Show data
        loadData();

        // Enable edit
        btn_check_complete.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);
        edtRemark.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        btnAddImage.setVisibility(View.GONE);
        req_vp_img_after.setEnabled(false);
        req_vp_img_before.setEnabled(false);
    }

    @Override
    public void showRattingStar() {
        ReportData reportData = mPresenter.getReport();
        if (reportData == null) {
            return;
        }

        // Show data
        loadData();
        btnAddImage.setVisibility(View.GONE);
        if (reportData.getIsRated() == BaseConfig.REPORT_UNREAD) {
            ratingBar.setVisibility(View.VISIBLE);
            edtRemark.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
            ratingBar.setRating(5);
            ratingBar.setNumStars(5);
            btn_check_complete.setVisibility(View.GONE);
            req_vp_img_after.setEnabled(false);
            req_vp_img_before.setEnabled(false);
        } else {
            btn_check_complete.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
            edtRemark.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
            req_vp_img_after.setEnabled(false);
            req_vp_img_before.setEnabled(false);
        }
    }

    @OnClick(R.id.btn_submit)
    public void submitRating() {
        if (mPresenter != null) {
            ReportData reportData = mPresenter.getReport();
            String title = reportData.getTitle();
            String remark = edtRemark.getText().toString() + " ";
            long id = reportData.getId();
            double numStart = ratingBar.getRating();
            mPresenter.submitRating(id, title, remark, numStart);
        }
    }

    @Override
    public void onViewImage(String imagePath) {
        // Zoom detail Image
        ImageView closeIcon, imgPicture;
        Dialog dialog = new Dialog(ReportPageActivity.this);
        dialog.setContentView(R.layout.dialog_zoom_image);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setAttributes(wlp);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        /*Mapping*/
        closeIcon = dialog.findViewById(R.id.img_report_close_icon);
        imgPicture = dialog.findViewById(R.id.img_report_view_picture);

        closeIcon.setOnClickListener(view -> {
            dialog.dismiss();
        });

        // Zoom image
        Picasso.with(dialog.getContext())
                .load(imagePath)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .fit().centerCrop()
                .into(imgPicture);

        dialog.show();
    }
}
