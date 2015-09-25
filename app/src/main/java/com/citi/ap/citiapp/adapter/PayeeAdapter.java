package com.citi.ap.citiapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingPayee;
import com.citi.ap.citiapp.R;

import java.util.List;

/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 30/06/15.
 */
public class PayeeAdapter extends ArrayAdapter<RetailBankingPayee> {

    public PayeeAdapter(Context context, List<RetailBankingPayee> payeeList) {
        super(context, R.layout.item_payee);
        addAll(payeeList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PayeeHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            holder = new PayeeHolder();
            view = inflater.inflate(R.layout.item_payee, parent, false);
            holder.id = (TextView) view.findViewById(R.id.payee_id);
            holder.payee_desc = (TextView) view.findViewById(R.id.payee_desc);
            holder.bank_name = (TextView) view.findViewById(R.id.bank_name);
            holder.payment_method = (TextView) view.findViewById(R.id.payment_method);
            holder.last_payment_amount = (TextView) view.findViewById(R.id.last_payment_amount);
            holder.type = (TextView) view.findViewById(R.id.type);
            view.setTag(holder);
        } else {
            holder = (PayeeHolder) view.getTag();
        }
        final RetailBankingPayee payee = getItem(position);
        holder.id.setText(payee.getId().toString());
        holder.payee_desc.setText(payee.getPayeeDescription());
        holder.bank_name.setText(payee.getBankNameExt());
        holder.payment_method.setText(payee.getPaymentMethod());
        holder.last_payment_amount.setText(payee.getLastPaymentAmt().toString());
        holder.type.setText(payee.getPayeeType().toString());
        return view;
    }

    private static class PayeeHolder {
        TextView id;
        TextView payee_desc;
        TextView bank_name;
        TextView payment_method;
        TextView last_payment_amount;
        TextView type;
    }
}