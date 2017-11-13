package com.android.group.farmvillage.Modele;

/**
 * Created by geoffrey on 13/11/17.
 */

public enum TypeBatiment {
    //Objets directement construits
    HDV ("Hotel de Ville", 1),
    Ferme ("Ferme", 1),
    Champs ("Champs", 1),
    Entrepot ("Entrepot", 1),
    Port ("Port", 1);

    private String name = "";
    private int taille = 0;

    //Constructeur
    TypeBatiment(String name, int taille){
        this.name = name;
        this.taille = taille;
    }





}
