package com.citi.ap.citiapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.rails_droid.RemoteRequestException;
import com.anypresence.sdk.citi.models.LoginInfo;
import com.citi.ap.citiapp.CitiApplication;
import com.citi.ap.citiapp.CitiConstants;
import com.citi.ap.citiapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;

import interfaces.LoginListener;

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

    public void callLogin(LoginInfo client, final LoginListener listener) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", client.getUsername());
            obj.put("password", client.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            LoginInfo info = new LoginInfo();
            info.setUsername("john");
            info.setPassword("password");
            info.setIsEligibleForOffers(false);
            info.setIsPersonToPersonRequired(false);
            info.setToken("");
            try {
                info.save();
            } catch (RemoteRequestException e) {
                e.printStackTrace();
            } finally {
                loginSuccessful();
            }
        }
        //postData(obj, listener);
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
            LoginInfo client = new LoginInfo();
            client.setUsername(usernameValue);
            client.setPassword(passwordValue);
            callLogin(client, loginListener);
        }
    }

    public void unsuccessfulLogin(final Throwable e) {
        mConnectionProgressDialog.dismiss();
        runOnUiThread(new Runnable() {
            public void run() {
                if (e instanceof UnknownHostException) {
                    Toast.makeText(LoginActivity.this, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(LoginActivity.this, R.string.invalid_username_or_password, Toast.LENGTH_LONG).show();
            }
        });
    }

    private LoginListener loginListener = new LoginListener() {
        @Override
        public void onLoginFailed(boolean isConnected, Throwable ex) {
            unsuccessfulLogin(ex);
        }

        @Override
        public void onLoginSuccess(LoginInfo client) {
            if (client instanceof LoginInfo) {
                CitiApplication.getInstance().setClient(client);
                loginSuccessful();
                mConnectionProgressDialog.dismiss();
            }
        }
    };

    private void loginSuccessful() {
        Intent i = new Intent(this, CitiMainActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("loginInfo", CitiApplication.getInstance().getClient());
        i.putExtras(args);
        startActivity(i);
        LoginActivity.this.finish();
    }
}
