package com.android.group.farmvillage.Modele;

/**
 * Created by hm on 14/11/2017.
 */

public class AskExchange  {
    private int iIDUserRemote;
    private String sUserNameRemote;
    private Ressource rRessource;

    public AskExchange(int iIDUserRemote, String sUserNameRemote, Ressource rRessource) {
        this.iIDUserRemote = iIDUserRemote;
        this.sUserNameRemote = sUserNameRemote;
        this.rRessource = rRessource;
    }

    public int getiIDUserRemote() {
        return iIDUserRemote;
    }

    public AskExchange(String sUserNameRemote, Ressource rRessource) {
        this.sUserNameRemote = sUserNameRemote;
        this.rRessource = rRessource;
    }

    public String getsUserNameRemote() {
        return sUserNameRemote;
    }

    public void setsUserNameRemote(String sUserNameRemote) {
        this.sUserNameRemote = sUserNameRemote;
    }

    public Ressource getrRessource() {
        return rRessource;
    }

    public void setrRessource(Ressource rRessource) {
        this.rRessource = rRessource;
    }

    @Override
    public String toString() {
        return "AskExchange{" +
                "iIDUserRemote=" + iIDUserRemote +
                ", sUserNameRemote='" + sUserNameRemote + '\'' +
                ", rRessource=" + rRessource +
                '}';
    }
}
