package com.android.group.farmvillage.Modele;

import java.util.ArrayList;

/**
 * Created by geoffrey on 13/11/17.
 */

public enum TypeBuilding {
    //Objets directement construits
    HDV ("Hotel de Ville", 1, 10, 50, 10, "gold", 100, 100, 100, 100, 50, "hdv", 10000, 0),
    Ferme ("Ferme", 1, 10, 50, 10, "food", 100, 100, 100, 100, 50, "hdv", 10000, 2000),
    Entrepot ("Entrepot", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "entrepot", 10000, 2000),
    Academie ("Academie", 1, 5, 0, 0, null, 100, 100, 100, 100, 0, "academie", 10000, 2000),
    Scierie ("Scierie", 1, 5, 0, 10, "wood", 100, 100, 100, 100, 0, "scierie", 10000, 2000),
    Mur ("Mur d'enceinte", 1, 5, 0, 0, null, 100, 100, 100, 100, 0, "mur", 10000, 2000),
    Carriere ("Carrière", 1, 5, 0, 10, "rock", 100, 100, 100, 100, 0, "carriere", 10000, 2000),
    Port ("Port", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "hdv", 10000, 2000),
    Garnison ("Garnison", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "hdv", 10000, 2000),
    Vide ("", 0, 0, 0, 0, null, 0, 0, 0, 0, 0, "vide", 0, 0);

    protected String sName;
    protected int iTaille;
    protected int iTpsConstruct;
    protected int iHomeCapacity;
    protected int iProductionCapacity;
    protected String sProdutionType;
    protected int iPriceWood;
    protected int iPriceFood;
    protected int iPriceRock;
    protected int iPriceGold;
    protected int iDefensePoint;
    protected String sNameFile;
    protected int duration;
    protected int iStockageCapacity;


    TypeBuilding(String sName, int iTaille, int iTpsConstruct, int iHomeCapacity, int iProductionCapacity, String sProdutionType, int iPriceWood, int iPriceFood, int iPriceRock, int iPriceGold, int iDefensePoint, String sNameFile, int duration, int iStockageCapacity) {
        this.sName = sName;
        this.iTaille = iTaille;
        this.iTpsConstruct = iTpsConstruct;
        this.iHomeCapacity = iHomeCapacity;
        this.iProductionCapacity = iProductionCapacity;
        this.sProdutionType = sProdutionType;
        this.iPriceWood = iPriceWood;
        this.iPriceFood = iPriceFood;
        this.iPriceRock = iPriceRock;
        this.iPriceGold = iPriceGold;
        this.iDefensePoint = iDefensePoint;
        this.sNameFile = sNameFile;
        this.duration = duration;
        this.iStockageCapacity=iStockageCapacity;
    }

    public ArrayList<Ressource> constructionPrice () {
        ArrayList<Ressource> constructionPrice = new ArrayList<>();
        Ressource r1 = new Ressource("Bois", this.iPriceWood);
        Ressource r2 = new Ressource("Nourriture", this.iPriceFood);
        Ressource r3 = new Ressource("Pierre", this.iPriceRock);
        Ressource r4 = new Ressource("Or", this.iPriceGold);
        constructionPrice.add(r1);
        constructionPrice.add(r2);
        constructionPrice.add(r3);
        constructionPrice.add(r4);
        return constructionPrice;
    }



    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getiTaille() {
        return iTaille;
    }

    public void setiTaille(int iTaille) {
        this.iTaille = iTaille;
    }

    public int getiTpsConstruct() {
        return iTpsConstruct;
    }

    public void setiTpsConstruct(int iTpsConstruct) {
        this.iTpsConstruct = iTpsConstruct;
    }

    public int getiHomeCapacity() {
        return iHomeCapacity;
    }

    public void setiHomeCapacity(int iHomeCapacity) {
        this.iHomeCapacity = iHomeCapacity;
    }

    public int getiProductionCapacity() {
        return iProductionCapacity;
    }

    public void setiProductionCapacity(int iProductionCapacity) {
        this.iProductionCapacity = iProductionCapacity;
    }

    public String getsProdutionType() {
        return sProdutionType;
    }

    public void setsProdutionType(String sProdutionType) {
        this.sProdutionType = sProdutionType;
    }

    public int getiPriceWood() {
        return iPriceWood;
    }

    public void setiPriceWood(int iPriceWood) {
        this.iPriceWood = iPriceWood;
    }

    public int getiPriceFood() {
        return iPriceFood;
    }

    public void setiPriceFood(int iPriceFood) {
        this.iPriceFood = iPriceFood;
    }

    public int getiPriceRock() {
        return iPriceRock;
    }

    public void setiPriceRock(int iPriceRock) {
        this.iPriceRock = iPriceRock;
    }

    public int getiPriceGold() {
        return iPriceGold;
    }

    public void setiPriceGold(int iPriceGold) {
        this.iPriceGold = iPriceGold;
    }

    public int getiDefensePoint() {
        return iDefensePoint;
    }

    public void setiDefensePoint(int iDefensePoint) {
        this.iDefensePoint = iDefensePoint;
    }

    public String getsNameFile() {
        return sNameFile;
    }

    public void setsNameFile(String sNameFile) {
        this.sNameFile = sNameFile;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getiStockageCapacity() {
        return iStockageCapacity;
    }

    public void setiStockageCapacity(int iStockageCapacity) {
        this.iStockageCapacity = iStockageCapacity;
    }
}
