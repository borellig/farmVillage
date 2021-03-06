package com.android.group.farmvillage.Activities;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.android.group.farmvillage.Modele.Users;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;
import com.android.group.farmvillage.Tools.BackgroundTask;
import com.android.group.farmvillage.Tools.Task;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    //Déclaration de l'adapteur
    //public map_adapt mapAdapteur;
    public MapAdapter mapAdapteur;
    public Village myVillage;
    public Handler mHandler;
    public boolean eventValidate = true;
    public final static String VillageIntent = "village";
    public Users user;

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
        if(indiceSong == 1){
            menu.findItem(R.id.stopSong).setTitle("Couper le son");
        }else{
            menu.findItem(R.id.stopSong).setTitle("Activer le son");
        }

        //Gestion des factions
        if(user.getiIdFaction().contentEquals("shadow")){
            menu.findItem(R.id.flag).setIcon(R.drawable.flagblack);
        }else{
            menu.findItem(R.id.flag).setIcon(R.drawable.flag);
        }
        //menu.findItem(R.id.flag).setIcon();

        return true;
    }

    private MediaPlayer ring;
    private int indiceSong = 1;


    /**
     * Fonction pour récupérer la clé SHA1 pour FB Connect
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.exchangeMenu:
//                FonctionMissoum();
//                break;
            case R.id.renameVillage:
                renameVillage();
                break;
            case R.id.stopSong:
                if(indiceSong == 0){
                    indiceSong = 1;
                    changeSong(ring);
                }else{
                    indiceSong = 0;
                    changeSong(ring);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void changeSong(MediaPlayer ring){
        if(indiceSong == 0){
            ring.pause();
        }else {
            ring.start();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        ring.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        ring.start();
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
        user=(Users)getIntent().getSerializableExtra("user");

        Log.e("userFaction", user.getiIdFaction());


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

        //-------------------Gestion de la musique--------------------
        //------------------------------------------------------------
        ring = MediaPlayer.create(getBaseContext(),R.raw.aoe);
        changeSong(ring);


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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme);
        builder.setTitle(myVillage.getListBuilding().get(position).getsName()+" niv "+myVillage.getListBuilding().get(position).getiLevel());
        TypeBuilding bClicked = myVillage.getListBuilding().get(position).getTbBuilding();
        if(bClicked!=TypeBuilding.Construction) {
            if(bClicked==TypeBuilding.Carriere || bClicked == TypeBuilding.Scierie || bClicked == TypeBuilding.Ferme || bClicked == TypeBuilding.HDV) {
                popUpConstruction(position, myVillage, timeConstruct, timeImage, builder);
            }
            else if(bClicked == TypeBuilding.Academie){
                popUpRecherche(position, myVillage, timeConstruct, timeImage, builder);
            }
            else if(bClicked==TypeBuilding.Entrepot || bClicked == TypeBuilding.Taverne) {
                popUpAutre(position, myVillage, timeConstruct, timeImage, builder);
            }
            else if(bClicked==TypeBuilding.Banque){
                popUpBanque(position, myVillage, timeConstruct, timeImage, builder);
            }
            else if(bClicked==TypeBuilding.Laboratoire){
                popUpLaboratoire(position, myVillage, timeConstruct, timeImage, builder);
            }
            else if(bClicked==TypeBuilding.Marche){
                popUpMarche(position, myVillage, timeConstruct, timeImage, builder);
            }
            else if(bClicked==TypeBuilding.Garnison){
                Log.e("connard", "connard");
                popUpGarnison(position, myVillage, timeConstruct, timeImage, builder);
            }
            else {
                popUpAutre(position, myVillage, timeConstruct, timeImage, builder);
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
        genereBoutonsPopup(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void popUpMarche(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder) {
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
        String besoin = "";
        for (Ressource res : ressources) {
            besoin += res.getType() + " x" + res.getQte() + "\n";
        }
        builder.setMessage("Pour passer de niveau il faut : \n" +besoin);
        Button toMarche = new Button(getApplicationContext());
        toMarche.setText("Visiter le marché");
        toMarche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FonctionMissoum();
            }
        });
        builder.setView(toMarche);
        genereBoutonsPopup(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void popUpRecherche(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder) {
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
        String besoin = "";
        for (Ressource res : ressources) {
            besoin += res.getType() + " x" + res.getQte() + "\n";
        }
        builder.setMessage("Pour passer de niveau il faut : \n" +besoin);
        genereBoutonsPopup(position, myVillage, timeConstruct, timeImage, builder, ressources);
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
        genereBoutonsPopup(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void popUpBanque(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, final AlertDialog.Builder builder) {
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
        String besoin = "";
        for (Ressource res : ressources) {
            besoin += res.getType() + " x" + res.getQte() + "\n";
        }
        BackgroundTask bgTask = new BackgroundTask();
        ArrayList<ObjetBanque> obl = new ArrayList<>();
        Log.d("ici", "ici");
        try {
            JSONObject jItems = new JSONObject(String.valueOf(bgTask.execute("http://artshared.fr/andev1/distribue/android/get_items.php?uid="+myVillage.getsUUID()).get()));
            JSONArray jListObjetBanque = new JSONArray(jItems.getString("items"));
            if (jListObjetBanque != null) {
                for (int i = 0; i < jListObjetBanque.length(); i++) {
                    JSONObject jObjetBanque = new JSONObject(jListObjetBanque.get(i).toString());
                    JSONObject jStat = new JSONObject(jObjetBanque.getString("stats"));
                    String obId = jObjetBanque.getString("id");
                    int obLvl = jObjetBanque.getInt("level");
                    String obType = jObjetBanque.getString("equipement");
                    String obName = jObjetBanque.getString("name");
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



        final GridView gridObjetBanque = new GridView(getBaseContext());
        gridObjetBanque.setNumColumns(3);
        ObjetBanqueAdapter obAdapter = new ObjetBanqueAdapter(getBaseContext(), myVillage.getListeBanque());
        gridObjetBanque.setAdapter(obAdapter);
        //RelativeLayout rl = new RelativeLayout(getApplicationContext());
        final LinearLayout rl = new LinearLayout(getApplicationContext());
        rl.setOrientation(LinearLayout.VERTICAL);
        final Button btoInfo = new Button(rl.getContext());
        btoInfo.setText("Information batiment");
        final Button btoObjets = new Button(rl.getContext());
        btoObjets.setText("Retour aux objets");
        final TextView tv = new TextView(getApplicationContext());
        String capacity = "Votre coffre contient : "+(myVillage.getListBuilding().get(position).getiLevel()+10)+" emplacements";
        String productionLvlUp = "Au niveau suivant vous béneficirez de : "+(myVillage.getListBuilding().get(position).getiLevel()+1+10)+" emplacements";
        String tempsLvlUp = "L'amélioration au niveau superieur prendra : "+formatSeconde((int) Math.pow(myVillage.getListBuilding().get(position).getTbBuilding().getDuration(), 1 + ((double) (myVillage.getListBuilding().get(position).getiLevel()) / 10)));
        tv.setText(capacity+"\nPour passer de niveau il faut : \n" +besoin+"\n"+productionLvlUp+"\n"+tempsLvlUp);
        Log.e("tempsMaggle", formatSeconde((int) Math.pow(myVillage.getListBuilding().get(position).getTbBuilding().getDuration(), 1 + ((double) (myVillage.getListBuilding().get(position).getiLevel()) / 10))));
        rl.addView(btoInfo);
        rl.addView(gridObjetBanque);
        Log.d("builder",builder.toString());
        btoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.removeAllViews();
                rl.addView(btoObjets);
                rl.addView(tv);
            }
        });
        btoObjets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.removeAllViews();
                rl.addView(btoInfo);
                rl.addView(gridObjetBanque);
            }
        });


        //params.addRule(RelativeLayout.ALIGN_TOP, gridObjetBanque.getId());

        //bChange.setLayoutParams(params); //causes layout update



        builder.setView(rl);

        genereBoutonsPopup(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void popUpLaboratoire(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder) {
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
        BackgroundTask bgTask = new BackgroundTask();
        ArrayList<ObjetBanque> obl = new ArrayList<>();


        RelativeLayout rl = new RelativeLayout(getApplicationContext());
        Button toLabo = new Button(getApplicationContext());
        toLabo.setText("Entrer dans le laboratoire");
        toLabo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = new Intent(MainActivity.this, PotionActivity.class);
                // On rajoute un extra
                secondeActivite.putExtra(VillageIntent, myVillage);
                startActivity(secondeActivite);
            }
        });

        rl.addView(toLabo);
        builder.setView(rl);

        genereBoutonsPopup(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void popUpGarnison(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder) {
        final ArrayList<Ressource> ressources = myVillage.getListBuilding().get(position).getLvlUpPrice();
        BackgroundTask bgTask = new BackgroundTask();
        ArrayList<ObjetBanque> obl = new ArrayList<>();


        RelativeLayout rl = new RelativeLayout(getApplicationContext());
        Button demandeTroupe = new Button(getApplicationContext());
        int militaryCount =0;
        for (Building building : myVillage.getListBuilding()){
            militaryCount+=building.getiMilitaryCount();
        }
        final int nbTroupe = myVillage.getHomeCapacityAvailable();
        Log.e("homecapacity", String.valueOf(myVillage.getHomeCapacityAvailable()));
        demandeTroupe.setText("Demander "+nbTroupe+" troupes");
        demandeTroupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jDemande = new JSONObject();
                try {
                    jDemande.put("uuid", myVillage.getsUUID());
                    jDemande.put("nbTroupes", nbTroupe);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requetePost(jDemande, "http://artshared.fr/andev1/distribue/android/set_army.php");
                Toast.makeText(getApplicationContext(), "Vous venez de demander "+nbTroupe+" troupes", Toast.LENGTH_LONG).show();
            }
        });

        rl.addView(demandeTroupe);
        builder.setView(rl);

        genereBoutonsPopup(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

    private void genereBoutonsPopup(final int position, final Village myVillage, TextView timeConstruct, ImageView timeImage, AlertDialog.Builder builder, ArrayList<Ressource> ressources) {
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
        upgradeBuilding(position, myVillage, timeConstruct, timeImage, builder, ressources);
    }

        private void upgradeBuilding(final int position, final Village myVillage, final TextView timeConstruct, final ImageView timeImage, AlertDialog.Builder builder, ArrayList<Ressource> ressources) {
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
                upgradeBuilding(myVillage, position, timeConstruct, timeImage);
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

    private void upgradeBuilding(Village myVillage, int position, TextView timeConstruct, ImageView timeImage) {
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
                                //Toast.makeText(getApplicationContext(), "Construction de "+newB.getsName()+" terminée.", Toast.LENGTH_SHORT).show();
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
                    invalidateOptionsMenu();
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

//        Intent secondeActivite = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(secondeActivite);


       // CreationBanqueDonneeMissoum();

        Intent secondeActivite = new Intent(MainActivity.this, ExchangeActivity.class);
        // On rajoute un extra
        secondeActivite.putExtra(VillageIntent,myVillage);
        startActivity(secondeActivite);

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

    private void requetePost(JSONObject jObject, final String url) {
        final OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jObject.toString());

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Your Token")
                .addHeader("cache-control", "no-cache")
                .build();
        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                String mMessage = e.getMessage().toString();
                                                Log.e("failure Response", url+" "+mMessage);
                                                //call.cancel();
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response)
                                                    throws IOException {

                                                String mMessage = response.body().string();
                                                if (response.isSuccessful()){

                                                    //Log.d("success POST", mMessage);

                                                }
                                            }
                                        }
        );
    }




}