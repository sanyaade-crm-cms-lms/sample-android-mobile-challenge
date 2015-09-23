package com.citi.ap.citiapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.rails_droid.RemoteRequest;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingAccount;
import com.citi.ap.citiapp.CitiApplication;
import com.citi.ap.citiapp.CitiConstants;
import com.citi.ap.citiapp.R;
import com.citi.ap.citiapp.activity.BalancesActivity;
import com.citi.ap.citiapp.activity.TransactionsActivity;
import com.citi.ap.citiapp.adapter.AccountAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 03/03/15.
 */
public class AccountFragment extends Fragment{
    private final static String ACCOUNT_FRAGMENT_TAG = "ACCOUNT_FRAGMENT";
    protected ProgressDialog mConnectionProgressDialog;
    private ListView accountList;
    private AccountAdapter adapter;

    private IAPFutureCallback callback = new IAPFutureCallback() {
        @Override
        public void finished(Object o, Throwable throwable) {
            Log.e("ACCOUNT_TAG", o.toString());
        }

        @Override
        public void onSuccess(final Object o) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConnectionProgressDialog.dismiss();
                    setupAccountView((List) o);
                }
            });
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.e(ACCOUNT_FRAGMENT_TAG, throwable.toString());
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mConnectionProgressDialog = new ProgressDialog(getActivity(), R.style.ProgressTheme);
        mConnectionProgressDialog.setMessage(getString(R.string.please_wait));
        mConnectionProgressDialog.setCancelable(false);
        Map<String,String> maps = new HashMap<>();
        maps.put("Authorization", String.valueOf(CitiApplication.getInstance().getClient().getToken()));
        RemoteRequest request = new RemoteRequest();
        request.setContext(CitiApplication.getInstance().getClient());
        request.setHeaders(maps);
        request.setPath(CitiConstants.BACKEND_URL + "/" + CitiConstants.VERSION +  "/accounts");
        request.setQuery(RetailBankingAccount.Scopes.ALL);
        request.setRequestMethod("GET");
        mConnectionProgressDialog.show();
        RetailBankingAccount.queryInBackground(request, RetailBankingAccount.class, callback);
        return inflater.inflate(R.layout.fragment_account_list, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setupAccountView(List<RetailBankingAccount> listAccounts) {
        accountList= (ListView) getView().findViewById(R.id.account_list_view);
        adapter=new AccountAdapter(getActivity(), listAccounts);
        accountList.setAdapter(adapter);
        adapter.setOnBalanceTransactionClickListener(new AccountAdapter.OnBalanceTransactionClickListener() {
            @Override
            public void onBalanceClicked(int position, RetailBankingAccount account) {
                startActivity(new Intent(getActivity(), BalancesActivity.class));
            }

            @Override
            public void onTransactionClicked(int position, RetailBankingAccount account) {
                startActivity(new Intent(getActivity(), TransactionsActivity.class));
            }
        });
    }

}