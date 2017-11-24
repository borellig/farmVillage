package com.android.group.farmvillage.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.group.farmvillage.R;

/**
 * Created by gui on 24/11/2017.
 */

public class SplashScreen extends Activity{
    private static int SPLASH_TIME_OUT = 5000;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }

        }, SPLASH_TIME_OUT);

        final ProgressBar mProgressBar;
        final CountDownTimer[] mCountDownTimer = new CountDownTimer[1];

        final int[] cpt = {0};

        mProgressBar=(ProgressBar) findViewById(R.id.progress_bar); //associe à la progressbar du layout

        final Boolean[] bCancel = {false}; //etat permettant ou non de cancel la suppression d'un magasin
        //au recheragement de de l'adapter verifie l'état actuel de suppression
        if(cpt[0] <= 5){ //si c'est en cours de suppression
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

        else {//pas de suppression en cours
        }
    }


}
