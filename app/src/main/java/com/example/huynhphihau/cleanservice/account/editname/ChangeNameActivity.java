package com.example.huynhphihau.cleanservice.account.editname;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huynhphihau on 11/24/17.
 */

public class ChangeNameActivity
        extends BaseActivity
        implements ChangeNameContract.ChangeNameView {

    @BindView(R.id.toolbar_change_name)
    Toolbar toolbar_change_name;
    @BindView(R.id.btn_change_name)
    Button btn_change_name;
    @BindView(R.id.edt_change_full_name)
    EditText edt_change_full_name;

    ChangeNamePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        ButterKnife.bind(this);

        mPresenter = new ChangeNamePresenter(this);

        /*Set tool bar*/
        setSupportActionBar(toolbar_change_name);
        /*Show home button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_change_full_name.getText().toString().trim();
                mPresenter.changeName(name);
            }
        });

    }

    @Override
    public void showChangeNameSuccess() {

        new AlertDialog.Builder(this, R.style.DashboardDialogTheme)
                .setMessage(getResources().getText(R.string.change_name_success))
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