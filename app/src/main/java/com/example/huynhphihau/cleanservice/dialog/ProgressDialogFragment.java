package com.example.huynhphihau.cleanservice.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.example.huynhphihau.cleanservice.R;

/**
 * Created by LucLX on 4/22/17.
 */

public class ProgressDialogFragment extends AppCompatDialogFragment {

    public boolean isShow;

    public static ProgressDialogFragment newInstance() {
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        return progressDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        RelativeLayout rel_loading;
        Dialog dialog = new Dialog(getActivity(), R.style.DialogStyle);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar_custom_layout);

        rel_loading = (RelativeLayout) dialog.findViewById(R.id.rel_loading);
        rel_loading.setBackgroundColor(ContextCompat.getColor(dialog.getContext(), R.color.transparent_black_80));


        dialog.setCancelable(false);
        rel_loading.setVisibility(View.VISIBLE);

        return dialog;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (isShow) return;
        super.show(manager, tag);
        isShow = true;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShow = false;
    }
}
