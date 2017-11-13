package com.android.group.farmvillage.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.group.farmvillage.Adapteur.map_adapt;
import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.Modele.Coordonnees;
import com.android.group.farmvillage.Modele.TypeBuilding;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    //Déclaration de l'adapteur MarketAdapteur
    public map_adapt mapAdapteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Coordonnees> coord = new ArrayList<Coordonnees>();
        coord.add(new Coordonnees(1, 1));
        Date d = new Date();

        Building b1 = new Building(true, 1, TypeBuilding.HDV, coord, d, 50);

        final ArrayList<Building> listBatiment = new ArrayList<Building>(24);
        for (int i=0; i<24; i++){
            listBatiment.add(i, null);
        }
        listBatiment.set(0, b1);








        Village myVillage = new Village(0001, "villageNom", 100, 100, 100, 100, 50, listBatiment);


        mapAdapteur = new map_adapt(myVillage.getListBuilding(), this);

        GridView listTest = (GridView) findViewById(R.id.gridMap);

        listTest.setAdapter(mapAdapteur);

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position", String.valueOf(position));
                if (listBatiment.get(position)==null){
                    Log.d("vide", "ajoute building");
                }
                else{
                    Log.d("vide", listBatiment.get(position).getsName());
                }
            }
        }

        );


    }

}
