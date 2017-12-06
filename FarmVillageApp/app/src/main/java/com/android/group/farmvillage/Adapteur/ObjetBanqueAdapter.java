package com.android.group.farmvillage.Adapteur;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.group.farmvillage.Modele.ObjetBanque;
import com.android.group.farmvillage.R;

import java.util.ArrayList;

/**
 * Created by geoffrey on 05/12/17.
 */

public class ObjetBanqueAdapter extends ArrayAdapter<ObjetBanque> {

    public ArrayList<ObjetBanque> objetBanqueListe;

    public ObjetBanqueAdapter(Context context, ArrayList<ObjetBanque> objetBanqueListe) {
        super(context, 0, objetBanqueListe);
        this.objetBanqueListe=objetBanqueListe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.objetbanque_adapter, null);
        }

        final ObjetBanque ob = getItem(position);
        final TextView obName = (TextView) convertView.findViewById(R.id.obName);
        obName.setText(ob.getsName());




        return convertView;
    }

}
