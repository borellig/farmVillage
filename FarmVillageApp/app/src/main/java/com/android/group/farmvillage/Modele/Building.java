package com.android.group.farmvillage.Modele;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by geoffrey on 13/11/17.
 */

public class Building {

    protected int iId;
    protected boolean bEnable;
    protected TypeBuilding tbBuilding;
    protected ArrayList<Coordonnees> coord;
    protected String sName;
    protected Date dConstruct;
    protected int iMilitaryCount;

    public Building(boolean bEnable, TypeBuilding tbBuilding, ArrayList<Coordonnees> coord, String sName, Date dConstruct, int iMilitaryCount) {
        this.bEnable = bEnable;
        this.tbBuilding = tbBuilding;
        this.coord = coord;
        this.sName = sName;
        this.dConstruct = dConstruct;
        this.iMilitaryCount = iMilitaryCount;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public boolean isbEnable() {
        return bEnable;
    }

    public void setbEnable(boolean bEnable) {
        this.bEnable = bEnable;
    }

    public TypeBuilding getTbNuilding() {
        return tbBuilding;
    }

    public void setTbNuilding(TypeBuilding tbNuilding) {
        this.tbBuilding = tbNuilding;
    }

    public ArrayList<Coordonnees> getCoord() {
        return coord;
    }

    public void setCoord(ArrayList<Coordonnees> coord) {
        this.coord = coord;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Date getdConstruct() {
        return dConstruct;
    }

    public void setdConstruct(Date dConstruct) {
        this.dConstruct = dConstruct;
    }

    public int getiMilitaryCount() {
        return iMilitaryCount;
    }

    public void setiMilitaryCount(int iMilitaryCount) {
        this.iMilitaryCount = iMilitaryCount;
    }

    /**
     * Change l'état d'un batiment, le rend innactif si il était actif et actif si il était inactif.
     */
    protected void changeEtat(){
        this.bEnable=!bEnable;
    }

}
