package com.citi.ap.citiapp;

import android.app.Application;

import com.anypresence.APSetup;
import com.anypresence.sdk.citi.models.LoginInfo;
import com.anypresence.sdk.config.Config;

/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 27/02/15.
 */
public class CitiApplication extends Application {

    private static CitiApplication instance;

    public LoginInfo getClient() {
        return client;
    }

    public void setClient(LoginInfo client) {
        this.client = client;
    }

    private LoginInfo client;

    public static CitiApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        APSetup.setupOrm(getApplicationContext());
        APSetup.setup();
        //Config.getInstance().setBaseUrl(CitiConstants.BACKEND_URL);
        //Config.getInstance().setAppUrl(CitiConstants.BACKEND_URL);
        //Config.getInstance().setAuthUrl(CitiConstants.BACKEND_URL + "/user/login?client_id=anypresence");
        //Config.getInstance().setDeauthUrl(com.citi.ap.citiapp.CitiConstants.BACKEND_URL + "auth/signout");
        Config.getInstance().setStrictQueryFieldCheck(false);
    }
}
