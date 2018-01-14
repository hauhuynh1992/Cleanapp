package com.example.huynhphihau.cleanservice.account.editpassword;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseActivity;
import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phihau on 5/11/2017.
 */

public class ChangeNewPasswordActivity
        extends BaseActivity
        implements ChangeNewPasswordContract.ChangeNewPasswordView {

    @BindView(R.id.toolbar_new_password)
    Toolbar toolbar;
    @BindView(R.id.btn_reset_password)
    Button btn_reset_password;
    @BindView(R.id.edt_new_password)
    EditText edt_new_password;
    @BindView(R.id.edt_confim_password)
    EditText edt_confim_password;
    @BindView(R.id.til_confirm_pass)
    TextInputLayout til_confirm_pass;
    @BindView(R.id.txt_enter_password)
    TextView txt_enter_password;
    @BindView(R.id.til_new_pass)
    TextInputLayout til_new_pass;


    ChangeNewPasswordPresenter mPresenter;
    boolean isVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        ButterKnife.bind(this);

        /*Set tool bar*/
        setSupportActionBar(toolbar);
        /*Show home button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter = new ChangeNewPasswordPresenter(this);
        btn_reset_password.setText(getResources().getString(R.string.btn_verify_password));
        til_confirm_pass.setVisibility(View.GONE);
        txt_enter_password.setVisibility(View.VISIBLE);
        til_new_pass.setHint(getResources().getString(R.string.txt_password_exist));

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVerify) {
                    String newPass = edt_new_password.getText().toString().trim();
                    String confirmPass = edt_confim_password.getText().toString().trim();
                    mPresenter.changeNewPassword(newPass, confirmPass);
                } else {
                    String existPass = edt_new_password.getText().toString().trim();
                    String username = BaseApplication.getInstance().getUser().getUsername();
                    String device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    String device_token = FirebaseInstanceId.getInstance().getToken();
                    mPresenter.verifyPassword(username, existPass, device_id, device_token);
                }
            }
        });
    }

    @Override
    public void showChangeNewPasswordSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getText(R.string.chang_pass_success));
        builder.setCancelable(false);
        builder.setNegativeButton(getResources().getText(R.string.btn_confirm_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void showVerifyPassSuccess() {
        isVerify = true;
        btn_reset_password.setText(getResources().getString(R.string.btn_reset_password));
        til_confirm_pass.setVisibility(View.VISIBLE);
        txt_enter_password.setVisibility(View.GONE);
        til_new_pass.setHint(getResources().getString(R.string.new_password));
        edt_new_password.setText("");

    }
}