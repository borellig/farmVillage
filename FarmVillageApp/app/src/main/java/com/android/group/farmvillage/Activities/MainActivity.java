package com.android.group.farmvillage.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.group.farmvillage.Adapteur.MapAdapter;
import com.android.group.farmvillage.Adapteur.ObjetBanqueAdapter;
import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.Modele.Event;
import com.android.group.farmvillage.Modele.ObjetBanque;
import com.android.group.farmvillage.Modele.Ressource;
import com.android.group.farmvillage.Modele.TypeBuilding;
import com.android.group.farmvillage.Modele.TypeEvent;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;
import com.android.group.farmvillage.Tools.BackgroundTask;
import com.android.group.farmvillage.Tools.Task;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;



public class MainActivity extends AppCompatActivity {

    //Déclaration de l'adapteur
    //public map_adapt mapAdapteur;
    public MapAdapter mapAdapteur;
    public Village myVillage;
    public Handler mHandler;
    public boolean eventValidate = true;
    public final static String VillageIntent = "village";

    public GridView listTest;
    public final int nbCase=30;


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
        Task.setMyVillage((Village)getIntent().getSerializableExtra("village"));
        Task.actionRecolte();

        //Récupère la résolution du support
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if(width<=2000 && height <= 1200){
            setContentView(R.layout.activity_main_small);
        }else{
            setContentView(R.layout.activity_main);
        }

        mHandler=new Handler();

        myVillage=(Village)getIntent().getSerializableExtra("village");
        mapAdapteur = new MapAdapter(getApplicationContext(), myVillage.getListBuilding());
        listTest = (GridView) findViewById(R.id.gridMap);
        listTest.setAdapter(mapAdapteur);
        listTest.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                for (Building b : myVillage.getListBuilding()) {
                    if (!b.isbEnable() && b.getTbBuilding() != TypeBuilding.Vide) {
                        int dureeConstruction = (int) Math.pow(b.getTbBuilding().getDuration(), 1 + ((double) (b.getiLevel() - 1) / 10));
                        if (b.getdConstruct().getTime() + dureeConstruction > new Date().getTime()) {
                            Building construction = new Building(false, b.getiLevel(), TypeBuilding.Construction, b.getIndexList(), b.getdConstruct(), b.getiMilitaryCount());
                            View view = listTest.getChildAt(b.getIndexList());
                            TextView tv = (TextView) view.findViewById(R.id.timeConstruct);
                            ImageView iv = (ImageView) view.findViewById(R.id.parchemin);
                            int dureeRestante =  (int) (b.getdConstruct().getTime() + dureeConstruction - new Date().getTime());
                            b.setiTpsConstruct(dureeRestante);
                            myVillage.resumeConstruction(construction, b);
                            threadConstruction(b.getTbBuilding(), b, myVillage, tv, iv);
                            b.setbEnable(true);
                        }
                    }
                }
            }
        });

        myVillage.recolteServeur();










        //Test musique
        MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.ageofempire);
        ring.start();

        final ArrayList<Building> listBatiment = myVillage.getListBuilding();

        initMainValue(myVillage);



        recolteThread();//myVillage);
        eventThread(myVillage);

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ArrayList<Building> listBuilding = listBatiment;
                ArrayList<Ressource> ressourcesDispo = myVillage.getAllRessource();

                final TextView timeConstruct = view.findViewById(R.id.timeConstruct);
                final ImageView timeImage = view.findViewById(R.id.parchemin);


                if(position==0 || position==6){
                    myVillage.sauvegarde();
                    Toast.makeText(getApplicationContext(), "Vous ne pouvez pas construire des bâtiments sur une forêt voyons  !!", Toast.LENGTH_LONG).show();
                }else{
                    if(position==5){
                        Toast.makeText(getApplicationContext(), "Vous ne pouvez pas construire des bâtiments sur une carrière voyons  !!", Toast.LENGTH_LONG).show();
                    }else{
                        if (listBatiment.get(position).getTbBuilding()==TypeBuilding.Vide) {
                            newBuilding(position, ressourcesDispo, myVillage, timeConstruct, timeImage);
                        }
                        else{
                            buildingModification(position, myVillage, timeConstruct, timeImage);
                        }
                    }

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
    }

    private void newBuilding(final int position, ArrayList<Ressource> ressourcesDispo, final Village myVillage, final TextView timeConstruct, final ImageView timeImage) {
        ArrayList<TypeBuilding> buildingDispo=TypeBuilding.getTypeBuildingsDispo(myVillage);
        final String [] batimentDispo = new String[buildingDispo.size()];
        final String [] nomBatimentDispo = new String[buildingDispo.size()];
        int cpt=0;
        for (TypeBuilding tb : buildingDispo){
            batimentDispo[cpt] = tb.name();
            nomBatimentDispo[cpt] = tb.getsName();
            cpt++;
        }
        Log.d("cpt", String.valueOf(cpt));
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
            builder.setMessage("Vous ne disposez pas des ressources nécessaires pour construire un batiment. T'es pauvre. Cordialement.");

        }
        builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (batAcreer[0]!= null) {
                    final TypeBuilding tb = TypeBuilding.valueOf(batAcreer[0]);
                    final Date d = new Date();
                    Building construction = new Building(false, 0, TypeBuilding.Construction, position, d, 0);
                    final Building newB = new Building(true, 1, tb, position, d, 0);
                    myVillage.construction(construction, newB);
                    newB.setiTpsConstruct((int) Math.pow(newB.getTbBuilding().getDuration(), 1 + ((double) (newB.getiLevel() - 1) / 10)));
                    threadConstruction(tb, newB, myVillage, timeConstruct, timeImage);
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

    private void buildingModification(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(myVillage.getListBuilding().get(position).getsName()+" niv "+myVillage.getListBuilding().get(position).getiLevel());
        TypeBuilding bClicked = myVillage.getListBuilding().get(position).getTbBuilding();
        if(bClicked!=TypeBuilding.Construction) {
            if(bClicked==TypeBuilding.Carriere || bClicked == TypeBuilding.Scierie || bClicked == TypeBuilding.Ferme || bClicked == TypeBuilding.HDV) {
                popUpConstruction(position, myVillage, timeConstruct, timeImage, builder);
            }
            if(bClicked==TypeBuilding.Laboratoire || bClicked == TypeBuilding.Academie){
                popUpRecherche(position, myVillage, timeConstruct, timeImage, builder);
            }
            if(bClicked==TypeBuilding.Entrepot || bClicked == TypeBuilding.Garnison || bClicked == TypeBuilding.Taverne || bClicked == TypeBuilding.Marche) {
                popUpAutre(position, myVillage, timeConstruct, timeImage, builder);
            }
            if(bClicked==TypeBuilding.Banque){
                popUpBanque(position, myVillage, timeConstruct, timeImage, builder);
            }

        }
        else {
            builder.setMessage("Rome ne s'est pas construite en un jour ! Patientez un peu !");
            builder.show();
        }

    }

    private void popUpAutre(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder) {
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
        String besoin = "";
        for (Ressource res : ressources) {
            besoin += res.getType() + " x" + res.getQte() + "\n";
        }
        builder.setMessage("Pour passer de niveau il faut : \n" +besoin);
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("Détruire", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                destructionBuilding(position, myVillage);

            }
        });
        ameliorerBuilding(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void popUpRecherche(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder) {
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
        String besoin = "";
        for (Ressource res : ressources) {
            besoin += res.getType() + " x" + res.getQte() + "\n";
        }
        builder.setMessage("Pour passer de niveau il faut : \n" +besoin);
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("Détruire", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                destructionBuilding(position, myVillage);

            }
        });
        ameliorerBuilding(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void popUpConstruction(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder) {
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
        String besoin = "";
        for (Ressource res : ressources) {
            besoin += res.getType() + " x" + res.getQte() + "\n";
        }
        String typeProd=myVillage.getListBuilding().get(position).getTbBuilding().getsProdutionType();
        switch (typeProd) {
            case "food":
                typeProd = "nourriture";
                break;
            case "wood":
                typeProd = "bois";
                break;
            case "rock":
                typeProd = "pierre";
                break;
            case "gold":
                typeProd = "or";
                break;
        }
        String production = "Production : "+(int)Math.pow(myVillage.getListBuilding().get(position).getTbBuilding().getiProductionCapacity(), 1+(double)myVillage.getListBuilding().get(position).getiLevel()/10)+" "+typeProd+" par secondes";
        String productionLvlUp = "Au niveau suivant la production sera de : "+(int)Math.pow(myVillage.getListBuilding().get(position).getTbBuilding().getiProductionCapacity(), 1+((double)myVillage.getListBuilding().get(position).getiLevel()+1)/10)+" "+typeProd+" par secondes";
        String tempsLvlUp = "L'amélioration au niveau superieur prendra : "+formatSeconde((int) Math.pow(myVillage.getListBuilding().get(position).getTbBuilding().getDuration(), 1 + ((double) (myVillage.getListBuilding().get(position).getiLevel()) / 10)));
        builder.setMessage(production+"\nPour passer de niveau il faut : \n" +besoin+"\n"+productionLvlUp+"\n"+tempsLvlUp);
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("Détruire", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                destructionBuilding(position, myVillage);

            }
        });
        ameliorerBuilding(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void popUpBanque(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder) {
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
        BackgroundTask bgTask = new BackgroundTask();
        ArrayList<ObjetBanque> obl = new ArrayList<>();
        try {
            JSONObject jItems = new JSONObject(String.valueOf(bgTask.execute("http://artshared.fr/andev1/distribue/android/get_items.php?uid="+myVillage.getsUUID()).get()));
            JSONArray jListObjetBanque = new JSONArray(jItems.getString("items"));
            Log.d("items4", jListObjetBanque.toString());
            if (jListObjetBanque != null) {
                for (int i = 0; i < jListObjetBanque.length(); i++) {
                    JSONObject jObjetBanque = new JSONObject(jListObjetBanque.get(i).toString());
                    JSONObject jTemplate = new JSONObject(jObjetBanque.getString("template"));
                    JSONObject jType = new JSONObject(jTemplate.getString("type"));
                    JSONObject jStat = new JSONObject(jObjetBanque.getString("stats"));
                    String obId = jObjetBanque.getString("_id");
                    int obLvl = jTemplate.getInt("level");
                    String obType = jType.getString("name");
                    String obName = jTemplate.getString("name");
                    int obHealth = jStat.getInt("health");
                    int obAttack = jStat.getInt("attack");
                    int obDefense = jStat.getInt("defense");
                    ObjetBanque ob = new ObjetBanque(obId, obLvl, obType, obName, obHealth, obAttack, obDefense);
                    obl.add(ob);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        myVillage.setListeBanque(obl);

        GridView gridObjetBanque = new GridView(getBaseContext());
        gridObjetBanque.setNumColumns(5);
        ObjetBanqueAdapter obAdapter = new ObjetBanqueAdapter(getBaseContext(), myVillage.getListeBanque());
        gridObjetBanque.setAdapter(obAdapter);
        builder.setView(gridObjetBanque);

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("Détruire", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                destructionBuilding(position, myVillage);

            }
        });
        ameliorerBuilding(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void ameliorerBuilding(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder, ArrayList<Ressource> ressources) {
        boolean bool = true;
        int cpt = 0;
        ArrayList<Ressource> villageRessources = myVillage.getAllRessource();
        while (cpt < 4 && bool) {
            if (villageRessources.get(cpt).getQte() < ressources.get(cpt).getQte()) {
                bool = false;
            }
            cpt++;
        }
        builder.setPositiveButton("Améliorer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ameliorerBuilding(myVillage, position, timeConstruct, timeImage);
            }
        });
        AlertDialog d = builder.show();

        if (bool) {
            d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        } else {
            d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    private void destructionBuilding(int position, Village myVillage) {
        Date d = new Date();
        Building b = new Building(false, 0, TypeBuilding.Vide, position, d, 0);
        myVillage.removeBuilding(myVillage.getListBuilding().get(position));
        myVillage.addBuilding(b);
        invalidateOptionsMenu();
        mapAdapteur.notifyDataSetChanged();
    }

    private void ameliorerBuilding(Village myVillage, int position, TextView timeConstruct, ImageView timeImage) {
        Date d = new Date();
        Building currentBuilding = myVillage.getListBuilding().get(position);
        currentBuilding.levelUp();
        Building construction = new Building(false, 0, TypeBuilding.Construction, position, d, 0);
        currentBuilding.setdConstruct(new Date());
        myVillage.construction(construction, currentBuilding);
        mapAdapteur.notifyDataSetChanged();
        currentBuilding.setiTpsConstruct((int) Math.pow(currentBuilding.getTbBuilding().getDuration(), 1 + ((double) (currentBuilding.getiLevel() - 1) / 10)));
        threadConstruction(currentBuilding.getTbBuilding(), currentBuilding, myVillage, timeConstruct, timeImage);
    }

    private void threadConstruction(final TypeBuilding tb, final Building newB, final Village myVillage, final TextView timeConstruct, final ImageView timeImage) {
        final Thread thCountDown = new Thread(new Runnable() {
            @Override
            public void run() {
                final int[] delay = {newB.getiTpsConstruct()};
                Timer timerCountDown = new Timer();
                TimerTask countDowntask = new TimerTask() {
                    @Override
                    public void run() {
                        delay[0]-=1000;

                        newB.setiTpsConstruct(delay[0]);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeConstruct.setText(String.valueOf(formatSeconde(delay[0])));
                                timeImage.setImageDrawable(getDrawable(R.drawable.parchemin));
                                mapAdapteur.notifyDataSetChanged();
                            }
                        });
                        if (delay[0] <=0 ){
                            newB.setbEnable(true);
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    timeConstruct.setText("");
                                    timeImage.setImageDrawable(getDrawable(R.drawable.vide));
                                    mapAdapteur.notifyDataSetChanged();
                                }
                            });
                            this.cancel();
                        }
                    }
                };
                timerCountDown.schedule(countDowntask, 0, 1000L);
            }
        });
        thCountDown.start();
        Thread thConstruction = new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        newB.setbEnable(true);
                        //newB.getTbBuilding().setsNameFile(TypeBuilding.values()[newB.getTbBuilding().getiId_typebuilding()].getsNameFile());
                        myVillage.addBuilding(newB);
                        invalidateOptionsMenu();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mapAdapteur.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Construction de "+newB.getsName()+" terminée.", Toast.LENGTH_SHORT).show();
                                myVillage.sauvegarde();
                            }
                        });
                    }
                };
                timer.schedule(task, newB.getiTpsConstruct());
            }
        });
        thConstruction.start();
    }

    public void recolteThread(){//final Village myVillage) {
        final Thread thRecolte = new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    public void run()
                    {
                        //myVillage.recolte();
                        myVillage=Task.getMyVillage();
                        invalidateOptionsMenu();
                        //myVillage.sauvegardeRessource();
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
                        Random randomGenerator = new Random();
                        int rdmApparition = randomGenerator.nextInt(99);
                        if (rdmApparition>=95) {
                            if (eventValidate) {
                                eventValidate = false;
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

                                            }
                                        });
                                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                myVillage.evement(e.getrConsequence());
                                                invalidateOptionsMenu();
                                                eventValidate = true;
                                            }
                                        });
                                        builder.show();
                                    }
                                });
                            }
                        }
                    }
                };
                timer.schedule( task, time ,time);
            }
        });
        thEvent.start();
    }



    /**
     * Fonction pour récupérer la clé SHA1 pour FB Connect
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exchangeMenu:
                FonctionMissoum();
            case R.id.renameVillage:
                renameVillage();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Renomme le village
     */
    private void renameVillage(){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Renommer votre village");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText titleBox = new EditText(this);
        titleBox.setHint(myVillage.getsName());
        titleBox.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(titleBox);

        builder.setView(layout);

        // Lors du clic sur "Créer"
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newNameVillage = titleBox.getText().toString();
                if(Pattern.matches("[^0-9]+", newNameVillage) && titleBox.getText().toString().trim().length() <= 25) {
                    myVillage.setsName(newNameVillage);
                    myVillage.sauvegarde();
                }else{
                    Toast.makeText(getBaseContext(), "Nom incorrect !", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Lors du clic sur "Annuler"
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    /**
     *
     */
    private void FonctionMissoum(){

        // CreationBanqueDonneeMissoum();

        Intent secondeActivite = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(secondeActivite);


       // CreationBanqueDonneeMissoum();
      /*
        Intent secondeActivite = new Intent(MainActivity.this, ExchangeActivity.class);
        // On rajoute un extra
        secondeActivite.putExtra(VillageIntent,myVillage);
        startActivity(secondeActivite);
*/
    }

    /**
     * Affiche le time en format h-m-s
     * @param
     */
    public String formatSeconde(int miliSeconde){
        String trueTime = "";
        int s = miliSeconde/1000;
        int m=s/60;
        int rs=s%60;
        int h=m/60;
        m=m%60;
        //Log.d("date","Seconde :"+ s+" Heure : "+h+ " Minute : " +m+ " Reste seconde :"+rs);
        if(h==0){
            trueTime = m+"m "+rs+"s";
            if(m==0){
                trueTime = s+"s";
            }
        }

        return trueTime;
    }




}