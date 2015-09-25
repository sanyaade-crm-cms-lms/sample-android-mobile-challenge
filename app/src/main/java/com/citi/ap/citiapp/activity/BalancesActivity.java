package com.citi.ap.citiapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.rails_droid.RemoteRequest;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingAccountBalance;
import com.citi.ap.citiapp.CitiApplication;
import com.citi.ap.citiapp.CitiConstants;
import com.citi.ap.citiapp.R;
import com.citi.ap.citiapp.adapter.BalanceAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by emi91_000 on 06/03/2015.
 */
public class BalancesActivity extends FragmentActivity {
    private ListView balanceList;
    private BalanceAdapter adapter;
    private IAPFutureCallback callback = new IAPFutureCallback() {
        @Override
        public void finished(Object o, Throwable throwable) {
            Log.e("BALANCE_TAG", o.toString());
        }

        @Override
        public void onSuccess(final Object o) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupAccountView((List) o);
                }
            });
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.e("BALANCE_TAG", throwable.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Map<String,String> headers = new HashMap<>();
        Map<String, String> parameters = new HashMap<>();
        headers.put("Authorization","Bearer " + CitiApplication.getInstance().getClient().getToken());
        parameters.put("client_id", "anypresence");
        Bundle b = getIntent().getExtras();
        String account_id = b.get("account_id").toString();
        RemoteRequest request = new RemoteRequest();
        request.setHeaders(headers);
        request.setParameters(parameters);
        request.setPath(CitiConstants.BACKEND_URL + "/" + CitiConstants.VERSION + "/accounts/" + account_id +"/balances");
        request.setQuery(RetailBankingAccountBalance.Scopes.ALL);
        request.setRequestMethod("GET");
        RetailBankingAccountBalance.queryInBackground(request, RetailBankingAccountBalance.class, callback);
    }

    private void setupAccountView(List<RetailBankingAccountBalance> listBalances) {
        balanceList = (ListView) this.findViewById(R.id.generic_listview);
        adapter=new BalanceAdapter(this, listBalances);
        balanceList.setAdapter(adapter);
    }

}
