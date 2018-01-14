package com.example.huynhphihau.cleanservice.base;

/**
 * Created by LucLX on 4/22/17.
 */

public interface BasePresenter {
    /**
     * implement in Activity's OnResume()
     */
    void start();

    /**
     * implement in Activity's OnStop()
     */
    void doCancel();
}
