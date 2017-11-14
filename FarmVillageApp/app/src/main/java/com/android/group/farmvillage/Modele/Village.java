package com.android.group.farmvillage.Modele;

import java.util.ArrayList;

/**
 * Created by geoffrey on 13/11/17.
 */

public class Village {
    protected int iId;
    protected String sName;
    protected int iWood;
    protected int iFood;
    protected int iRock;
    protected int iGold;
    protected int iDefensePoint;
    protected ArrayList<Building> listBuilding;


    public Village(int iId, String sName, int iWood, int iFood, int iRock, int iGold, int defensePoint, ArrayList<Building> listBuilding) {
        this.iId = iId;
        this.sName = sName;
        this.iWood = iWood;
        this.iFood = iFood;
        this.iRock = iRock;
        this.iGold = iGold;
        this.listBuilding = listBuilding;
        for (Building b : listBuilding){
            if (b.getTbBuilding().name()!="Vide") {
                this.iDefensePoint += b.getiMilitaryCount() + b.tbBuilding.iDefensePoint;
            }
        }
    }

    public void recolte() {
        for (Building b : listBuilding){
            if (b.getTbBuilding().name()!="Vide") {
                switch(b.getTbBuilding().getsProdutionType()){
                    case "food" :
                        this.iFood+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10);
                        break;
                    case "wood" :
                        this.iWood+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10);
                        break;
                    case "rock" :
                        this.iRock+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10);
                        break;
                    case "gold" :
                        this.iGold+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10);
                        break;
                }
            }
        }
    }

    /**
     * Permet d'ajouter un batiment à la liste des batilents du village
     * @param building
     */
    public void addBuilding(Building building){
        this.listBuilding.set(building.indexList, building);
        setiFood(this.iFood-building.tbBuilding.iPriceFood);
        setiWood(this.iWood-building.tbBuilding.iPriceWood);
        setiRock(this.iRock-building.tbBuilding.iPriceRock);
        setiGold(this.iGold-building.tbBuilding.iPriceGold);
        //// TODO: 13/11/17 insert webService
    }

    /**
     * Retire du village le building selectionné
     * @param building
     */
    public void removeBuilding(Building building) {
        this.iFood += (int) Math.pow(building.tbBuilding.iPriceFood, 1+(double)building.iLevel/10);
        this.iWood += (int) Math.pow(building.tbBuilding.iPriceWood, 1+(double)building.iLevel/10);
        this.iRock += (int) Math.pow(building.tbBuilding.iPriceRock, 1+(double)building.iLevel/10);
        this.iGold += (int) Math.pow(building.tbBuilding.iPriceGold, 1+(double)building.iLevel/10);
        this.listBuilding.set(building.indexList, null);
        // // TODO: 13/11/17 delete webservice
    }

    /**
     * Verifie la disponibilité d'espace pour les troupes
     * @return int nombre de troupes pouvant encore etre accueillies
     */
    public int getHomeCapacityAvailable(){
        int totalCapacity = 0;
        int usedCapacity = 0;

        for (Building b : this.listBuilding){
            usedCapacity+=b.getiMilitaryCount();
            totalCapacity+=b.tbBuilding.iHomeCapacity;
        }
        return (totalCapacity-usedCapacity);
    }

    public ArrayList<Ressource> getAllRessource(){
        ArrayList<Ressource> ressources = new ArrayList<Ressource>();
        Ressource r1 = new Ressource("Bois", this.iWood);
        Ressource r2 = new Ressource("Nourriture", this.iFood);
        Ressource r3 = new Ressource("Pierre", this.iRock);
        Ressource r4 = new Ressource("Or", this.iGold);
        ressources.add(r1);
        ressources.add(r2);
        ressources.add(r3);
        ressources.add(r4);
        return ressources;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getiWood() {
        return iWood;
    }

    public void setiWood(int iWood) {
        this.iWood = iWood;
    }

    public int getiFood() {
        return iFood;
    }

    public void setiFood(int iFood) {
        this.iFood = iFood;
    }

    public int getiRock() {
        return iRock;
    }

    public void setiRock(int iRock) {
        this.iRock = iRock;
    }

    public int getiGold() {
        return iGold;
    }

    public void setiGold(int iGold) {
        this.iGold = iGold;
    }

    public int getDefensePoint() {
        return iDefensePoint;
    }

    public void setDefensePoint(int defensePoint) {
        this.iDefensePoint = defensePoint;
    }

    public ArrayList<Building> getListBuilding() {
        return listBuilding;
    }

    public void setListBuilding(ArrayList<Building> listBuilding) {
        this.listBuilding = listBuilding;
    }


}
