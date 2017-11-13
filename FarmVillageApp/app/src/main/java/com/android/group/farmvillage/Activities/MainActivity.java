package com.android.group.farmvillage.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.android.group.farmvillage.Adapteur.map_adapt;
import com.android.group.farmvillage.Modele.Batiment;
import com.android.group.farmvillage.Modele.TypeBatiment;
import com.android.group.farmvillage.R;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Vector<Batiment> listBatiment = new Vector<Batiment>();

    //Déclaration de l'adapteur MarketAdapteur
    public map_adapt mapAdapteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Batiment bat1 = new Batiment(1,TypeBatiment.Entrepot, false, 1, 1);
        Batiment bat2 = new Batiment(1,TypeBatiment.HDV, false, 1, 1);
        Batiment bat3 = new Batiment(1,TypeBatiment.Ferme, false, 1, 1);
        Batiment bat4 = new Batiment(1,TypeBatiment.HDV, false, 1, 1);
        Batiment bat5 = new Batiment(1,TypeBatiment.Ferme, false, 1, 1);
        Batiment bat6 = new Batiment(1,TypeBatiment.HDV, false, 1, 1);
        Batiment bat7 = new Batiment(1,TypeBatiment.Ferme, false, 1, 1);
        Batiment bat8 = new Batiment(1,TypeBatiment.HDV, false, 1, 1);
        Batiment bat9 = new Batiment(1,TypeBatiment.Ferme, false, 1, 1);

        listBatiment.add(bat1);
        listBatiment.add(bat2);
        listBatiment.add(bat3);

        mapAdapteur = new map_adapt(listBatiment, this);

        GridView listTest = (GridView) findViewById(R.id.gridMap);

        listTest.setAdapter(mapAdapteur);


    }

}
