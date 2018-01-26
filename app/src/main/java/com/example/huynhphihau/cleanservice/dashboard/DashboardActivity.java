package com.example.huynhphihau.cleanservice.dashboard;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huynhphihau.cleanservice.BuildConfig;
import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.account.EditAccountActivity;
import com.example.huynhphihau.cleanservice.auth.FrontActivity;
import com.example.huynhphihau.cleanservice.base.AbstractActivity;
import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.dashboard.request.RequestPageActivity;
import com.example.huynhphihau.cleanservice.model.MenuBottom;
import com.example.huynhphihau.cleanservice.util.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.huynhphihau.cleanservice.base.BaseConfig.PERMISSIONS_REQUEST_CAMERA;

public class DashboardActivity extends AbstractActivity {

    @BindView(R.id.img_camera_report)
    ImageView imgCamera;
    @BindView(R.id.toolbar_home)
    Toolbar toolbar_home;
    @BindView(R.id.dr_home)
    DrawerLayout dr_home;
    @BindView(R.id.nv_home)
    NavigationView nv_home;
    @BindView(R.id.lv_home_menu)
    ListView lv_home_menu;
    @BindView(R.id.rl_version)
    RelativeLayout rl_version;
    @BindView(R.id.app_version)
    TextView app_version;
    @BindView(R.id.img_menu_header)
    ImageView img_menu_header;
    @BindView(R.id.txt_header_name)
    TextView txt_name;

    ArrayList<MenuBottom> menus;
    String mCurrentPhotoPath = "";
    File photoFile = null;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewReady() {
        initToolbar();
        initMenu();
    }

    @OnClick(R.id.img_camera_report)
    protected void createReport() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PERMISSIONS_REQUEST_CAMERA:
                    Intent intent = new Intent(this, RequestPageActivity.class);
                    intent.putExtra("IMAGE_PATH", mCurrentPhotoPath);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void initToolbar() {
        /*Set action bar*/
        setSupportActionBar(toolbar_home);
        /*Disable title*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*Set menu icon*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, dr_home, toolbar_home, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dr_home.addDrawerListener(toggle);
        /*Set color for menu icon*/
        toggle.getDrawerArrowDrawable().setColor(-1);
        toggle.syncState();
    }

    private void initMenu() {
        /*Set icon of menu*/
        nv_home.setItemIconTintList(null);
        /*When user click menu icon on Toolbar */
        toolbar_home.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr_home.openDrawer(GravityCompat.START);
            }
        });

        app_version.setText("v" + BuildConfig.VERSION_NAME);

        /*When user  click main menu */
        menus = new ArrayList<>();
        menus.add(new MenuBottom(getResources().getString(R.string.menu_logout), ""));
        MenuBottomAdapter adapterMenuBottom = new MenuBottomAdapter(this, R.layout.line_menu, menus);
        lv_home_menu.setAdapter(adapterMenuBottom);

        lv_home_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                switch (i) {
                    case 0:
                        showDialogLogout();
                        break;
                }
            }
        });

        rl_version.setOnClickListener(view -> {
        });

        // header
        String fullName, photoURL;
        fullName = BaseApplication.getInstance().getUser().getFirstName() + " " + BaseApplication.getInstance().getUser().getLastName();
        photoURL = BaseApplication.getInstance().getUser().getPhotoUrl().toString();
        //Set name for driver
        txt_name.setText(fullName);
        //Set avatar for driver
        Picasso.with(this)
                .load(photoURL)
                .placeholder(R.drawable.no_image)
                .transform(new RoundedImageView())
                .error(R.drawable.no_image)
                .into(img_menu_header);

        img_menu_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit_account = new Intent(DashboardActivity.this, EditAccountActivity.class);
                startActivity(edit_account);
            }
        });

    }

    private void showDialogLogout() {
        Button btn_sign_out_ok, btn_sign_out_cancel;

        final Dialog dialogSignOut = new Dialog(this);
        dialogSignOut.setContentView(R.layout.dialog_sign_out);
        dialogSignOut.setCanceledOnTouchOutside(false);
        Window windowSignOut = dialogSignOut.getWindow();
        windowSignOut.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogSignOut.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        btn_sign_out_ok = dialogSignOut.findViewById(R.id.btn_sign_out_ok);
        btn_sign_out_cancel = dialogSignOut.findViewById(R.id.btn_sign_out_cancel);

        btn_sign_out_ok.setOnClickListener(view -> {
            dialogSignOut.dismiss();
            BaseApplication.getInstance().clearSession();
            navigateLoginPage();
        });

        btn_sign_out_cancel.setOnClickListener(view -> {
            dialogSignOut.dismiss();
        });
        dialogSignOut.show();
    }

    public void navigateLoginPage() {
        Intent intent = new Intent(BaseApplication.getInstance(), FrontActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void updateNumberUnRead(){
        BaseApplication.getInstance().setNumberUnRead("+");
        super.onResume();
    }
}