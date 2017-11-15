package com.android.group.farmvillage.Modele;

import java.util.ArrayList;

/**
 * Created by geoffrey on 15/11/17.
 */

public class Event {

    protected String sTitre;
    protected String sDefinition;
    protected ArrayList<Ressource> rConsequence;
    protected int iProbability;

    public Event(TypeEvent teEvent) {
        this.sTitre = teEvent.getsTitre();
        this.sDefinition = teEvent.sDefinition;
        this.rConsequence = teEvent.getrConsequence();
        this.iProbability = teEvent.getiProbability();
    }


    public String getsTitre() {
        return sTitre;
    }

    public void setsTitre(String sTitre) {
        this.sTitre = sTitre;
    }

    public String getsDefinition() {
        return sDefinition;
    }

    public void setsDefinition(String sDefinition) {
        this.sDefinition = sDefinition;
    }

    public ArrayList<Ressource> getrConsequence() {
        return rConsequence;
    }

    public void setrConsequence(ArrayList<Ressource> rConsequence) {
        this.rConsequence = rConsequence;
    }

    public int getiProbability() {
        return iProbability;
    }

    public void setiProbability(int iProbability) {
        this.iProbability = iProbability;
    }
}
