package com.citi.ap.citiapp.fragment.container;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citi.ap.citiapp.R;
import com.citi.ap.citiapp.fragment.AccountFragment;
import com.citi.ap.citiapp.fragment.BaseContainerFragment;

/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 03/03/15.
 */
public class Tab1Container extends BaseContainerFragment {

    private boolean IsViewInited;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CitiAPP", "Tab1");
        return inflater.inflate(R.layout.container_framelayout, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!IsViewInited) {
            IsViewInited = true;
            initView();
        }
    }

    private void initView() {
        replaceFragment(new AccountFragment(), false);
    }

}
