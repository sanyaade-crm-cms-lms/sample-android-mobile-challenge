package com.citi.ap.citiapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.citi.ap.citiapp.R;
import com.citi.ap.citiapp.adapter.BalanceAdapter;

/**
 * Created by emi91_000 on 06/03/2015.
 */
public class BalancesActivity extends FragmentActivity {
    private ListView balanceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        balanceList= (ListView) findViewById(R.id.generic_listview);
        balanceList.setAdapter(new BalanceAdapter(this));
    }

}
