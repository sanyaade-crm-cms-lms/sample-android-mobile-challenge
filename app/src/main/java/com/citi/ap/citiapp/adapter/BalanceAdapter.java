package com.citi.ap.citiapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingAccountBalance;
import com.citi.ap.citiapp.R;
import com.citi.ap.citiapp.model.Balance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emi91_000 on 06/03/2015.
 */
public class BalanceAdapter extends ArrayAdapter<RetailBankingAccountBalance>{
    public BalanceAdapter(Context context, List<RetailBankingAccountBalance> listBalances) {
        super(context, R.layout.item_balance);
        addAll(listBalances);
    }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final BalanceHolder holder;
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
                holder = new BalanceHolder();
                view = inflater.inflate(R.layout.item_balance, parent, false);
                holder.value = (TextView) view.findViewById(R.id.balance_value);
                view.setTag(holder);
            } else {
                holder = (BalanceHolder) view.getTag();
            }
            final RetailBankingAccountBalance balance = getItem(position);
            holder.value.setText(balance.getValue().toString());
            return view;
        }


        private static class BalanceHolder {
            TextView value;
        }
}
