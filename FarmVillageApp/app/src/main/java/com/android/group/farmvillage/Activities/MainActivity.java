package com.android.group.farmvillage.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.group.farmvillage.Adapteur.MapAdapter;
import com.android.group.farmvillage.Adapteur.map_adapt;
import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.Modele.Coordonnees;
import com.android.group.farmvillage.Modele.Ressource;
import com.android.group.farmvillage.Modele.TypeBuilding;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Déclaration de l'adapteur MarketAdapteur
    //public map_adapt mapAdapteur;
    public MapAdapter mapAdapteur;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //setSupportActionBar(toolbar);

        ArrayList<Coordonnees> coord = new ArrayList<Coordonnees>();
        coord.add(new Coordonnees(1, 1));
        Date d = new Date();


        final ArrayList<Building> listBatiment = new ArrayList<>(24);
        for (int i=0; i<24; i++){
            listBatiment.add(i, new Building(false, 0, TypeBuilding.Vide, i, d, 0));
        }


        final Village myVillage = new Village(0001, "villageNom", 200, 200, 200, 200, 50, listBatiment);
        Building b1 = new Building(true, 1, TypeBuilding.HDV, 0, d, 0);
        myVillage.addBuilding(b1);



        mapAdapteur = new MapAdapter(getApplicationContext(), myVillage.getListBuilding());

        GridView listTest = (GridView) findViewById(R.id.gridMap);

        listTest.setAdapter(mapAdapteur);

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ArrayList<Building> listBuilding = listBatiment;
                if (listBatiment.get(position).getTbBuilding()==TypeBuilding.Vide){
                    final String [] batimentDispo = new String[TypeBuilding.values().length];
                    final String [] nomBatimentDispo = new String[TypeBuilding.values().length];
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
                            //myVillage.removeBuilding(myVillage.getListBuilding().get(position));
                            Date d = new Date();
                            Building b = new Building(false, 0, TypeBuilding.Vide, position, d, 0);
                            myVillage.addBuilding(b);
                            mapAdapteur.notifyDataSetChanged();

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

    /**
     * Fonction pour récupérer la clé SHA1 pour FB Connect
     */



}
