package com.example.huynhphihau.cleanservice.account.smsverification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phihau on 5/11/2017.
 */

public class SmsVerificationActivity
        extends BaseActivity
        implements SmsVerificationContract.SmsVerificationView {


    @BindView(R.id.toolbar_sms_verification)
    Toolbar toolbar;
    @BindView(R.id.edt_opt)
    EditText edt_otp;

    SmsVerificationPresneter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verification);

        ButterKnife.bind(this);

        /*Set tool bar*/
        setSupportActionBar(toolbar);
        /*Show home button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mPresenter = new SmsVerificationPresneter(this);
        Intent intent = getIntent();
        final String mobileNumber = intent.getStringExtra("NewMobileNumber");

        edt_otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 6) {
                    mPresenter.submitOtp(editable.toString(), mobileNumber);
                }
            }
        });
    }

    @Override
    public void showSmsVerificationSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getText(R.string.change_phone_success));
        builder.setCancelable(false);
        builder.setNegativeButton(getText(R.string.btn_confirm_ok), new DialogInterface.OnClickListener() {
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
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }
}