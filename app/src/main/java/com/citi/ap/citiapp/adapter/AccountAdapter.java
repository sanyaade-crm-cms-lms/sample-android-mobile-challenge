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
import com.citi.ap.citiapp.model.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emi91_000 on 06/03/2015.
 */
public class AccountAdapter extends ArrayAdapter<Account>{
    private OnBalanceTransactionClickListener onBalanceTransactionClickListener;
    public AccountAdapter(Context context) {
        super(context, R.layout.item_account);
        List<Account> accounts=new ArrayList<Account>();
        for (Integer i = 0; i < 10; i++) {
            accounts.add(new Account(i.toString(),"prod_desc"+i,i.toString()));
        }
        addAll(accounts);
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
            final Account account = getItem(position);
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
            holder.id.setText(account.getId());
            holder.accountDesc.setText(account.getProdDesc());
            holder.accountNumber.setText(account.getAccountNumber());
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
        public void onBalanceClicked(int position,Account account);
        public void onTransactionClicked(int position,Account account);
    }

    public void setOnBalanceTransactionClickListener(OnBalanceTransactionClickListener onBalanceTransactionClickListener) {
        this.onBalanceTransactionClickListener = onBalanceTransactionClickListener;
    }
}
