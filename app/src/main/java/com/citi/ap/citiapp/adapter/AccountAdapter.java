package com.citi.ap.citiapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.citi.ap.citiapp.R;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emi91_000 on 06/03/2015.
 */
public class AccountAdapter extends ArrayAdapter<RetailBankingAccount>{
    private OnBalanceTransactionClickListener onBalanceTransactionClickListener;
    public AccountAdapter(Context context, List<RetailBankingAccount> listAccounts) {
        super(context, R.layout.item_account);
        addAll(listAccounts);
    }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final AccountHolder holder;
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
                holder = new AccountHolder();
                view = inflater.inflate(R.layout.item_account, parent, false);
                holder.id = (TextView) view.findViewById(R.id.account_id);
                holder.accountDesc = (TextView)view.findViewById(R.id.account_desc);
                holder.accountNumber=(TextView)view.findViewById(R.id.account_number);
                holder.balanceButton= (Button) view.findViewById(R.id.balances_button);

                holder.transactionButton= (Button) view.findViewById(R.id.transactions_button);
                view.setTag(holder);
            } else {
                holder = (AccountHolder) view.getTag();
            }
            final RetailBankingAccount account = getItem(position);
            holder.balanceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onBalanceTransactionClickListener !=null)
                        onBalanceTransactionClickListener.onBalanceClicked(position,account);
                }
            });
            holder.transactionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onBalanceTransactionClickListener !=null)
                        onBalanceTransactionClickListener.onTransactionClicked(position,account);
                }
            });
            holder.id.setText(account.getId().toString());
            holder.accountDesc.setText(account.getProductDescription());
            holder.accountNumber.setText(account.getDisplayAcctNo());
            return view;
        }


        private static class AccountHolder {
            TextView id;
            TextView accountDesc;
            TextView accountNumber;
            Button balanceButton;
            Button transactionButton;
        }

    public interface OnBalanceTransactionClickListener{
        public void onBalanceClicked(int position, com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingAccount account);
        public void onTransactionClicked(int position, com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingAccount account);
    }

    public void setOnBalanceTransactionClickListener(OnBalanceTransactionClickListener onBalanceTransactionClickListener) {
        this.onBalanceTransactionClickListener = onBalanceTransactionClickListener;
    }
}
