package com.android.group.farmvillage.Adapteur;

/**
 * Created by gui on 13/11/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.group.farmvillage.Modele.Batiment;
import com.android.group.farmvillage.R;

import java.util.Vector;
import java.util.regex.Pattern;

/**
 * Adapteur lié aux magasins
 */
public class map_adapt extends BaseAdapter {

    private final Vector<Batiment> listBat;
    private Context context;

    /**
     * Contructeur
     *
     * @param listBat
     * @param cont
     */
    public map_adapt(Vector<Batiment> listBat, Context cont) {
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
        return listBat.get(position).getiId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.map_adapt, null);
        }
        final Batiment b = listBat.get(position);

        final TextView test = (TextView) convertView.findViewById(R.id.test);
        test.setText(b.getTbBatiment().getsName());

        return convertView;
    }

}