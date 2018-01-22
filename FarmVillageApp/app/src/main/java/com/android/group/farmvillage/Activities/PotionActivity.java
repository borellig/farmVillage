package com.android.group.farmvillage.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.android.group.farmvillage.Modele.PotionAskExchange;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;
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
    final List<PotionAskExchange> demande= new ArrayList<PotionAskExchange>();
    private MediaType MEDIA_TYPE = MediaType.parse("application/json");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potion);
        Intent i = getIntent();
        myVillage = (Village) i.getSerializableExtra(VillageIntent);
        recolteThread();
       // Potionexchange_adapt adapter = new Potionexchange_adapt(PotionActivity.this, listAskPotion);
     //   lvPotion.setAdapter(adapter);




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

    /**
     *
     * @param url1
     * @param j
     * @return
     * @throws IOException
     */
    List<PotionAskExchange> RunAskRessource(String url1, final int j) throws IOException {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url1+j)
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
                            demande.clear();
                            JSONArray values = json.getJSONArray("request");
                            int length = json.length();
                            Log.d("Ca arrive la ? ", "oui"+length);
                            for (int i = 0; i <= length; i++) {
                                JSONObject building = values.getJSONObject(i);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Ca marche ? ","non");

                        }


                    }

                });

            }
        });
        return demande;
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

}
