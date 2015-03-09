package com.citi.ap.citiapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anypresence.anypresence_inc.citi.dao.LoginInfo;
import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.rails_droid.RemoteRequest;
import com.anypresence.sdk.citi.models.Account;
import com.anypresence.sdk.config.Config;
import com.citi.ap.citiapp.CitiApplication;
import com.citi.ap.citiapp.CitiConstants;
import com.citi.ap.citiapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 03/03/15.
 */
public class AccountFragment extends Fragment{

    private IAPFutureCallback callback = new IAPFutureCallback() {
        @Override
        public void finished(Object o, Throwable throwable) {
            Log.e("ACCOUNT_TAG", o.toString());
        }

        @Override
        public void onSuccess(Object o) {
            Log.d("ACCOUNT_TAG", o.toString());
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.e("ACCOUNT_TAG", throwable.toString());
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Map<String,String> maps = new HashMap<>();
        maps.put("token", CitiApplication.getInstance().getClient().getToken());
        RemoteRequest request = new RemoteRequest();
        request.setContext(CitiApplication.getInstance().getClient());
        request.setHeaders(maps);
        request.setPath(CitiConstants.BACKEND_URL + "/" + CitiConstants.VERSION +  "/accounts");
        request.setQuery(Account.Scopes.ALL);
        request.setRequestMethod("GET");
        Account.queryInBackground(request, Account.class, callback);
        return inflater.inflate(R.layout.account, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv=(TextView)getActivity().findViewById(R.id.textset);
        tv.setText("Accounts");
    }

}