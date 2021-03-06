package com.example.huynhphihau.cleanservice.dashboard.request;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseActivity;
import com.example.huynhphihau.cleanservice.data.response.Building;
import com.example.huynhphihau.cleanservice.data.response.BuildingDetail;
import com.example.huynhphihau.cleanservice.data.response.Company;
import com.example.huynhphihau.cleanservice.data.response.ReportOption;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.huynhphihau.cleanservice.base.BaseConfig.PERMISSIONS_REQUEST_CAMERA;

/**
 * Created by huynhphihau on 12/17/17.
 */

public class RequestPageActivity
        extends BaseActivity
        implements
            RequestPageContact.RequestPageView,
            ViewPagerRequestImageAdapter.ViewImageListener {

    @BindView(R.id.txt_ic_close)
    Button txt_ic_close;
    @BindView(R.id.dl_req_company)
    Spinner dl_req_company;
    @BindView(R.id.dl_req_building)
    Spinner dl_req_building;
    @BindView(R.id.dl_req_building_level)
    Spinner dl_req_building_level;
    @BindView(R.id.dl_req_option)
    Spinner dl_req_option;
    @BindView(R.id.rel_loading)
    RelativeLayout rel_loading;
    @BindView(R.id.req_edt_notes)
    EditText req_edt_notes;
    @BindView(R.id.req_edt_title)
    EditText req_edt_title;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.vp_req_img_before)
    ViewPager vp_req_img_before;
    @BindView(R.id.btn_req_add_image)
    ImageView btn_req_add_image;

    RequestPagePresenter mPresenter;
    List<String> buildingNames = new ArrayList<String>();
    List<String> buildingLevleNames = new ArrayList<String>();
    List<String> companyNames = new ArrayList<String>();
    List<String> reportOptionNames = new ArrayList<String>();
    List<Building> buildings;
    BuildingDetail buildingDetail;
    List<Company> companies;
    List<ReportOption> reportOptions;


    long buildingId = -1;
    long buildingLevelId = -1;
    long companyId = -1;
    long optionId = -1;
    long jobType = 10;
    String mCurrentPhotoPath = "";
    String remark = "";
    String title = "";
    File photoFile = null;
    ViewPagerRequestImageAdapter imaBeforeAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.bind(this);

        mPresenter = new RequestPagePresenter(this);

        /* Set color background for Progress bar */
        rel_loading.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_black_80));

        txt_ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestPageActivity.this.finish();
            }
        });

        imaBeforeAdapter = new ViewPagerRequestImageAdapter(RequestPageActivity.this, this);
        vp_req_img_before.setAdapter(imaBeforeAdapter);

        // Get Image path
        mCurrentPhotoPath = getIntent().getStringExtra("IMAGE_PATH");
        if(!TextUtils.isEmpty(mCurrentPhotoPath)){
            imaBeforeAdapter.setImages(mCurrentPhotoPath);
            mPresenter.createTempFileWithSampleSize(RequestPageActivity.this, mCurrentPhotoPath);
        }

        mPresenter.getListBuilding();
        mPresenter.getListCompany();
        mPresenter.getReportOption();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check paramenter
                if (mPresenter.getImagePath().size() <= 0) {
                    Toast.makeText(RequestPageActivity.this, "Please selected photo", Toast.LENGTH_SHORT).show();
                    return;
                };

                remark = req_edt_notes.getText().toString();
                title = req_edt_title.getText().toString();
                mPresenter.createReport(title, optionId, remark, buildingId, buildingLevelId, companyId, jobType, mPresenter.getImagePath());
            }
        });

        btn_req_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(RequestPageActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    ActivityCompat.requestPermissions(RequestPageActivity.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
                }
            }
        });
    }

    private void openCamera() {
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

//    private void openGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
//                PERMISSIONS_REQUEST_GALLERY);
//    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PERMISSIONS_REQUEST_CAMERA:
//                    File imgFile = new File(mCurrentPhotoPath);
//                    if (imgFile.exists()) {
//                        Picasso.with(this)
//                                .load(imgFile)
//                                .placeholder(R.drawable.no_image)
//                                .resize(req_img_before.getWidth(), req_img_before.getHeight())
//                                .centerCrop()
//                                .error(R.drawable.no_image)
//                                .into(req_img_before);
//                    }
                    Log.d("AAA - camera", mCurrentPhotoPath);
                    imaBeforeAdapter.setImages(mCurrentPhotoPath);
                    mPresenter.createTempFileWithSampleSize(RequestPageActivity.this, mCurrentPhotoPath);
                    break;
//                case PERMISSIONS_REQUEST_GALLERY:
//                    Uri selectedImageUri = data.getData();
////                    Picasso.with(this)
////                            .load(selectedImageUri)
////                            .placeholder(R.drawable.no_image)
////                            .resize(req_img_before.getWidth(), req_img_before.getHeight())
////                            .centerCrop()
////                            .error(R.drawable.no_image)
////                            .into(req_img_before);
//                    Log.d("AAA - lib", selectedImageUri.getPath().toString());
//                    imaBeforeAdapter.setImages(selectedImageUri.toString(), 1);
//                    mPresenter.createTempFileWithSampleSize(RequestPageActivity.this, FileManager.getPath(this, selectedImageUri));
//                    break;
            }
        }
    }

    @Override
    public void showListBuilding() {
        buildings = mPresenter.getBuilings();

        if (buildings == null) {
            return;
        }

        //Get Building name
        buildingNames.clear();
        for (int i = 0; i < buildings.size(); i++) {
            buildingNames.add(buildings.get(i).getName());
        }

        dl_req_building.setAdapter(new ArrayAdapter<String>(RequestPageActivity.this, android.R.layout.simple_spinner_dropdown_item, buildingNames));

        dl_req_building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                buildingId = buildings.get(i).getId();
                mPresenter.getBuildingDetails(buildings.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                buildingLevleNames.clear();
            }
        });

    }

    @Override
    public void showListBuildingLevels() {
        buildingDetail = mPresenter.getBuilingDetail();

        if (buildingDetail == null) {
            return;
        }

        //Get Building name
        buildingLevleNames.clear();
        for (int i = 0; i < buildingDetail.getBuildingLevels().size(); i++) {
            buildingLevleNames.add(buildingDetail.getBuildingLevels().get(i).getLevelName());
        }

        dl_req_building_level.setAdapter(new ArrayAdapter<String>(RequestPageActivity.this, android.R.layout.simple_spinner_dropdown_item, buildingLevleNames));

        dl_req_building_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                buildingLevelId = buildingDetail.getBuildingLevels().get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void showListReportOptions() {
        reportOptions = mPresenter.getReportOptions();

        if (reportOptions == null) {
            return;
        }

        //Get Building name
        for (int i = 0; i < reportOptions.size(); i++) {
            reportOptionNames.add(reportOptions.get(i).getOptionContent());
        }

        dl_req_option.setAdapter(new ArrayAdapter<String>(RequestPageActivity.this, android.R.layout.simple_spinner_dropdown_item, reportOptionNames));

        dl_req_option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                optionId = reportOptions.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void showListCompanies() {
        companies = mPresenter.getCompanies();

        if (companies == null) {
            return;
        }

        //Get Building name
        for (int i = 0; i < companies.size(); i++) {
            companyNames.add(companies.get(i).getName());
        }

        dl_req_company.setAdapter(new ArrayAdapter<String>(RequestPageActivity.this, android.R.layout.simple_spinner_dropdown_item, companyNames));

        dl_req_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                companyId = companies.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    public void onViewImage(String imagePath) {
        // Zoom detail Image
        ImageView closeIcon, imgPicture;
        Dialog dialog = new Dialog(RequestPageActivity.this);
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
        File imgFile = new File(imagePath);
        Picasso.with(dialog.getContext())
                .load(imgFile)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .fit().centerCrop()
                .into(imgPicture);

        dialog.show();
    }
}
