package com.example.huynhphihau.cleanservice.base;

import okhttp3.ResponseBody;

/**
 * Created by LucLX on 4/22/17.
 */

public interface BaseView<T extends BasePresenter> {
    /**
     * show progress dialog
     */
    void showProgressDialog();

    /**
     * hide progress dialog
     */
    void hideProgressDialog();

    /**
     * show alert dialog with string message
     *
     * @param msg
     */
    void showDialogMessage(String msg);

    /**
     * handler api error response
     *
     * @param e
     */
    void handleError(Throwable e);

    /**
     * handler error response
     */
    void handleError(Integer code, ResponseBody errorBody);
}
