package com.android.group.farmvillage.Modele;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by geoffrey on 13/11/17.
 */

public class Building implements Serializable {

    protected int iId;
    protected boolean bEnable;
    protected int iTpsConstruct;
    protected int iLevel;
    protected TypeBuilding tbBuilding;
    protected int indexList;
    protected String sName;
    protected Date dConstruct;
    protected int iMilitaryCount;
    protected int iStockageCapacity;


    public Building(boolean bEnable, int iLevel, TypeBuilding tbBuilding, int indexList, Date dConstruct, int iMilitaryCount) {
        this.bEnable = bEnable;
        this.iLevel=iLevel;
        this.iTpsConstruct = 0;
        this.tbBuilding = tbBuilding;
        this.indexList = indexList;
        this.sName = tbBuilding.sName;
        this.dConstruct = dConstruct;
        this.iMilitaryCount = iMilitaryCount;
        this.iStockageCapacity=tbBuilding.getiStockageCapacity();
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


    public void setiTpsConstruct(int iTpsConstruct) {
        this.iTpsConstruct = iTpsConstruct;
    }

    public int getiTpsConstruct() {
        return iTpsConstruct;
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

    public String getsNameMiniscule() {
        sName = sName.toLowerCase();
        return sName;

    }

    public int getiStockageCapacity() {
        return iStockageCapacity;
    }

    public void setiStockageCapacity(int iStockageCapacity) {
        this.iStockageCapacity = iStockageCapacity;
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
        return "{" +
                "iId:" + iId +
                ", bEnable:" + bEnable +
                ", iLevel:" + iLevel +
                ", tbBuilding:" + tbBuilding +
                ", indexList:" + indexList +
                ", sName:'" + sName + '\'' +
                ", dConstruct:" + dConstruct.getTime() +
                ", iMilitaryCount:" + iMilitaryCount +
                ", iTpsConstruc:" + iTpsConstruct +
                "}";
    }
}
