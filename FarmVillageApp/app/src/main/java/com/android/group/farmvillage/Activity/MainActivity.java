package com.android.group.farmvillage.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

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


        Batiment bat1 = new Batiment(1,TypeBatiment.HDV, false, 1, 1);

        GridView listTest = (GridView) findViewById(R.id.gridMap);

        listTest.setAdapter(bat1);

    }

}
