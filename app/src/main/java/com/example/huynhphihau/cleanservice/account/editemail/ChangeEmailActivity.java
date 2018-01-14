package com.example.huynhphihau.cleanservice.account.editemail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phihau on 5/11/2017.
 */

public class ChangeEmailActivity
        extends BaseActivity
        implements ChangeEmailContract.ChangeEmailView {

    @BindView(R.id.toolbar_change_email)
    Toolbar toolbar_change_email;
    @BindView(R.id.btn_change_email)
    Button btn_change_email;
    @BindView(R.id.edt_change_email_address)
    EditText edt_change_email_address;

    ChangeEmailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        ButterKnife.bind(this);

        mPresenter = new ChangeEmailPresenter(this);

        /*Set tool bar*/
        setSupportActionBar(toolbar_change_email);
        /*Show home button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_change_email_address.getText().toString().trim();
                mPresenter.changeEmail(email);
            }
        });

        // Set Email keyboard
        edt_change_email_address.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }

    @Override
    public void showChangeEmailSuccess() {

        new AlertDialog.Builder(this, R.style.DashboardDialogTheme)
                .setMessage(getResources().getText(R.string.change_email_success))
                .setPositiveButton(getResources().getText(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}