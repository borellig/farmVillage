package com.android.group.farmvillage.Modele;

/**
 * Created by geoffrey on 13/11/17.
 */

public class Ressource {

    protected String type;
    protected int qte;

    public Ressource(String type, int qte) {
        this.type = type;
        this.qte = qte;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    @Override
    public String toString() {
        return "Ressource{" +
                "type='" + type + '\'' +
                ", qte=" + qte +
                '}';
    }
}
