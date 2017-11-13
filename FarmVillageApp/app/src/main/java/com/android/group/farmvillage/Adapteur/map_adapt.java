package com.android.group.farmvillage.Adapteur;

/**
 * Created by gui on 13/11/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.R;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Adapteur lié aux magasins
 */
public class map_adapt extends BaseAdapter {

    private final ArrayList<Building> listBat;
    private Context context;

    /**
     * Contructeur
     *
     * @param listBat
     * @param cont
     */
    public map_adapt(ArrayList<Building> listBat, Context cont) {
        this.listBat = listBat;
        this.context = cont;
    }

    @Override
    public int getCount() {
        return listBat.size();
    }

    @Override
    public Object getItem(int position) {
        return listBat.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (listBat.get(position) != null) {
            return listBat.get(position).getiId();
        }
        else {
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.map_adapt, null);
        }
        final Building b = listBat.get(position);

        final TextView test = (TextView) convertView.findViewById(R.id.test);
        if (b!=null)
        test.setText(b.getTbBuilding().getsName());

        return convertView;
    }

}