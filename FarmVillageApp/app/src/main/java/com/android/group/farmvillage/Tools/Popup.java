package com.android.group.farmvillage.Tools;

import android.support.v7.app.AlertDialog;

import com.android.group.farmvillage.Modele.Building;
import com.android.group.farmvillage.Modele.Village;

/**
 * Created by geoffrey on 27/11/17.
 */

public class Popup {

    protected AlertDialog.Builder builder;
    protected Village myVillage;

    public Popup(Village myVillage, Building building) {
        this.myVillage = myVillage;
        switch (building.getTbBuilding()){
            case HDV:
                break;
            case Ferme:
                break;
            case Entrepot:
                break;
            case Academie:
                break;
            case Scierie:
                break;
            case Carriere:
                break;
            case Laboratoire:
                break;
            case Garnison:
                break;
            case Construction:
                break;
            case Banque:
                break;
            case Taverne:
                break;
            case Marche:
                break;
        }
    }
}
