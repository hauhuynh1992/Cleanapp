package com.example.huynhphihau.cleanservice.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Window;
import android.view.WindowManager;

import com.example.huynhphihau.cleanservice.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by phihau on 6/30/2017.
 */

public class GetPhotoDialogFragment extends AppCompatDialogFragment {

    OnGetPhotoDialogListener mListener;

    public void setListener(OnGetPhotoDialogListener mListener) {
        this.mListener = mListener;
    }

    public static GetPhotoDialogFragment newInstance() {
        GetPhotoDialogFragment dialogFragment = new GetPhotoDialogFragment();
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogStyle);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pick_photo);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ButterKnife.bind(this, dialog);

        return dialog;
    }

    @OnClick(R.id.btn_gallery)
    public void openGallery() {
        if (mListener != null) {
            mListener.OnPickPhotoClicked();
        }
        this.dismiss();
    }

    @OnClick(R.id.btn_camera)
    public void openCamera() {
        if (mListener != null) {
            mListener.OnTakePhotoClicked();
        }
        this.dismiss();
    }

    @OnClick(R.id.btn_cancel)
    public void cancel() {
        this.dismiss();
    }

    public interface OnGetPhotoDialogListener {
        void OnPickPhotoClicked();

        void OnTakePhotoClicked();
    }
}