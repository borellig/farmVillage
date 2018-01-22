package com.android.group.farmvillage.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.group.farmvillage.Adapteur.exchange_adap;
import com.android.group.farmvillage.Modele.AskExchange;
import com.android.group.farmvillage.Modele.InputFilterMinMax;
import com.android.group.farmvillage.Modele.PotionAskExchange;
import com.android.group.farmvillage.Modele.Ressource;
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
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.sql.DriverManager.println;

public class ExchangeActivity extends AppCompatActivity {

    /** Gestion requete http - Json **/
    public String url= "http://artshared.fr/andev1/distribue/android/get_typeBuildings.php";
    public String urlPostRessource="http://artshared.fr/andev1/distribue/android/set_request.php";
    TextView txtString;

    exchange_adap adapter;

    private ListView mListView;
    Button BoisButton;
    Button OrButton;
    Button PierreButton;
    Button FoodButton;
    ImageButton PotionButton;
    TextView texteBottomFirst;
    TextView texteBottomscd;
    int iQtiteRestante;
    public final static String VillageIntent = "village";
    final List<AskExchange> demande= new ArrayList<AskExchange>();
    String strUrl = "http://artshared.fr/andev1/distribue/android/get_request.php?id_ressource=";
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    static List<AskExchange> listAskTry;
    Village myVillage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        Intent i = getIntent();
        myVillage = (Village) i.getSerializableExtra(VillageIntent);
        //Mise en relation Layout Object
        mListView = (ListView) findViewById(R.id.ExchangeListeView);
        BoisButton = (Button)findViewById(R.id.buttonBois);
        OrButton = (Button)findViewById(R.id.buttonOR);
        PierreButton = (Button)findViewById(R.id.buttonPierre);
        FoodButton = (Button)findViewById(R.id.buttonFood);
        PotionButton = (ImageButton)findViewById(R.id.imageButtonPotion);
        texteBottomFirst= (TextView)findViewById(R.id.textView);
        texteBottomscd= (TextView)findViewById(R.id.textView2);



        final List<PotionAskExchange> listAskPotion = genererRequest();
        BoisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texteBottomFirst.setText("Demandeur");
                texteBottomscd.setText("Quantité");

                /*Test json */
                try {
                    listAskTry = RunAskRessource(strUrl,1);

                    adapter = new exchange_adap(ExchangeActivity.this, listAskTry);
                    mListView.setAdapter(adapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            AskExchange request = (AskExchange) adapterView.getItemAtPosition(i);
                          sendRessource(listAskTry, i, request,myVillage,1);
                        }
                    });
                    //Log.d("str", String.valueOf(listAskTry.get(1)));

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        OrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texteBottomFirst.setText("Demandeur");
                texteBottomscd.setText("Quantité");

                try {
                    listAskTry = RunAskRessource(strUrl,3);
                    adapter = new exchange_adap(ExchangeActivity.this, listAskTry);
                    mListView.setAdapter(adapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            AskExchange request = (AskExchange) adapterView.getItemAtPosition(i);
                            sendRessource(listAskTry,i,request,myVillage,3);
                        }
                    });
                    //Log.d("str", String.valueOf(listAskTry.get(1)));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        PierreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texteBottomFirst.setText("Demandeur");
                texteBottomscd.setText("Quantité");

                try {
                    listAskTry = RunAskRessource(strUrl,4);
                    adapter = new exchange_adap(ExchangeActivity.this, listAskTry);
                    mListView.setAdapter(adapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            AskExchange request = (AskExchange) adapterView.getItemAtPosition(i);
                            sendRessource(listAskTry,i,request,myVillage,4);
                            Log.d("valeur", String.valueOf(listAskTry.get(i).getrRessource()));

                        }
                    });

                    //Log.d("str", String.valueOf(listAskTry.get(1)));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        FoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texteBottomFirst.setText("Demandeur");
                texteBottomscd.setText("Quantité");

                try {
                    listAskTry = RunAskRessource(strUrl,2);
                    adapter = new exchange_adap(ExchangeActivity.this, listAskTry);
                    mListView.setAdapter(adapter);
                    //Log.d("str", String.valueOf(listAskTry.get(1)));
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            AskExchange request = (AskExchange) adapterView.getItemAtPosition(i);
                            sendRessource(listAskTry,i,request,myVillage,2);

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });

        PotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* texteBottomscd.setText("Quantité");

                texteBottomFirst.setText("Offreur");
                Potionexchange_adapt adapter = new Potionexchange_adapt(ExchangeActivity.this, listAskPotion);
                mListView.setAdapter(adapter);*/
            }
        });

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
     * @return
     */
    private List<PotionAskExchange> genererRequest(){
        List<PotionAskExchange> request = new ArrayList<PotionAskExchange>();
        Ressource ressource1 = new Ressource("Bois",4);
        request.add(new PotionAskExchange("Zizou",ressource1));
        Ressource ressource2 = new Ressource("Bois",4);
        request.add(new PotionAskExchange("Zizou",ressource2));
        Ressource ressource3 = new Ressource("Or",5000);
        request.add(new PotionAskExchange("Henry",ressource3));
        Ressource ressource4 = new Ressource("Pierre",4);
        request.add(new PotionAskExchange("Henry",ressource4));
        Ressource ressource5 = new Ressource("Bois",3444);
        request.add(new PotionAskExchange("Zizou",ressource5));
        Ressource ressource6 = new Ressource("Bois",4);
        request.add(new PotionAskExchange("Zizou",ressource6));
        Ressource ressource7 = new Ressource("Or",4);
        request.add(new PotionAskExchange("Henry",ressource7));
        Ressource ressource8 = new Ressource("Food",4);
        request.add(new PotionAskExchange("Henry",ressource8));

        return request;
    }

    /**
     *
     * @param Attribut
     * @param listRequest
     * @return
     */
    private  List<AskExchange> TryForAttribut(String Attribut, List<AskExchange> listRequest){
        List<AskExchange> request = new ArrayList<AskExchange>();
        for(int i=0; i<listRequest.size(); i++)
            if(listRequest.get(i).getrRessource().getType()==Attribut){
                request.add(listRequest.get(i));
            }
            else {

            }

        return  request;
    }



    /**
     *
     * @param request
     * @param village
     * @param i
     */
    private void sendRessource(final List<AskExchange> lRequeteListe, final int iPosition, final AskExchange request, Village village, final int i){
        final int[] iSommeRestante = new int[1];
        final int[] iSommeSend = new int[1];
        final int max;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        
        builder.setTitle("Gestion d'envoi de ressource : "+ConvertIDRessource(i));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        max = ComparaisonValeurRessource(request, myVillage,i);

        final EditText titleBox = new EditText(this);
        switch(i){
            case 1:
                if(max==village.getiWood()){
                    titleBox.setTextColor(Color.RED);
                }
                break;
            case 2:
                if(max==village.getiFood()){
                    titleBox.setTextColor(Color.RED);
                }
                break;
            case 3:
                if(max==village.getiGold()){
                    titleBox.setTextColor(Color.RED);
                }
                break;
            case 4:
                if(max==village.getiRock()){
                    titleBox.setTextColor(Color.RED);
                }
                break;
        }
        titleBox.setText(String.valueOf(max));
        titleBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(titleBox);
        builder.setView(layout);
        titleBox.setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(max))});
        //Controle de saisie

        // Configurations des bouttons
        builder.setNegativeButton("Total :"+max+" "+ConvertIDRessource(i), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            makePostRequest(request, 0);
                UpdateRessourceVillage(myVillage,i,max);
                updateList(lRequeteListe,iPosition,0);
            }
        });

        builder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Somme", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Integer.parseInt(titleBox.getText().toString())>max){
                    titleBox.setText(String.valueOf(max));
                    Toast.makeText(getApplicationContext(), "La quantité indiquée est trop élevée : "+max+" et pas plus", Toast.LENGTH_LONG).show();
                }
                else {
                    iSommeRestante[0] = RemainingAmount(request, Integer.parseInt(titleBox.getText().toString()));
                    makePostRequest(request, iSommeRestante[0]);
                    UpdateRessourceVillage(myVillage,i,Integer.parseInt(titleBox.getText().toString()));
                    adapter.notifyDataSetChanged();
                    iSommeSend[0] = Integer.parseInt(titleBox.getText().toString());
                    updateList(lRequeteListe, iPosition,iSommeRestante[0]);

                }
            }
        });

        builder.show();
    }

    private void updateList(List<AskExchange> lRequeteListe, int iPosition, int iSomme){
        if (iSomme==0){
            lRequeteListe.remove(iPosition);
        }
        else {
            Log.d("rIpos", String.valueOf(iPosition));
            Ressource rNewressource = new Ressource(lRequeteListe.get(iPosition).getrRessource().getType(),iSomme);
            lRequeteListe.get(iPosition).setrRessource(rNewressource);
            Log.d("Ressource", String.valueOf(lRequeteListe.get(iPosition).getrRessource().getQte()));
        }
        adapter.notifyDataSetChanged();

    }

    /**
     *
     * @param request information de la demande
     * @param village objet associé au village de l'utilisateur
     * @param i indicateur pour savoir la demande (or,bois,pierre ou nourriture)
     * @return
     */
    private int ComparaisonValeurRessource(AskExchange request, Village village, int i){
        int max=0;
        switch (i) {
            case 1:
                if(request.getrRessource().getQte()>=village.getiWood()){
                    max = village.getiWood();
                }
                else {
                    max = request.getrRessource().getQte();
                }

                break;
            case 2:
                if(request.getrRessource().getQte()>=village.getiFood()){
                    max = village.getiFood();
                }
                else {
                    max = request.getrRessource().getQte();
                }
                break;
            case 3:
                if(request.getrRessource().getQte()>=village.getiGold()){
                    max = village.getiGold();
                }
                else {
                    max = request.getrRessource().getQte();
                }
                break;
            case 4:
                if(request.getrRessource().getQte()>=village.getiRock()){
                    max = village.getiRock();
                }
                else {
                    max = request.getrRessource().getQte();
                }
                break;
        }
        return max;

    }

    /**
     *
     * @param i
     * @return
     */
    private String GetNameRessourceWithID(int i){
        String NameRessource = null;
        switch (i) {
            case 1:

                NameRessource = "Bois";
                break;
            case 2:
                NameRessource = "food";
                break;
            case 3:
                NameRessource = "gold";

                break;
            case 4:
                NameRessource = "rock";

                break;
        }

        return NameRessource;
    }

    /**
     *
     * @param i
     * @return
     */
    private String ConvertIDRessource(int i){
        String Ressource = new String();
        switch (i){
            case 1:
                Ressource = "Bois";
                break;
            case 2:
                Ressource = "Nourriture";

                break;
            case 3:
                Ressource = "OR";

                break;
            case 4:
                Ressource = "Pierre";

                break;

        }
        return  Ressource;
    }

    /**
     *
     * @throws IOException
     */
    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                ExchangeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(" execute ?","oui");

                        try {
                            JSONObject json = new JSONObject(myResponse);


                            JSONArray values = json.getJSONArray("type_building");
                            int length = json.length();
                            for (int i = 0; i <= length; i++) {

                                JSONObject building = values.getJSONObject(i);

                                int id = building.getInt("sId");
                                String name = building.getString("sName");

                                println(id + ", " + name + ", " );
                                Log.d("Ca marche ? ",name);
                                Log.d("Ca marche ? ",""+length);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Ca marche ? ","non");

                        }


                    }

                });

            }
        });
    }


    /**
     *
     * @param url1
     * @param j
     * @return
     * @throws IOException
     */
    List<AskExchange> RunAskRessource(String url1, final int j) throws IOException {
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

                ExchangeActivity.this.runOnUiThread(new Runnable() {
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
                                int idUser = building.getInt("iId");
                                String NameUserRemote = building.getString("sUUID");
                                int qtite = building.getInt("iAmount");
                                demande.add(new AskExchange(idUser,NameUserRemote,new Ressource(GetNameRessourceWithID(j),qtite)));

                                println(idUser + ", " + qtite + ", " );
                                Log.d("Ca marche ? ",""+qtite);
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

    /**
     * Fonction pour envoyer les ressources demandée
     * @param demande
     * @param montantrestant
     */
    private void makePostRequest(AskExchange demande, int montantrestant) {
        final OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("id", demande.getiIDUserRemote());
            postdata.put("amount", montantrestant);
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());

        final Request request = new Request.Builder()
                .url(urlPostRessource)
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

    /**
     *  Fonction pour calculer la somme restante de la demande, elle sera envoyer a la db
     * @param request
     * @param SommePaye
     * @return
     */
    int RemainingAmount(AskExchange request, int SommePaye){
        int iSommeRestante = 0;
        iSommeRestante = request.getrRessource().getQte() - SommePaye;
        return iSommeRestante;
    }

    /**
     * Fonction pour mettre a jour les ressources du village lors de l'envoi de ressource
     * @param village
     * @param i
     * @param SumSent
     */
    void UpdateRessourceVillage (Village village, int i, int SumSent){
        switch(i){
            case 1:
                village.setiWood(myVillage.getiWood()-SumSent);
                break;
            case 2:
                myVillage.setiFood(myVillage.getiFood()-SumSent);

                break;
            case 3:
                myVillage.setiGold(myVillage.getiGold()-SumSent);

                break;
            case 4:
                myVillage.setiRock(myVillage.getiRock()-SumSent);
                break;
        }

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
