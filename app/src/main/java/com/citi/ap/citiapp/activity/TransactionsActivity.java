package com.citi.ap.citiapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.citi.ap.citiapp.R;
import com.citi.ap.citiapp.adapter.BalanceAdapter;
import com.citi.ap.citiapp.adapter.TransactionAdapter;

/**
 * Created by emi91_000 on 06/03/2015.
 */
public class TransactionsActivity extends FragmentActivity {
    private ListView transactionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        transactionList= (ListView) findViewById(R.id.generic_listview);
        transactionList.setAdapter(new TransactionAdapter(this));
    }

}
