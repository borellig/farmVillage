package com.android.group.farmvillage.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.group.farmvillage.Adapteur.exchange_adap;
import com.android.group.farmvillage.Modele.AskExchange;
import com.android.group.farmvillage.Modele.Ressource;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;

import java.util.ArrayList;
import java.util.List;

public class ExchangeActivity extends AppCompatActivity {

    private ListView mListView;
    Button BoisButton;
    Button OrButton;
    Button PierreButton;
    Button FoodButton;
    public final static String VillageIntent = "village";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        Intent i = getIntent();
        Village Myvillage = (Village) i.getSerializableExtra(VillageIntent);

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
                        sendRessource(request);
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

            }
        });

        PierreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AskExchange>  listAsk2 =  TryForAttribut("Pierre",listAsk);
                exchange_adap adapter = new exchange_adap(ExchangeActivity.this, listAsk2);
                mListView.setAdapter(adapter);
            }
        });
        FoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AskExchange>  listAsk2 =  TryForAttribut("Food",listAsk);
                exchange_adap adapter = new exchange_adap(ExchangeActivity.this, listAsk2);
                mListView.setAdapter(adapter);
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
        Ressource ressource5 = new Ressource("Bois",4);
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

    private void sendRessource(AskExchange request){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Redemander votre mots de passe");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        //final DBHandler db1 = db;

        int qtiterequest = request.getrRessource().getQte();

        final EditText titleBox = new EditText(this);
        titleBox.setText(String.valueOf(request.getrRessource().getQte()));
        titleBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(titleBox);
        builder.setView(layout);
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

            }
        });

        builder.show();


    }


}
