package com.android.group.farmvillage.Modele;

import java.util.Date;

/**
 * Created by hm on 14/11/2017.
 */

public class Users {

    protected int iID;
    protected String sGlobalID;
    protected String sUsername;
    protected String sEmail;
    protected String sPassword;
    protected int iIdFaction;
    protected Date dDate;

    public Users(){

    }

    public Users(int iID, String sGlobalID, String sUsername, String sEmail, String sPassword, int iIdFaction, Date dDate) {
        this.iID = iID;
        this.sGlobalID = sGlobalID;
        this.sUsername = sUsername;
        this.sEmail = sEmail;
        this.sPassword = sPassword;
        this.iIdFaction = iIdFaction;
        this.dDate = dDate;
    }

    public Users(int iID) {
        this.iID = iID;
    }

    public int getiID() {
        return iID;
    }

    public String getsGlobalID() {
        return sGlobalID;
    }

    public String getsUsername() {
        return sUsername;
    }

    public String getsEmail() {
        return sEmail;
    }

    public String getsPassword() {
        return sPassword;
    }

    public int getiIdFaction() {
        return iIdFaction;
    }

    public Date getdDate() {
        return dDate;
    }

    public void setiID(int iID) {
        this.iID = iID;
    }

    public void setsGlobalID(String sGlobalID) {
        this.sGlobalID = sGlobalID;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public void setiIdFaction(int iIdFaction) {
        this.iIdFaction = iIdFaction;
    }

    public void setdDate(Date dDate) {
        this.dDate = dDate;
    }
}
