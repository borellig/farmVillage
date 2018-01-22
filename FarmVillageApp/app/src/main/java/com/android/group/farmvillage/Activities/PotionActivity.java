package com.android.group.farmvillage.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.group.farmvillage.Adapteur.Potionexchange_adapt;
import com.android.group.farmvillage.Modele.PotionListAsk;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;
import com.android.group.farmvillage.Tools.BackgroundTask;
import com.android.group.farmvillage.Tools.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PotionActivity extends AppCompatActivity {

    private ListView lvPotion;
    Village myVillage;
    private String VillageIntent = "village";
    private List<PotionListAsk> ListAllPotion = new ArrayList<PotionListAsk>();
    private List<PotionListAsk> ListGoldPotion = new ArrayList<PotionListAsk>();
    private List<PotionListAsk> ListRockPotion = new ArrayList<PotionListAsk>();
    private List<PotionListAsk> ListFoodPotion = new ArrayList<PotionListAsk>();
    private List<PotionListAsk> ListWoodPotion = new ArrayList<PotionListAsk>();
    String errormsg;
    String errorcode;
    String urlGet= "http://artshared.fr/andev1/distribue/android/get_potion.php?uid=307c7442-5da2-4c4e-8199-2d12fe21533d";
    private MediaType MEDIA_TYPE = MediaType.parse("application/json");
    Button GoldButton,WoodButton,RockButton,FoodButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potion);
        Intent i = getIntent();
        myVillage = (Village) i.getSerializableExtra(VillageIntent);
        recolteThread();
        //  Association des elements avec le layout
        GoldButton = (Button) findViewById(R.id.button_potiongold);
        WoodButton = (Button) findViewById(R.id.button_potionwood);
        RockButton = (Button) findViewById(R.id.button_potionrock);
        FoodButton = (Button) findViewById(R.id.button_potionfood);
        lvPotion = (ListView) findViewById(R.id.lv_potions);

        //try {
            //RunAskRessource();
            try {
                BackgroundTask bgTask = new BackgroundTask();
                JSONObject jItems = new JSONObject(String.valueOf(bgTask.execute(urlGet).get()));
                JSONArray jListPotions = new JSONArray(jItems.getString("potions"));
                if (jListPotions != null) {
                    for (int ii = 0; ii < jListPotions.length(); ii++) {
                        JSONObject jPotion = new JSONObject(jListPotions.get(ii).toString());
                        int idpotion = jPotion.getInt("id");
                        int iPuissance = jPotion.getInt("puissance");
                        String sName = jPotion.getString("nom");
                        String sDescription = jPotion.getString("description");
                        int iQtite = jPotion.getInt("qte");
                        String sType = jPotion.getString("type");
                        int iType = ConvertTypetoID(sType);
                        ListAllPotion.add(new PotionListAsk(idpotion,iPuissance,sName,sDescription,iQtite,iType));
                        Log.d("OK pour le ","potion "+i);
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        //}
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Toast.makeText(getApplicationContext(), "Il y a eu une erreur (sleep), veuillez réessayer",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/



        int length = ListAllPotion.size();
        Log.d("List all contenu ", "donc :"+length);

        if(length==0){
            Toast.makeText(getApplicationContext(), "Il y a eu une erreur taille liste, veuillez réessayer",Toast.LENGTH_LONG).show();
        }
        else {
            for (int j = 0; j <= length-1; j++) {
                Log.e("typeRessource", String.valueOf(ListAllPotion.get(j).getiTypeRessource()));
                if(ListAllPotion.get(j).getiTypeRessource()==1  )
                {
                    ListWoodPotion.add(ListAllPotion.get(j));
                }
                if(ListAllPotion.get(j).getiTypeRessource()==2 ) {
                    ListFoodPotion.add(ListAllPotion.get(j));

                }
                if(ListAllPotion.get(j).getiTypeRessource()==3  ){
                    ListGoldPotion.add(ListAllPotion.get(j));

                }
                if(ListAllPotion.get(j).getiTypeRessource()==4  )   {
                    ListRockPotion.add(ListAllPotion.get(j));

                }
                else {
                    Log.d("List error"," oui");
                }

            }
            GoldButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Potionexchange_adapt adapter = new Potionexchange_adapt(PotionActivity.this, ListGoldPotion);
                    lvPotion.setAdapter(adapter);
                }
            });

            WoodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Potionexchange_adapt adapter = new Potionexchange_adapt(PotionActivity.this, ListWoodPotion);
                    lvPotion.setAdapter(adapter);
                }
            });

            RockButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Potionexchange_adapt adapter = new Potionexchange_adapt(PotionActivity.this, ListRockPotion);
                    lvPotion.setAdapter(adapter);
                }
            });

            FoodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Potionexchange_adapt adapter = new Potionexchange_adapt(PotionActivity.this, ListFoodPotion);
                    lvPotion.setAdapter(adapter);

                }
            });


        }




    }


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

    void RunAskRessource() throws IOException {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(urlGet)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                PotionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(" execute ?","oui");

                        try {
                            JSONObject json = new JSONObject(myResponse);
                            ListAllPotion.clear();
                            Log.d(" avant etude json"," oui");
                            JSONArray values = json.getJSONArray("potions");
                            Log.d(" contenu json ", String.valueOf(values));
                            int length = values.length();
                            Log.d("Ca arrive la ? ", "oui"+length);
                            for (int i = 0; i < length; i++) {
                                JSONObject potion = values.getJSONObject(i);

                                int idpotion = potion.getInt("id");
                                int iPuissance = potion.getInt("puissance");
                                String sName = potion.getString("nom");
                                String sDescription = potion.getString("description");
                                int iQtite = potion.getInt("qte");
                                String sType = potion.getString("type");
                                int iType = ConvertTypetoID(sType);
                                ListAllPotion.add(new PotionListAsk(idpotion,iPuissance,sName,sDescription,iQtite,iType));
                                Log.d("OK pour le ","potion "+i);

                            }
                            Log.e("List potion all",ListAllPotion.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Ca marche ? ","non");

                        }



                    }

                });

            }
        });
    }
    public void recolteThread(){
        final Thread thRecolte = new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    public void run()
                    {
                        myVillage= Task.getMyVillage();
                        invalidateOptionsMenu();
                    }
                };
                timer.schedule( task, 0L ,1000L);
            }
        });
        thRecolte.start(); //lance le thread
    }

    public int ConvertTypetoID(String sType){
        int iType=0;

        switch (sType) {
            case "bois":

                iType = 1;
                break;
            case "nourriture":
                iType = 2;
                break;
            case "or":
                iType = 3;

                break;
            case "pierre":
                iType = 4;

                break;
        }
        return iType;

    }

}
