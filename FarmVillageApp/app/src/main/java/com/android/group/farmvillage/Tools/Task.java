package com.android.group.farmvillage.Tools;


import android.util.Log;

import com.android.group.farmvillage.Modele.Village;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by geoffrey on 29/11/17.
 */

public abstract class Task {

    static Village myVillage;

    public static void actionRecolte(){
        Thread thRecolte=new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {

                    public void run()
                    {
                        myVillage.recolte();
                        myVillage.sauvegardeRessource();
                    }
                };
                timer.schedule( task, 0L ,1000L);
            }
        });
        thRecolte.run();
    }

    public static Village getMyVillage() {
        return myVillage;
    }

    public static void setMyVillage(Village myVillage) {
        Task.myVillage = myVillage;
    }
}
