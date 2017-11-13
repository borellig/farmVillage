package com.android.group.farmvillage.Modele;

/**
 * Created by geoffrey on 13/11/17.
 */

public enum TypeBuilding {
    //Objets directement construits
    HDV ("Hotel de Ville", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "hdv", 10000),
    Ferme ("Ferme", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "hdv", 10000),
    Champs ("Champs", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "hdv", 10000),
    Entrepot ("Entrepot", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "hdv", 10000),
    Port ("Port", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "hdv", 10000),
    Garnison ("Garnison", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "hdv", 10000);

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



    TypeBuilding(String sName, int iTaille, int iTpsConstruct, int iHomeCapacity, int iProductionCapacity, String sProdutionType, int iPriceWood, int iPriceFood, int iPriceRock, int iPriceGold, int iDefensePoint, String sNameFile, int duration) {
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
}
