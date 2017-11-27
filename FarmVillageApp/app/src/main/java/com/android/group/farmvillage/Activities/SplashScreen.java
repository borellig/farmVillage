package com.android.group.farmvillage.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.Modele.TypeBuilding;
import com.android.group.farmvillage.Modele.Village;
import com.android.group.farmvillage.R;
import com.android.group.farmvillage.Tools.BackgroundTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gui on 24/11/2017.
 */

public class SplashScreen extends Activity{
    private static int SPLASH_TIME_OUT = 5000;
    public final int nbCase=30;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                Village myVillage = initialisation();
                if(myVillage!=null) {
                    i.putExtra("village", myVillage);
                    startActivity(i);
                    finish();
                }
                else {
                    Log.d("dommage...", "grv");
                }
            }

        }, SPLASH_TIME_OUT);

        final ProgressBar mProgressBar;
        final CountDownTimer[] mCountDownTimer = new CountDownTimer[1];

        final int[] cpt = {0};

        mProgressBar=(ProgressBar) findViewById(R.id.progress_bar); //associe à la progressbar du layout

        final Boolean[] bCancel = {false}; //etat permettant ou non de cancel la suppression d'un magasin
        //au recheragement de de l'adapter verifie l'état actuel de suppression
        mProgressBar.setVisibility(View.VISIBLE);//rend la progressbar visible
        mProgressBar.setProgress(cpt[0]);//définit l'avancement de la progressbar
        bCancel[0] = true; //est en suppression
        final int[] progressTemps = {cpt[0]};
        mCountDownTimer[0]=new CountDownTimer(5000,96) { //crée un timer de 3 secondes remis à jour toute les 0.1 secondes
            @Override
            public void onTick(long millisUntilFinished) {//fait avancer la progressbar
                progressTemps[0]++;
                mProgressBar.setProgress(progressTemps[0]*2);
                cpt[0] = cpt[0]+1;
            }

            @Override
            public void onFinish() {//reset la progressBar
               // mProgressBar.setProgress(0);
            }
        };
        mCountDownTimer[0].start();//lance le timer
    }

    private Village initialisation() {
        BackgroundTask bgTask = new BackgroundTask();
        Village myVillage = null;

        try {
            String str = String.valueOf(bgTask.execute("http://artshared.fr/andev1/distribue/android/get_game.php?uid=UNIQUEID1").get());
            Log.d("str", str);
            JSONObject jVillage = new JSONObject(str);
            int iId = jVillage.getInt("iId");
            String sUUID = jVillage.getString("sUUID");
            String sName = jVillage.getString("sName");
            int iWood = jVillage.getInt("iWood");
            int iFood = jVillage.getInt("iFood");
            int iRock = jVillage.getInt("iRock");
            int iGold = jVillage.getInt("iGold");
            int iDefensePoint = jVillage.getInt("iDefensePoint");
            ArrayList<Building> listBuilding = new ArrayList<>();
            JSONArray jListBuilding = new JSONArray(jVillage.getString("building"));
            Date d = new Date();
            for (int i=0; i<this.nbCase; i++){
                listBuilding.add(i, new Building(false, 0, TypeBuilding.Vide, i, d, 0));
            }
            if (jListBuilding != null) {
                for (int i=0;i<jListBuilding.length();i++){
                    JSONObject jBuilding = new JSONObject(jListBuilding.get(i).toString());
                    boolean bEnable ;
                    if(jBuilding.getInt("bEnable")==1){
                        bEnable=true;
                    }
                    else {
                        bEnable=false;
                    }

                    int iLevel = jBuilding.getInt("iLevel");
                    int iMilitaryCount = jBuilding.getInt("iMilitaryCount");
                    Date dConstruct = new Date(jBuilding.getLong("dConstruct"));
                    int typeBuilding = jBuilding.getInt("iId_typebuilding");
                    int index = jBuilding.getInt("iIndex");
                    Log.d("benable", String.valueOf(bEnable)+" "+String.valueOf(index));
                    TypeBuilding tb = TypeBuilding.values()[typeBuilding];
                    Building newB = new Building(bEnable, iLevel, tb, index, dConstruct, iMilitaryCount);
                    if(newB.isbEnable()){//if(newB.getdConstruct().getTime()+dureeConstruction<new Date().getTime()){
                        listBuilding.set(index, newB);
                    }
                    else {
                        Building tmpBuilding = new Building(false, iLevel, TypeBuilding.values()[typeBuilding], index, dConstruct, iMilitaryCount);
                        listBuilding.set(index, tmpBuilding);

                    }
                    listBuilding.get(index).setiId(jBuilding.getInt("iId"));
                }
            }
            myVillage = new Village(iId, sUUID, sName, iWood, iFood, iRock, iGold, iDefensePoint,listBuilding);
            myVillage.setlLastmaj(jVillage.getLong("lastmaj"));
            Log.d("timesJVillage", jVillage.toString());
            Log.d("times", String.valueOf(jVillage.getLong("lastmaj")));
            Log.d("times", String.valueOf(myVillage.getlLastmaj()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myVillage;
    }


}
