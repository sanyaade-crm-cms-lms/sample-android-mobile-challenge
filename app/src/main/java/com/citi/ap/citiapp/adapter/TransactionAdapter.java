package com.citi.ap.citiapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.citi.ap.citiapp.R;
import com.citi.ap.citiapp.model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by emi91_000 on 06/03/2015.
 */
public class TransactionAdapter extends ArrayAdapter<Transaction>{
    public TransactionAdapter(Context context) {
        super(context, R.layout.item_transaction);
        List<Transaction> transactions=new ArrayList<Transaction>();
        for (Integer i = 0; i < 9; i++) {
            transactions.add(new Transaction(i.toString(),new Date(),"type"+i,"description"+i,"activity"+i));
        }
        addAll(transactions);
    }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final TransactionHolder holder;
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
                holder = new TransactionHolder();
                view = inflater.inflate(R.layout.item_transaction, parent, false);
                holder.id = (TextView) view.findViewById(R.id.transaction_id);
                holder.transactionType=(TextView) view.findViewById(R.id.transaction_type);
                holder.description=(TextView) view.findViewById(R.id.description);
                holder.activity=(TextView) view.findViewById(R.id.activity);
                holder.pendingDate =(TextView) view.findViewById(R.id.pending_date);
                view.setTag(holder);
            } else {
                holder = (TransactionHolder) view.getTag();
            }
            final Transaction transaction = getItem(position);
            holder.id.setText(getContext().getString(R.string.transaction_id,transaction.getId()));
            holder.transactionType.setText(getContext().getString(R.string.transaction_type,transaction.getTransactionType()));
            holder.description.setText(getContext().getString(R.string.transaction_description, transaction.getDescription()));
            holder.pendingDate.setText(getContext().getString(R.string.pending_date, transaction.getPendingDate()));
            holder.activity.setText(getContext().getString(R.string.activity,transaction.getActivity()));
            return view;
        }


        private static class TransactionHolder {
            TextView id;
            TextView pendingDate;
            TextView transactionType;
            TextView description;
            TextView activity;
        }
}
