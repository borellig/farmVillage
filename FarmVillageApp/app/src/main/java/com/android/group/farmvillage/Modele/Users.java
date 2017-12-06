package com.android.group.farmvillage.Modele;

import java.util.Date;

/**
 * Created by hm on 14/11/2017.
 */

public class Users {

    protected int iID;
    protected String sUUID;
    protected String sUsername;
    protected String sEmail;
    protected String sPassword;
    protected String iIdFaction;
    protected Date dDate;

    public Users(){

    }

    public Users(int iID, String sUUID, String sUsername, String sEmail, String iIdFaction) {
        this.iID = iID;
        this.sUUID = sUUID;
        this.sUsername = sUsername;
        this.sEmail = sEmail;
        this.iIdFaction = iIdFaction;
    }

    public Users(int iID, String sUUID, String sUsername, String sEmail, String sPassword, String iIdFaction, Date dDate) {
        this.iID = iID;
        this.sUUID = sUUID;
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

    public String getsUUID() {
        return sUUID;
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

    public String getiIdFaction() {
        return iIdFaction;
    }

    public Date getdDate() {
        return dDate;
    }

    public void setiID(int iID) {
        this.iID = iID;
    }

    public void setsUUID(String sUUID) {
        this.sUUID = sUUID;
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

    public void setiIdFaction(String iIdFaction) {
        this.iIdFaction = iIdFaction;
    }

    @Override
    public String toString() {
        return "Users{" +
                "iID='" + iID + '\'' +
                ", sUUID='" + sUUID + '\'' +
                ", sUsername='" + sUsername + '\'' +
                ", sEmail='" + sEmail + '\'' +
                ", iIdFaction='" + iIdFaction + '\'' +
                '}';
    }

    public void setdDate(Date dDate) {
        this.dDate = dDate;
    }
}
