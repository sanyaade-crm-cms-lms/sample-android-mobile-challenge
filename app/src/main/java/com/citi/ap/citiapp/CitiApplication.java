package com.citi.ap.citiapp;

import android.app.Application;
import android.util.Log;

import com.anypresence.APSetup;
import com.anypresence.rails_droid.CustomCookieManager;
import com.anypresence.rails_droid.RemoteRailsConfig;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingLogin;
import com.anypresence.sdk.config.Config;

/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 27/02/15.
 */
public class CitiApplication extends Application {

    private static CitiApplication instance;

    public RetailBankingLogin getClient() {
        return client;
    }

    public void setClient(RetailBankingLogin client) {
        this.client = client;
    }

    private RetailBankingLogin client;

    public static CitiApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;
        //APSetup.setupOrm(getApplicationContext());

        APSetup.setup(this);

        APSetup.setBaseUrl(CitiConstants.BACKEND_URL + "/" + CitiConstants.VERSION);
        //Config.getInstance().setBaseUrl(CitiConstants.BACKEND_URL);
        //Config.getInstance().setAppUrl(CitiConstants.BACKEND_URL + "/" + CitiConstants.VERSION);
        //Config.getInstance().setAuthUrl(CitiConstants.BACKEND_URL + "/user/login?client_id=anypresence");
        //Config.getInstance().setDeauthUrl(com.citi.ap.citiapp.CitiConstants.BACKEND_URL + "auth/signout");
        //Config.getInstance().setStrictQueryFieldCheck(false);
    }
}
