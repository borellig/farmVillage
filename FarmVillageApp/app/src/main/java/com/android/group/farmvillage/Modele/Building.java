package com.android.group.farmvillage.Modele;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by geoffrey on 13/11/17.
 */

public class Building {

    protected int iId;
    protected boolean bEnable;
    protected TypeBuilding tbNuilding;
    protected ArrayList<Coordonnees> coord;
    protected String sName;
    protected Date dConstruct;
    protected int iMilitaryCount;






    /**
     * Change l'état d'un batiment, le rend innactif si il était actif et actif si il était inactif.
     */
    protected void changeEtat(){
        this.bEnable=!bEnable;
    }

}
