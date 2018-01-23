package com.android.group.farmvillage.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.group.farmvillage.Adapteur.Potionexchange_adapt;
import com.android.group.farmvillage.Modele.InputFilterMinMax;
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
import okhttp3.RequestBody;
import okhttp3.Response;

public class PotionActivity extends AppCompatActivity {

    ListView lvPotion;
    Village myVillage;
    private String VillageIntent = "village";
    private List<PotionListAsk> ListAllPotion = new ArrayList<PotionListAsk>();
    private List<PotionListAsk> ListGoldPotion = new ArrayList<PotionListAsk>();
    private List<PotionListAsk> ListRockPotion = new ArrayList<PotionListAsk>();
    private List<PotionListAsk> ListFoodPotion = new ArrayList<PotionListAsk>();
    private List<PotionListAsk> ListWoodPotion = new ArrayList<PotionListAsk>();
    String errormsg;
    String errorcode;
    String urlGet= "http://artshared.fr/andev1/distribue/android/get_potion.php?uid=";
    String urlPost = "http://artshared.fr/andev1/distribue/android/set_potion.php?uid=";
    int iBonuslv1 = 10;
    int iBonuslv2 = 40;
    int iBonuslv3 = 100;
    int iBonuslv4 = 300;

    Potionexchange_adapt adapter;
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
                JSONObject jItems = new JSONObject(String.valueOf(bgTask.execute(urlGet+myVillage.getsUUID()).get()));
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
                    adapter = new Potionexchange_adapt(PotionActivity.this, ListGoldPotion);
                    lvPotion.setAdapter(adapter);
                    lvPotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.e("OnclickList", "oui");
                            PotionListAsk request = (PotionListAsk) adapterView.getItemAtPosition(i);
                            TakePotion(ListGoldPotion,i,request,myVillage);
                        }
                    });

                }
            });



            WoodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter = new Potionexchange_adapt(PotionActivity.this, ListWoodPotion);
                    lvPotion.setAdapter(adapter);
                    lvPotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            PotionListAsk request = (PotionListAsk) adapterView.getItemAtPosition(i);
                            TakePotion(ListWoodPotion,i,request,myVillage);
                        }
                    });
                }
            });

            RockButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter = new Potionexchange_adapt(PotionActivity.this, ListRockPotion);
                    lvPotion.setAdapter(adapter);
                    lvPotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            PotionListAsk request = (PotionListAsk) adapterView.getItemAtPosition(i);
                            TakePotion(ListRockPotion,i,request,myVillage);
                        }
                    });
                }
            });

            FoodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter = new Potionexchange_adapt(PotionActivity.this, ListFoodPotion);
                    lvPotion.setAdapter(adapter);
                    lvPotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            PotionListAsk request = (PotionListAsk) adapterView.getItemAtPosition(i);
                            TakePotion(ListFoodPotion,i,request,myVillage);
                        }
                    });

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


    private void TakePotion(final List<PotionListAsk> lRequeteListe, final int iPosition, final PotionListAsk request, Village village){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Récupération de Potion");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final EditText titleBox = new EditText(this);

        titleBox.setText(String.valueOf(request.getQtite()));
        titleBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(titleBox);
        builder.setView(layout);
        titleBox.setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(request.getQtite()))});
        //Controle de saisie

        // Configurations des bouttons
        builder.setNegativeButton("Tout récupérer ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int Value = ValuePotion(request,request.getQtite());
                makePostRequest(request, request.getQtite());
                UpdateRessourceVillage(myVillage,request.getiTypeRessource(),Value);
                updateList(lRequeteListe,iPosition,0);
            }
        });

        builder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Somme ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    int value = ValuePotion(request,Integer.parseInt(titleBox.getText().toString()));
                    makePostRequest(request, Integer.parseInt(titleBox.getText().toString()));
                    UpdateRessourceVillage(myVillage,request.getiTypeRessource(), value);
                    int iSommeRestante = request.getQtite()-Integer.parseInt(titleBox.getText().toString());
                    updateList(lRequeteListe, iPosition,iSommeRestante);
                    adapter.notifyDataSetChanged();

            }
        });

        builder.show();
    }

    private void updateList(List<PotionListAsk> lRequeteListe, int iPosition, int iSomme){
        if (iSomme==0){
            lRequeteListe.remove(iPosition);
        }
        else {
            lRequeteListe.get(iPosition).setQtite(iSomme);
        }
        adapter.notifyDataSetChanged();

    }

    private int ValuePotion(PotionListAsk request, int Qtite){
        int Value=0;

        switch(request.getiPuissancePotion()){

            case 1 :
                Value = Qtite * iBonuslv1;
                break;
            case 2 :
                Value = Qtite * iBonuslv2;
                break;
            case 3 :
                Value = Qtite * iBonuslv3;
                break;
            case 4 :
                Value = Qtite * iBonuslv4;
                break;

        }
        return Value;
    }

    private void makePostRequest(PotionListAsk demande, int qtite) {
        final OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("id", demande.getiIDPotion());
            postdata.put("qte", qtite);
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());

        final Request request = new Request.Builder()
                .url(urlPost+myVillage.getsUUID())
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Your Token")
                .addHeader("cache-control", "no-cache")
                .build();

        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                String mMessage = e.getMessage().toString();
                                                Log.w("failure Response", mMessage);
                                                //call.cancel();
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response)
                                                    throws IOException {

                                                String mMessage = response.body().string();
                                                if (response.isSuccessful()){
                                                    try {
                                                        JSONObject json = new JSONObject(mMessage);
                                                        final String serverResponse = json.getString("Your Index");

                                                    } catch (Exception e){
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
        );
    }

    void UpdateRessourceVillage (Village village, int i, int SumSent){
        switch(i){
            case 1:
                village.setiWood(myVillage.getiWood()+SumSent);
                break;
            case 2:
                myVillage.setiFood(myVillage.getiFood()+SumSent);

                break;
            case 3:
                myVillage.setiGold(myVillage.getiGold()+SumSent);

                break;
            case 4:
                myVillage.setiRock(myVillage.getiRock()+SumSent);
                break;
        }

    }


}
