package com.android.group.farmvillage.Modele;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by geoffrey on 13/11/17.
 */

public class Building {

    protected int iId;
    protected boolean bEnable;
    protected int iLevel;
    protected TypeBuilding tbBuilding;
    //protected ArrayList<Coordonnees> coord;
    protected int indexList;
    protected String sName;
    protected Date dConstruct;
    protected int iMilitaryCount;


    public Building(boolean bEnable, int iLevel, TypeBuilding tbBuilding, int indexList, Date dConstruct, int iMilitaryCount) {
        this.bEnable = bEnable;
        this.iLevel=iLevel;
        this.tbBuilding = tbBuilding;
        this.indexList = indexList;
        this.sName = tbBuilding.sName;
        this.dConstruct = dConstruct;
        this.iMilitaryCount = iMilitaryCount;
    }



    /**
     * Change l'état d'un batiment, le rend innactif si il était actif et actif si il était inactif.
     */
    public void changeEtat(){
        this.bEnable=!bEnable;
    }

    /**
     * Augmente le niveau de 1;
     */
    public void levelUp() {
        this.iLevel++;
    }

    /**
     * Calcul le type et la quantitée de ressources fournie par unité de temps
     * @return obejt de type Ressource {type, quantite}
     */
    public Ressource getProdution(){
        Ressource ressource;
        ressource = new Ressource(this.tbBuilding.sProdutionType, (int) Math.pow(this.tbBuilding.iProductionCapacity, 1+this.iLevel/10));
        return ressource;
    }

    public ArrayList<Ressource> getLvlUpPrice(){
        ArrayList<Ressource> price = new ArrayList<Ressource>();
        Ressource r1 = new Ressource("Bois", (int) Math.pow(this.tbBuilding.iPriceWood, 1+(double)this.iLevel/10));
        Ressource r2 = new Ressource("Nourriture", (int) Math.pow(this.tbBuilding.iPriceFood, 1+(double)this.iLevel/10));
        Ressource r3 = new Ressource("Pierre", (int) Math.pow(this.tbBuilding.iPriceRock, 1+(double)this.iLevel/10));
        Ressource r4 = new Ressource("Or", (int) Math.pow(this.tbBuilding.iPriceGold, 1+(double)this.iLevel/10));
        price.add(r1);
        price.add(r2);
        price.add(r3);
        price.add(r4);
        return price;
    }

    public int getiLevel() {
        return iLevel;
    }

    public void setiLevel(int iLevel) {
        this.iLevel = iLevel;
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

    public TypeBuilding getTbBuilding() {
        return tbBuilding;
    }

    public void setTbBuilding(TypeBuilding tbNuilding) {
        this.tbBuilding = tbNuilding;
    }

    public int getIndexList() {
        return indexList;
    }

    public void setIndexList(int indexList) {
        this.indexList = indexList;
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

    @Override
    public String toString() {
        return "Building{" +
                "iId=" + iId +
                ", bEnable=" + bEnable +
                ", iLevel=" + iLevel +
                ", tbBuilding=" + tbBuilding +
                ", indexList=" + indexList +
                ", sName='" + sName + '\'' +
                ", dConstruct=" + dConstruct +
                ", iMilitaryCount=" + iMilitaryCount +
                '}';
    }
}
