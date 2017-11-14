package com.android.group.farmvillage.Adapteur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffrey on 14/11/17.
 */

public class MapAdapter extends ArrayAdapter<Building> {

    public ArrayList<Building> listBuilding;

    public MapAdapter(Context context, ArrayList<Building> listBuilding) {
        super(context, 0, listBuilding);
        this.listBuilding=listBuilding;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.map_adapt, null);
        }
        final Building b = getItem(position);

        final TextView test = (TextView) convertView.findViewById(R.id.test);
        if (b!=null)
            test.setText(b.getTbBuilding().getsName());

        return convertView;
    }

    public void enleve(int position)
    {
        listBuilding.remove(position);
        this.notifyDataSetChanged();
    }




}
