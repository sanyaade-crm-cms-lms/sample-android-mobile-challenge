package com.citi.ap.citiapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anypresence.rails_droid.APFutureCallback;
import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.rails_droid.RemoteObject;
import com.anypresence.rails_droid.RemoteRequest;
import com.anypresence.rails_droid.RemoteRequestException;
import com.anypresence.rails_droid.RemoteRequestResult;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingAccount;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingAccountFundTransfer;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingAccountTransaction;
import com.citi.ap.citiapp.CitiApplication;
import com.citi.ap.citiapp.CitiConstants;
import com.citi.ap.citiapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 03/03/15.
 */
public class TxfFragment extends Fragment{
    public static final String TXF_FRAGMENT_TAG = TxfFragment.class.getSimpleName();
    protected ProgressDialog mConnectionProgressDialog;
    private EditText customerId;
    private EditText sourceAccId;
    private EditText amount;
    private EditText transactionDate;
    private EditText paymentType;
    private EditText currency;
    private EditText destAccId;
    private EditText memo;
    private EditText description;
    private EditText payeeId;
    private EditText type;
    private IAPFutureCallback callback = new IAPFutureCallback() {
        @Override
        public void finished(Object o, Throwable throwable) {
            Log.d(TXF_FRAGMENT_TAG, o.toString());
        }

        @Override
        public void onSuccess(final Object o) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (o != null) {
                        Log.d(TXF_FRAGMENT_TAG, o.toString());
                        Toast.makeText(getActivity(), "Reference Number: ", Toast.LENGTH_SHORT).show();
                    }
                    customerId.setText("");
                    amount.setText("");
                    transactionDate.setText("");
                    paymentType.setText("");
                    currency.setText("");
                    destAccId.setText("");
                    memo.setText("");
                    description.setText("");
                    sourceAccId.setText("");
                    payeeId.setText("");
                    type.setText("");
                }
            });
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.e(TXF_FRAGMENT_TAG, throwable.toString());
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.txf, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mConnectionProgressDialog = new ProgressDialog(getActivity(), R.style.ProgressTheme);
        mConnectionProgressDialog.setMessage(getString(R.string.please_wait));
        mConnectionProgressDialog.setCancelable(false);
        customerId = (EditText) getActivity().findViewById(R.id.txf_customer_id);
        amount = (EditText) getActivity().findViewById(R.id.txf_amount);
        transactionDate = (EditText) getActivity().findViewById(R.id.txf_transaction_date);
        paymentType = (EditText) getActivity().findViewById(R.id.txf_payment_type);
        currency = (EditText) getActivity().findViewById(R.id.txf_currency);
        destAccId = (EditText) getActivity().findViewById(R.id.txf_dest_acc_id);
        memo = (EditText) getActivity().findViewById(R.id.txf_memo);
        description = (EditText) getActivity().findViewById(R.id.txf_payee_description);
        sourceAccId = (EditText) getActivity().findViewById(R.id.txf_source_acc_id);
        payeeId = (EditText) getActivity().findViewById(R.id.txf_payee_id);
        type = (EditText) getActivity().findViewById(R.id.txf_payee_type);
        getActivity().findViewById(R.id.submit_btn).setOnClickListener(onSubmitClick);
    }

    private View.OnClickListener onSubmitClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onSubmit2();
        }
    };

    private void onSubmit() {
        Map<String,String> maps = new HashMap<>();
        maps.put("Authorization","Bearer " + CitiApplication.getInstance().getClient().getToken());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", "anypresence");
        RemoteRequest request = new RemoteRequest();
        request.setContext(CitiApplication.getInstance().getClient());
        request.setHeaders(maps);
        request.setParameters(parameters);
        request.setPath(CitiConstants.BACKEND_URL + "/" + CitiConstants.VERSION + "/accounts/" + destAccId.getText().toString() + "/fund_transfers");
        request.setQuery(RetailBankingAccountFundTransfer.Scopes.ALL);
        request.setRequestMethod("POST");
        //Account.queryInBackground(request, Account.class, callback);

        JSONObject payload = new JSONObject();
        try {
            payload.put("amount", amount.getText().toString());
            payload.put("destination_account_id", destAccId.getText().toString());
            payload.put("payee_type", type.getText());
            payload.put("payment_type", paymentType.getText());
            payload.put("currency", currency.getText().toString());
            payload.put("memo", memo.getText().toString());
            payload.put("payee_description", description.getText().toString());
            payload.put("payee_id", payeeId.getText().toString());
            payload.put("transaction_date", transactionDate.getText().toString());
            payload.put("destination_id", customerId.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setPayload(payload.toString());



//        FundTransfer txf = new FundTransfer();
//        txf.setAmount(Integer.parseInt(amount.getText().toString()));
//        txf.setCurrency(currency.getText().toString());
//        txf.setDestinationAccountId(destAccId.getText().toString());
//        txf.setMemo(memo.getText().toString());
//        txf.setPayeeDesc(description.getText().toString());
//        txf.setPayeeId(payeeId.getText().toString());
//        txf.setPayeeType(Integer.parseInt(type.getText().toString()));
//        txf.setTransactionDate(transactionDate.getText().toString());
//        txf.setPaymentType(Integer.parseInt(paymentType.getText().toString()));
//        txf.setDestinationId(customerId.getText().toString());
        //txf.saveInBackground(callback);

        RemoteRequestResult<RetailBankingAccountFundTransfer> remoteRequestResult = new RemoteRequestResult<>(RetailBankingAccountFundTransfer.class);
        try {
            RetailBankingAccountFundTransfer.requestInBackground(RetailBankingAccountFundTransfer.class, "", request, remoteRequestResult, callback);
        } catch (RemoteRequestException e) {
            e.printStackTrace();
        }
    }

    private void onSubmit2() {
        Map<String,String> maps = new HashMap<>();
        maps.put("Authorization","Bearer " + CitiApplication.getInstance().getClient().getToken());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", "anypresence");
        RemoteRequest request = new RemoteRequest();
        request.setContext(CitiApplication.getInstance().getClient());
        request.setHeaders(maps);
        request.setParameters(parameters);
        request.setPath(CitiConstants.BACKEND_URL + "/" + CitiConstants.VERSION + "/accounts/" + destAccId.getText().toString() + "/fund_transfers");
        request.setQuery(RetailBankingAccountFundTransfer.Scopes.ALL);
        request.setRequestMethod("POST");

        JSONObject payload = new JSONObject();
        try {
            payload.put("amount", amount.getText().toString());
            payload.put("destination_account_id", destAccId.getText().toString());
            payload.put("payee_type", type.getText());
            payload.put("payment_type", paymentType.getText());
            payload.put("currency", currency.getText().toString());
            payload.put("memo", memo.getText().toString());
            payload.put("payee_description", description.getText().toString());
            payload.put("payee_id", payeeId.getText().toString());
            payload.put("transaction_date", transactionDate.getText().toString());
            payload.put("destination_id", customerId.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setPayload(payload.toString());

        RemoteRequestResult<RetailBankingAccountFundTransfer> result = new RemoteRequestResult<>(RetailBankingAccountFundTransfer.class);
        try {
            RemoteObject.requestInBackground(RetailBankingAccountFundTransfer.class, request.getPayload(), request, result, new APFutureCallback<RetailBankingAccountFundTransfer>() {
                @Override
                public void finished(RetailBankingAccountFundTransfer arg0, Throwable arg1) {
                    if (arg1 == null) {
                        System.out.println("got: " + arg0.getReferenceNumber());
                    }
                }
            });
        } catch (RemoteRequestException e) {
            e.printStackTrace();
        }
    }

}