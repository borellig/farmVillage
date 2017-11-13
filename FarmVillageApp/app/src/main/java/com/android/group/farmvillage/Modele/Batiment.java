package com.android.group.farmvillage.Modele;

import android.widget.ListAdapter;

/**
 * Created by geoffrey on 13/11/17.
 */

public class Batiment {

    protected int iId;
    protected boolean bActif;
    protected TypeBatiment tbBatiment;
    protected int iCoordX;
    protected int iCoordY;
    protected String sSens;
    protected int iTaille;


    public Batiment(int id, TypeBatiment tbBatiment, boolean bEtat, int x, int y){
        this.iId=id;
        this.bActif=true;
        this.tbBatiment=TypeBatiment.valueOf(tbBatiment.name());
        this.iCoordX=x;
        this.iCoordY=y;

    }
    /**
     * Change l'état d'un batiment, le rend innactif si il était actif et actif si il était inactif.
     */
    protected void changeEtat(){
        this.bActif=!bActif;
    }

}
