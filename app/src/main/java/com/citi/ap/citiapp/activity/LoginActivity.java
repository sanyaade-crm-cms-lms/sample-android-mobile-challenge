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
import com.anypresence.sdk.citi.models.LoginInfo;
import com.citi.ap.citiapp.CitiApplication;
import com.citi.ap.citiapp.R;


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

    private IAPFutureCallback callback = new IAPFutureCallback() {
        @Override
        public void finished(Object object, Throwable ex) {
            Log.d(LOGIN_ACTIVITY_TAG, object.toString());
        }

        @Override
        public void onSuccess(Object o) {
            Log.d(LOGIN_ACTIVITY_TAG, o.toString());
            loginSuccessful((LoginInfo)o);
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

    public void callLogin(LoginInfo client) {
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
            //mConnectionProgressDialog.show();
            LoginInfo client = new LoginInfo();
            client.setUsername(usernameValue);
            client.setPassword(passwordValue);
            callLogin(client);
        }
    }

    public void unsuccessfulLogin(final Throwable e) {
        //mConnectionProgressDialog.dismiss();
        runOnUiThread(new Runnable() {
            public void run() {
                    Toast.makeText(LoginActivity.this, R.string.invalid_username_or_password, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginSuccessful(LoginInfo client) {
        CitiApplication.getInstance().setClient(client);
        Intent i = new Intent(this, CitiMainActivity.class);
//        Bundle args = new Bundle();
//        args.putSerializable("loginInfo", CitiApplication.getInstance().getClient());
//        i.putExtras(args);
        startActivity(i);
        LoginActivity.this.finish();
    }
}