package com.android.group.farmvillage.Activities;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.group.farmvillage.Adapteur.map_adapt;
import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.Modele.Coordonnees;
import com.android.group.farmvillage.Modele.Ressource;
import com.android.group.farmvillage.Modele.TypeBuilding;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

        final Building b1 = new Building(true, 5, TypeBuilding.HDV, 0, d, 50);

        final ArrayList<Building> listBatiment = new ArrayList<Building>(24);
        for (int i=0; i<24; i++){
            listBatiment.add(i, null);
        }

        final Village myVillage = new Village(0001, "villageNom", 1100, 1100, 1100, 1100, 50, listBatiment);

        myVillage.addBuilding(b1);


        mapAdapteur = new map_adapt(myVillage.getListBuilding(), this);

        GridView listTest = (GridView) findViewById(R.id.gridMap);

        listTest.setAdapter(mapAdapteur);

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("position", String.valueOf(position));
                if (listBatiment.get(position)==null){
                    final String [] batimentDispo = new String[6];
                    final String [] nomBatimentDispo = new String[6];
                    int cpt=0;
                    for (TypeBuilding tb : TypeBuilding.values()){
                        batimentDispo[cpt]=tb.name();
                        nomBatimentDispo[cpt]=tb.getsName();
                        cpt++;
                    }
                    final String[] batAcreer = {null};
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Choisir un batiment");
                    builder.setSingleChoiceItems(nomBatimentDispo,-1, new

                            DialogInterface.OnClickListener()

                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("test", batimentDispo[which]);
                                    batAcreer[0] =batimentDispo[which];

                                }
                            });
                    builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TypeBuilding tb = TypeBuilding.valueOf(batAcreer[0]);
                            Date d = new Date();
                            Building newB = new Building(true, 0, tb, position, d, 0);
                            myVillage.addBuilding(newB);
                            mapAdapteur.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();


                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(myVillage.getListBuilding().get(position).getsName()+" niv "+myVillage.getListBuilding().get(position).getiLevel());
                    ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
                    String besoin = "";
                    for(Ressource res : ressources){
                        besoin+=res.getType()+" x"+res.getQte()+"\n";
                    }
                    builder.setMessage("Pour passer de niveau il faut : \n"+
                        besoin);
                    builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNeutralButton("Détruire", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    boolean bool = true;
                    int cpt = 0;
                    ArrayList<Ressource> villageRessources = myVillage.getAllRessource();
                    while(cpt<4 && bool){
                        Log.d("comparaison", String.valueOf(villageRessources.get(cpt).getQte())+" | "+String.valueOf(ressources.get(cpt).getQte()));
                        if (villageRessources.get(cpt).getQte()<ressources.get(cpt).getQte()){
                            bool=false;
                        }
                        cpt++;
                    }
                    builder.setPositiveButton("Améliorer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog d = builder.show();

                    if (bool) {
                        d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                    else {
                        d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                }
            }
        }

        );


    }

}
