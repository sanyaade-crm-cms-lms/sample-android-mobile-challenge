package com.citi.ap.citiapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.rails_droid.RemoteRequest;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingPayee;
import com.citi.ap.citiapp.CitiApplication;
import com.citi.ap.citiapp.CitiConstants;
import com.citi.ap.citiapp.R;
import com.citi.ap.citiapp.adapter.PayeeAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 03/03/15.
 */
public class PayeeFragment extends Fragment{

    private final static String PAYEE_FRAGMENT_TAG = "PAYEE_FRAGMENT";
    protected ProgressDialog mConnectionProgressDialog;
    private ListView payeeList;
    private PayeeAdapter adapter;
    private IAPFutureCallback callback = new IAPFutureCallback() {
        @Override
        public void finished(Object o, Throwable throwable) {
            Log.e(PAYEE_FRAGMENT_TAG, o.toString());
        }

        @Override
        public void onSuccess(final Object o) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConnectionProgressDialog.dismiss();
                    setupPayeeView((List) o);
                }
            });
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.e(PAYEE_FRAGMENT_TAG, throwable.toString());
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mConnectionProgressDialog = new ProgressDialog(getActivity(), R.style.ProgressTheme);
        mConnectionProgressDialog.setMessage(getString(R.string.please_wait));
        mConnectionProgressDialog.setCancelable(false);
        Map<String,String> maps = new HashMap<>();
        maps.put("Authorization", "Bearer " + CitiApplication.getInstance().getClient().getToken());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", "anypresence");
        RemoteRequest request = new RemoteRequest();
        request.setHeaders(maps);
        request.setParameters(parameters);
        request.setPath(CitiConstants.BACKEND_URL + "/" + CitiConstants.VERSION + "/payees");
        request.setQuery(RetailBankingPayee.Scopes.ALL);
        request.setRequestMethod("GET");
        mConnectionProgressDialog.show();
        RetailBankingPayee.queryInBackground(request, RetailBankingPayee.class, callback);
        return inflater.inflate(R.layout.fragment_payee_list, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setupPayeeView(List<RetailBankingPayee> listPayees) {
        payeeList= (ListView) getView().findViewById(R.id.payee_list_view);
        adapter=new PayeeAdapter(getActivity(), listPayees);
        payeeList.setAdapter(adapter);
    }

}