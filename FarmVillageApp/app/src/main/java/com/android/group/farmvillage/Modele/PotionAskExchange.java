package com.android.group.farmvillage.Modele;

/**
 * Created by hm on 23/11/2017.
 */

public class PotionAskExchange {
    private int iIDUserRemotePotion;
    private String sUserNameRemotePotion;
    private Ressource rRessource;

    public PotionAskExchange(int iIDUserRemotePotion, String sUserNameRemotePotion, Ressource rRessource) {
        this.iIDUserRemotePotion = iIDUserRemotePotion;
        this.sUserNameRemotePotion = sUserNameRemotePotion;
        this.rRessource = rRessource;
    }

    public PotionAskExchange(String sUserNameRemotePotion, Ressource rRessource) {
        this.sUserNameRemotePotion = sUserNameRemotePotion;
        this.rRessource = rRessource;
    }

    public PotionAskExchange() {
    }

    public int getiIDUserRemotePotion() {
        return iIDUserRemotePotion;
    }

    public void setiIDUserRemotePotion(int iIDUserRemotePotion) {
        this.iIDUserRemotePotion = iIDUserRemotePotion;
    }

    public String getsUserNameRemotePotion() {
        return sUserNameRemotePotion;
    }

    public void setsUserNameRemotePotion(String sUserNameRemotePotion) {
        this.sUserNameRemotePotion = sUserNameRemotePotion;
    }

    public Ressource getrRessource() {
        return rRessource;
    }

    public void setrRessource(Ressource rRessource) {
        this.rRessource = rRessource;
    }

}
