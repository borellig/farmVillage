package com.android.group.farmvillage.Modele;

/**
 * Created by geoffrey on 13/11/17.
 */

public enum TypeBatiment {
    //Objets directement construits
    HDV ("Hotel de Ville", 1, 10),
    Ferme ("Ferme", 1, 10),
    Champs ("Champs", 1, 10),
    Entrepot ("Entrepot", 1, 10),
    Port ("Port", 1, 10);

    private String sName;
    private int iTaille;
    private int iTpsConstruct;

    //Constructeur
    TypeBatiment(String name, int taille, int tpsConstruct){
        this.sName = name;
        this.iTaille = taille;
        this.iTpsConstruct = tpsConstruct;

    }





}
