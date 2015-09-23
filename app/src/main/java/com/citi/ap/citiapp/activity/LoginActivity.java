package com.citi.ap.citiapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.rails_droid.RemoteRailsConfig;
import com.anypresence.rails_droid.RemoteRequest;
import com.anypresence.rails_droid.http.RequestMethod;
import com.anypresence.rails_droid.APFutureCallback;
import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.rails_droid.RemoteObject;
import com.anypresence.rails_droid.RemoteRequest;
import com.anypresence.rails_droid.RemoteRequestException;
import com.anypresence.rails_droid.RemoteRequestResult;
import com.anypresence.sdk.callbacks.APCallback;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingLogin;
import com.anypresence.sdk.http.HttpAdapter;
import com.citi.ap.citiapp.CitiApplication;
import com.citi.ap.citiapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 27/02/15.
 */
public class LoginActivity extends Activity {

    public static final String LOGIN_ACTIVITY_TAG = LoginActivity.class.getSimpleName();
    protected ProgressDialog mConnectionProgressDialog;
    private TextView username;
    private TextView password;
    private EditText clientId;
    private TextView.OnEditorActionListener passwordActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onLogin();
                return true;
            }
            return false;
        }
    };

    private View.OnClickListener onSignInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onLogin();
        }

    };

    private APFutureCallback callback = new APFutureCallback<RetailBankingLogin>() {
        @Override
        public void finished(RetailBankingLogin object, Throwable ex) {
            Log.d(LOGIN_ACTIVITY_TAG, object.toString());
        }

        @Override
        public void onSuccess(RetailBankingLogin o) {
            Log.d(LOGIN_ACTIVITY_TAG, o.toString());
            loginSuccessful((RetailBankingLogin)o);
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.e(LOGIN_ACTIVITY_TAG, throwable.toString());
            unsuccessfulLogin(throwable);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConnectionProgressDialog = new ProgressDialog(this, R.style.ProgressTheme);
        mConnectionProgressDialog.setMessage(getString(R.string.please_wait));
        mConnectionProgressDialog.setCancelable(false);
        setContentView(R.layout.activity_login);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        clientId = (EditText) findViewById(R.id.client_id_edt);
        clientId.setEnabled(false);
        password.setOnEditorActionListener(passwordActionListener);
        this.findViewById(R.id.login_btn).setOnClickListener(onSignInClick);
    }

    public void callLogin(RetailBankingLogin client) {
        client.saveInBackground(callback);
    }

    private void onLogin() {
        String usernameValue = username.getText().toString();
        String passwordValue = password.getText().toString();

        if (usernameValue.isEmpty() || passwordValue.isEmpty()) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LoginActivity.this, getString(R.string.username_password_empty), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            mConnectionProgressDialog.show();
            Map<String, String> parameters = new HashMap<>();
            parameters.put("client_id", "anypresence");
            RemoteRequest request = new RemoteRequest();
            request.setContext(new RetailBankingLogin());
            request.setParameters(parameters);
            request.setPath("http://citimobilechallenge.anypresenceapp.com/retailbanking/v1/login");
            request.setQuery(RetailBankingLogin.Scopes.ALL);
            request.setRequestMethod("POST");

            JSONObject payload = new JSONObject();
            try {
                payload.put("username", usernameValue);
                payload.put("password", passwordValue);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            request.setPayload(payload.toString());
            RemoteRequestResult<RetailBankingLogin> result = new RemoteRequestResult<>(RetailBankingLogin.class);
            try {
                RemoteObject.requestInBackground(RetailBankingLogin.class, "", request, result, callback);
            } catch (RemoteRequestException e) {
                e.printStackTrace();
            }
        }
    }

    public void unsuccessfulLogin(final Throwable e) {
        mConnectionProgressDialog.dismiss();
        runOnUiThread(new Runnable() {
            public void run() {
                    Toast.makeText(LoginActivity.this, R.string.invalid_username_or_password, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginSuccessful(RetailBankingLogin client) {
        mConnectionProgressDialog.dismiss();
        CitiApplication.getInstance().setClient(client);
        Intent i = new Intent(this, CitiMainActivity.class);
        startActivity(i);
        LoginActivity.this.finish();
    }
}
