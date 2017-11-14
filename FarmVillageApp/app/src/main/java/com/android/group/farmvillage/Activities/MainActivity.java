package com.android.group.farmvillage.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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

public class MainActivity extends AppCompatActivity {

    //Déclaration de l'adapteur MarketAdapteur
    public map_adapt mapAdapteur;

    /**
     * Créé le menu horizontal en haut du layout
     * @param menu Le contenu du menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        /*Intent secondeActivite = new Intent(MainActivity.this, LoginActivity.class);

        startActivity(secondeActivite);*/

        setSupportActionBar(toolbar);

        ArrayList<Coordonnees> coord = new ArrayList<Coordonnees>();
        coord.add(new Coordonnees(1, 1));
        Date d = new Date();

        final Building b1 = new Building(true, 1, TypeBuilding.HDV, coord, d, 50);
        final Building b2 = new Building(true, 2, TypeBuilding.Ferme, coord, d, 50);
        final Building b3 = new Building(true, 3, TypeBuilding.Entrepot, coord, d, 50);
        final Building b4 = new Building(true, 4, TypeBuilding.Champs, coord, d, 50);
        final Building b5 = new Building(true, 5, TypeBuilding.HDV, coord, d, 50);
        final Building b6 = new Building(true, 1, TypeBuilding.HDV, coord, d, 50);
        final Building b7 = new Building(true, 2, TypeBuilding.Ferme, coord, d, 50);
        final Building b8 = new Building(true, 3, TypeBuilding.Entrepot, coord, d, 50);
        final Building b9 = new Building(true, 4, TypeBuilding.Champs, coord, d, 50);
        final Building b10 = new Building(true, 5, TypeBuilding.HDV, coord, d, 50);
        final Building b11= new Building(true, 1, TypeBuilding.HDV, coord, d, 50);
        final Building b12= new Building(true, 2, TypeBuilding.Ferme, coord, d, 50);
        final Building b13= new Building(true, 3, TypeBuilding.Entrepot, coord, d, 50);
        final Building b14= new Building(true, 4, TypeBuilding.Champs, coord, d, 50);
        final Building b15= new Building(true, 5, TypeBuilding.HDV, coord, d, 50);

        final ArrayList<Building> listBatiment = new ArrayList<Building>(24);
        for (int i=0; i<24; i++){
            listBatiment.add(i, null);
        }
        listBatiment.set(0, b1);
        listBatiment.set(1, b2);
        listBatiment.set(2, b3);
        listBatiment.set(3, b4);
        listBatiment.set(4, b5);
        listBatiment.set(5, b6);
        listBatiment.set(6, b7);
        listBatiment.set(7, b8);
        listBatiment.set(8, b9);
        listBatiment.set(9, b10);
        listBatiment.set(10, b11);
        listBatiment.set(11, b12);
        listBatiment.set(12, b13);
        listBatiment.set(13, b14);
        listBatiment.set(14, b15);





        Village myVillage = new Village(0001, "villageNom", 100, 100, 100, 100, 50, listBatiment);


        mapAdapteur = new map_adapt(myVillage.getListBuilding(), this);

        GridView listTest = (GridView) findViewById(R.id.gridMap);

        listTest.setAdapter(mapAdapteur);

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position", String.valueOf(position));
                if (listBatiment.get(position)==null){
                    listBatiment.set(position, b1);
                    mapAdapteur.notifyDataSetChanged();
                }
                else{
                    Log.d("vide", listBatiment.get(position).getsName());
                }
            }
        }

        );



    }

    /**
     * Fonction pour récupérer la clé SHA1 pour FB Connect
     */



}
