package com.android.group.farmvillage.Modele;

/**
 * Created by geoffrey on 05/12/17.
 */

public class ObjetBanque {
    protected String iId;
    protected int iLevel;
    protected String sType;
    protected String sName;
    protected int iHeath;
    protected int iAttack;
    protected int iDefense;

    public ObjetBanque(String iId, int iLevel, String sType, String sName, int iHeath, int iAttack, int iDefense) {
        this.iId = iId;
        this.iLevel = iLevel;
        this.sType = sType;
        this.sName = sName;
        this.iHeath = iHeath;
        this.iAttack = iAttack;
        this.iDefense = iDefense;
    }

    public String getiId() {
        return iId;
    }

    public void setiId(String iId) {
        this.iId = iId;
    }

    public int getiLevel() {
        return iLevel;
    }

    public void setiLevel(int iLevel) {
        this.iLevel = iLevel;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getiHeath() {
        return iHeath;
    }

    public void setiHeath(int iHeath) {
        this.iHeath = iHeath;
    }

    public int getiAttack() {
        return iAttack;
    }

    public void setiAttack(int iAttack) {
        this.iAttack = iAttack;
    }

    public int getiDefense() {
        return iDefense;
    }

    public void setiDefense(int iDefense) {
        this.iDefense = iDefense;
    }
}
