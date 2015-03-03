package com.citi.ap.citiapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.rails_droid.RemoteRequest;
import com.anypresence.sdk.citi.models.Account;
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
        //Account.queryInBackground(Account.Scopes.ALL, callback);
        Map<String,String> maps = new HashMap<>();
        maps.put("token", "12345");
        Account.queryInBackground(Account.Scopes.ALL, maps, callback);
        return inflater.inflate(R.layout.account, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv=(TextView)getActivity().findViewById(R.id.textset);
        tv.setText("Accounts");
    }

}