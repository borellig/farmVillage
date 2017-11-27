package com.android.group.farmvillage.Modele;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

/**
 * Created by geoffrey on 25/11/17.
 */

public class Popup  {

    public TypeBuilding getTypeBuilding() {
        return typeBuilding;
    }

    public void setTypeBuilding(TypeBuilding typeBuilding) {
        this.typeBuilding = typeBuilding;
    }

    protected TypeBuilding typeBuilding;

    public Popup(TypeBuilding typeBuilding) {
        this.typeBuilding = typeBuilding;
    }

}
