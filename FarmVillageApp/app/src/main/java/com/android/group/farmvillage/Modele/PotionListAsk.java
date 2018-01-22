package com.android.group.farmvillage.Modele;

/**
 * Created by hm on 22/01/2018.
 */

public class PotionListAsk {
    private int iIDPotion;
    private int iPuissancePotion;
    private String sName;
    private String sDescription;
    private int Qtite ;
    private int  iTypeRessource;


    public PotionListAsk(int iIDPotion, int iPuissancePotion, String sName, String sDescription, int qtite, int iTypeRessource) {
        this.iIDPotion = iIDPotion;
        this.iPuissancePotion = iPuissancePotion;
        this.sName = sName;
        this.sDescription = sDescription;
        Qtite = qtite;
        this.iTypeRessource = iTypeRessource;
    }


    public int getiIDPotion() {
        return iIDPotion;
    }

    public void setiIDPotion(int iIDPotion) {
        this.iIDPotion = iIDPotion;
    }

    public int getiPuissancePotion() {
        return iPuissancePotion;
    }

    public void setiPuissancePotion(int iPuissancePotion) {
        this.iPuissancePotion = iPuissancePotion;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public int getQtite() {
        return Qtite;
    }

    public void setQtite(int qtite) {
        Qtite = qtite;
    }

    public int getiTypeRessource() {
        return iTypeRessource;
    }

    public void setiTypeRessource(int iTypeRessource) {
        this.iTypeRessource = iTypeRessource;
    }

    public String getNamePuissane (){
        String Namepuissance= null;
        switch (this.getiPuissancePotion()){
            case 1 :
                Namepuissance="Petite";
                break;
            case 2 :
                Namepuissance="Moyenne";

                break;
            case 3 :
                Namepuissance="Grande";

                break;
            case 4 :
                Namepuissance="Colossale";

                break;


        }
        return Namepuissance;
    }
}
