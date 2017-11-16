package com.android.group.farmvillage.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.group.farmvillage.Adapteur.exchange_adap;
import com.android.group.farmvillage.Modele.AskExchange;
import com.android.group.farmvillage.Modele.InputFilterMinMax;
import com.android.group.farmvillage.Modele.Ressource;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExchangeActivity extends AppCompatActivity {

    private ListView mListView;
    Button BoisButton;
    Button OrButton;
    Button PierreButton;
    Button FoodButton;
    public final static String VillageIntent = "village";
    public Village Myvillage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        Intent i = getIntent();
        Myvillage = (Village) i.getSerializableExtra(VillageIntent);
        //Mise en relation Layout Object

        mListView = (ListView) findViewById(R.id.ExchangeListeView);
        BoisButton = (Button)findViewById(R.id.buttonBois);
        OrButton = (Button)findViewById(R.id.buttonOR);
        PierreButton = (Button)findViewById(R.id.buttonPierre);
        FoodButton = (Button)findViewById(R.id.buttonFood);
        final List<AskExchange> listAsk = genererRequest();
        BoisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AskExchange>  listAsk2 =  TryForAttribut("Bois",listAsk);
                exchange_adap adapter = new exchange_adap(ExchangeActivity.this, listAsk2);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AskExchange request = (AskExchange) adapterView.getItemAtPosition(i);
                        sendRessource(request,Myvillage,1);
                    }
                });
            }
        });
        OrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AskExchange>  listAsk2 =  TryForAttribut("Or",listAsk);
                exchange_adap adapter = new exchange_adap(ExchangeActivity.this, listAsk2);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AskExchange request = (AskExchange) adapterView.getItemAtPosition(i);
                        sendRessource(request,Myvillage,3);
                    }
                });
            }
        });

        PierreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AskExchange>  listAsk2 =  TryForAttribut("Pierre",listAsk);
                exchange_adap adapter = new exchange_adap(ExchangeActivity.this, listAsk2);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AskExchange request = (AskExchange) adapterView.getItemAtPosition(i);
                        sendRessource(request,Myvillage,4);
                    }
                });
            }
        });
        FoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AskExchange>  listAsk2 =  TryForAttribut("Food",listAsk);
                exchange_adap adapter = new exchange_adap(ExchangeActivity.this, listAsk2);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AskExchange request = (AskExchange) adapterView.getItemAtPosition(i);
                        sendRessource(request,Myvillage,2);
                    }
                });
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
        menu.findItem(R.id.orValue).setTitle(String.valueOf(Myvillage.getiGold()));
        menu.findItem(R.id.pierreValue).setTitle(String.valueOf(Myvillage.getiRock()));
        menu.findItem(R.id.boisValue).setTitle(String.valueOf(Myvillage.getiWood()));
        menu.findItem(R.id.foodValue).setTitle(String.valueOf(Myvillage.getiFood()));
        return true;
    }

    private List<AskExchange> genererRequest(){
        List<AskExchange> request = new ArrayList<AskExchange>();
        Ressource ressource1 = new Ressource("Bois",4);
        request.add(new AskExchange("Zizou",ressource1));
        Ressource ressource2 = new Ressource("Bois",4);
        request.add(new AskExchange("Zizou",ressource2));
        Ressource ressource3 = new Ressource("Or",4);
        request.add(new AskExchange("Henry",ressource3));
        Ressource ressource4 = new Ressource("Pierre",4);
        request.add(new AskExchange("Henry",ressource4));
        Ressource ressource5 = new Ressource("Bois",3444);
        request.add(new AskExchange("Zizou",ressource5));
        Ressource ressource6 = new Ressource("Bois",4);
        request.add(new AskExchange("Zizou",ressource6));
        Ressource ressource7 = new Ressource("Or",4);
        request.add(new AskExchange("Henry",ressource7));
        Ressource ressource8 = new Ressource("Food",4);
        request.add(new AskExchange("Henry",ressource8));

        return request;
    }

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

    private void sendRessource(AskExchange request, Village village, int i){
        final int max;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Gestion d'envoi de ressource");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        max = ComparaisonValeurRessource(request, village,i);

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
        builder.setPositiveButton("Total", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNeutralButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Integer.parseInt(titleBox.getText().toString())>max){
                    titleBox.setText(String.valueOf(max));
                    Toast.makeText(getApplicationContext(), "La quantité indiquée est trop élevée : "+max+" et pas plus", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.show();
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
}