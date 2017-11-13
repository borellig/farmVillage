package com.android.group.farmvillage.Modele;

/**
 * Created by geoffrey on 13/11/17.
 */

public enum TypeBuilding {
    //Objets directement construits
    HDV ("Hotel de Ville", 1, 10),
    Ferme ("Ferme", 1, 10),
    Champs ("Champs", 1, 10),
    Entrepot ("Entrepot", 1, 10),
    Port ("Port", 1, 10),
    Garnison ("Garnison", 1, 10);

    protected String sName;
    protected int iTaille;
    protected int iTpsConstruct;
    protected int iHomeCapacity;
    protected int iProductionCapacity;
    protected int iPriceWood;
    protected int iPriceFood;
    protected int iPriceRock;
    protected int iPriceGold;
    protected int iDefensePoint;
    protected String sNameFile;

    //Constructeur
    TypeBuilding(String name, int taille, int tpsConstruct){
        this.sName = name;
        this.iTaille = taille;
        this.iTpsConstruct = tpsConstruct;

    }





}
