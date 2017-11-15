package com.android.group.farmvillage.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.group.farmvillage.Adapteur.MapAdapter;
import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.Modele.Coordonnees;
import com.android.group.farmvillage.Modele.Event;
import com.android.group.farmvillage.Modele.Ressource;
import com.android.group.farmvillage.Modele.TypeBuilding;
import com.android.group.farmvillage.Modele.TypeEvent;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Déclaration de l'adapteur MarketAdapteur
    //public map_adapt mapAdapteur;
    public MapAdapter mapAdapteur;
    public Village myVillage;
    public Handler mHandler;
    public boolean eventValidate = true;
    public final static String VillageIntent = "village";


    /**
     * Créé le menu horizontal en haut du layout
     * @param menu Le contenu du menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.orValue).setTitle(String.valueOf(myVillage.getiGold()));
        menu.findItem(R.id.pierreValue).setTitle(String.valueOf(myVillage.getiRock()));
        menu.findItem(R.id.boisValue).setTitle(String.valueOf(myVillage.getiWood()));
        menu.findItem(R.id.foodValue).setTitle(String.valueOf(myVillage.getiFood()));
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler=new Handler();

        // Puis on lance l'intent !

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ArrayList<Coordonnees> coord = new ArrayList<Coordonnees>();
        coord.add(new Coordonnees(1, 1));
        Date d = new Date();


        final ArrayList<Building> listBatiment = new ArrayList<>(24);
        for (int i=0; i<24; i++){
            listBatiment.add(i, new Building(false, 0, TypeBuilding.Vide, i, d, 0));
        }


        myVillage = new Village(0001, "Sparte", 500, 500, 500, 500, 50, listBatiment);
        Building b1 = new Building(true, 1, TypeBuilding.HDV, 0, d, 0);
        myVillage.addBuilding(b1);


        initMainValue(myVillage);

        mapAdapteur = new MapAdapter(getApplicationContext(), myVillage.getListBuilding());

        GridView listTest = (GridView) findViewById(R.id.gridMap);

        listTest.setAdapter(mapAdapteur);

        recolteThread(myVillage);
        eventThread(myVillage);

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ArrayList<Building> listBuilding = listBatiment;
                ArrayList<Ressource> ressourcesDispo = myVillage.getAllRessource();
                if (listBatiment.get(position).getTbBuilding()==TypeBuilding.Vide) {
                    newBuilding(position, ressourcesDispo, myVillage);
                }
                else{
                    buildingModification(position, myVillage);
                }
            }
        }


        );

    }

    /**
     * Initialise les principales valeurs du jeu
     * @param myVillage
     */
    private void initMainValue(Village myVillage){

        //On affiche le nom du village dans le menu du haut
        setTitle(myVillage.getsName());

        //Met à jour les ressources
    }

    private void newBuilding(final int position, ArrayList<Ressource> ressourcesDispo, final Village myVillage) {
        ArrayList<TypeBuilding> buildingDispo = new ArrayList<>();
        for (TypeBuilding tb : TypeBuilding.values()) {
            if (tb != TypeBuilding.Vide) {
                int indexList=0;
                boolean bool=true;
                while(indexList<4 && bool){
                    if (ressourcesDispo.get(indexList).getQte()<tb.constructionPrice().get(indexList).getQte()){
                        bool=false;
                    }
                    indexList++;
                }
                if (bool){
                    buildingDispo.add(tb);
                }
            }
        }
        final String [] batimentDispo = new String[buildingDispo.size()];
        final String [] nomBatimentDispo = new String[buildingDispo.size()];
        int cpt=0;
        for (TypeBuilding tb : buildingDispo){
            batimentDispo[cpt] = tb.name();
            nomBatimentDispo[cpt] = tb.getsName();
            cpt++;
        }
        final String[] batAcreer = {null};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choisir un batiment");
        if(nomBatimentDispo.length!=0) {
            builder.setSingleChoiceItems(nomBatimentDispo, -1, new

                    DialogInterface.OnClickListener()

                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("test", batimentDispo[which]);
                            batAcreer[0] = batimentDispo[which];

                        }
                    });
        }
        else {
            builder.setMessage("Vous ne disposez pas des ressources nécessaires pour contruire un batiment. T'es pauvre. Corialement.");

        }
        builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (batAcreer[0]!= null) {
                    TypeBuilding tb = TypeBuilding.valueOf(batAcreer[0]);
                    Date d = new Date();
                    Building newB = new Building(true, 1, tb, position, d, 0);
                    myVillage.addBuilding(newB);
                    invalidateOptionsMenu();
                    mapAdapteur.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Veillez choisir un batiment quand meme !", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog d = builder.show();

        if (nomBatimentDispo.length!=0) {
            d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        }
        else {
            d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    private void buildingModification(final int position, final Village myVillage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(myVillage.getListBuilding().get(position).getsName()+" niv "+myVillage.getListBuilding().get(position).getiLevel());
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
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
                invalidateOptionsMenu();
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
                myVillage.getListBuilding().get(position).levelUp();
                myVillage.setiWood(myVillage.getiWood()-ressources.get(0).getQte());
                myVillage.setiFood(myVillage.getiFood()-ressources.get(1).getQte());
                myVillage.setiRock(myVillage.getiRock()-ressources.get(2).getQte());
                myVillage.setiGold(myVillage.getiGold()-ressources.get(3).getQte());
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

    public void recolteThread(final Village myVillage) {
        final Thread thRecolte = new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    public void run()
                    {
                        myVillage.recolte();
                        invalidateOptionsMenu();
                    }
                };
                timer.schedule( task, 0L ,1000L);
            }
        });
        thRecolte.start(); //lance le thread

    }

    public void eventThread(final Village myVillage){
        final Thread thEvent = new Thread(new Runnable() {
            @Override
            public void run() {
                Long time = 10000L;
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    public void run()
                    {
                        if (eventValidate) {
                            eventValidate=false;
                            mHandler.post(new Runnable() {
                                public void run() {
                                    TypeEvent[] eventPossible = TypeEvent.values();
                                    Random randomGenerator = new Random();
                                    int rdm = randomGenerator.nextInt(9);
                                    final Event e = new Event(eventPossible[rdm]);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle(e.getsTitre());
                                    builder.setMessage(e.getsDefinition());
                                    builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            myVillage.evement(e.getrConsequence());
                                            invalidateOptionsMenu();
                                            eventValidate=true;
                                        }
                                    });
                                    builder.show();

                                }
                            });
                        }
                    }
                };
                timer.schedule( task, time ,time);
            }
        });
        thEvent.start(); //lance le thread
    }


    /**
     * Fonction pour récupérer la clé SHA1 pour FB Connect
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exchangeMenu:

                FonctionMissoum();
                ;
                Log.d("ok", "ca passe");
        }
        return super.onOptionsItemSelected(item);
    }

    private void FonctionMissoum(){
       // CreationBanqueDonneeMissoum();
        Intent secondeActivite = new Intent(MainActivity.this, ExchangeActivity.class);
        // On rajoute un extra
        secondeActivite.putExtra(VillageIntent,myVillage);
        startActivity(secondeActivite);

    }

}