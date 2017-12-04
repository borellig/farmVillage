package com.android.group.farmvillage.Modele;

import java.util.ArrayList;

/**
 * Created by geoffrey on 13/11/17.
 */

public enum TypeBuilding {
    //Objets directement construits
    HDV(0, "Hotel de Ville", 1, 10, 50, 10, "gold", 100, 100, 100, 100, 50, "hdv", 10000, 1000),
    Ferme(1, "Ferme", 1, 10, 50, 10, "food", 100, 100, 100, 100, 50, "ferme", 10000, 1000),
    Entrepot(2, "Entrepot", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "entrepot", 10000, 2000),
    Academie(3, "Academie", 1, 5, 0, 0, null, 100, 100, 100, 100, 0, "academie", 10000, 2000),
    Scierie(4, "Scierie", 1, 5, 0, 10, "wood", 100, 100, 100, 100, 0, "scierie", 10000, 2000),
    Carriere(5, "Carrière", 1, 5, 0, 10, "rock", 100, 100, 100, 100, 0, "carriere", 10000, 2000),
    Laboratoire(6, "Laboratoire", 1, 0, 50, 0, null, 100, 100, 100, 100, 50, "hdv", 10000, 2000),
    Garnison(7, "Garnison", 1, 10, 50, 0, null, 100, 100, 100, 100, 50, "garnison", 10000, 2000),
    Construction(8, "", 0, 0, 0, 0, null, 0, 0, 0, 0, 0, "construction", 0, 0),
    Banque(9, "Coffre", 0, 0, 0, 0, null, 0, 0, 0, 0, 0, "banque", 120000, 0),
    Taverne(10, "Taverne", 0, 0, 0, 0, null, 0, 0, 0, 0, 0, "taverne", 120000, 0),
    Marche(11, "Marché", 1, 300, 0, 0, null, 50, 50, 50, 50, 0, "marche", 300000, 0),
    Vide(12, "", 0, 0, 0, 0, null, 0, 0, 0, 0, 0, "vide", 0, 0);

    protected int iId_typebuilding;
    protected String sName;
    protected int iTaille;
    protected int iTpsConstruct;
    protected int iHomeCapacity;
    public int iProductionCapacity;
    public String sProdutionType;
    protected int iPriceWood;
    protected int iPriceFood;
    protected int iPriceRock;
    protected int iPriceGold;
    protected int iDefensePoint;
    protected String sNameFile;
    protected int duration;
    protected int iStockageCapacity;


    TypeBuilding(int iId_typebuilding, String sName, int iTaille, int iTpsConstruct, int iHomeCapacity, int iProductionCapacity, String sProdutionType, int iPriceWood, int iPriceFood, int iPriceRock, int iPriceGold, int iDefensePoint, String sNameFile, int duration, int iStockageCapacity) {
        this.iId_typebuilding = iId_typebuilding;
        this.sName = sName;
        this.iTaille = iTaille;
        this.iHomeCapacity = iHomeCapacity;
        this.iProductionCapacity = iProductionCapacity;
        this.sProdutionType = sProdutionType;
        this.iPriceWood = iPriceWood;
        this.iPriceFood = iPriceFood;
        this.iPriceRock = iPriceRock;
        this.iPriceGold = iPriceGold;
        this.iDefensePoint = iDefensePoint;
        this.sNameFile = sNameFile;
        this.duration = duration;
        this.iStockageCapacity = iStockageCapacity;
    }

    public ArrayList<Ressource> constructionPrice() {
        ArrayList<Ressource> constructionPrice = new ArrayList<>();
        Ressource r1 = new Ressource("Bois", this.iPriceWood);
        Ressource r2 = new Ressource("Nourriture", this.iPriceFood);
        Ressource r3 = new Ressource("Pierre", this.iPriceRock);
        Ressource r4 = new Ressource("Or", this.iPriceGold);
        constructionPrice.add(r1);
        constructionPrice.add(r2);
        constructionPrice.add(r3);
        constructionPrice.add(r4);
        return constructionPrice;
    }

    public static ArrayList<TypeBuilding> getTypeBuildingsDispo(Village myVillage) {
        ArrayList<TypeBuilding> dispo = new ArrayList<>();
        ArrayList<TypeBuilding> actuel = new ArrayList<>();
        for (Building b : myVillage.getListBuilding()) {
            if (b.getTbBuilding() != TypeBuilding.Vide) {
                actuel.add(b.getTbBuilding());
            }
        }
        if (actuel.size() == 0) {
            dispo.add(HDV);
        } else {
            int hdvLvl = chercheNiveauHDV(myVillage.getListBuilding());
            for (TypeBuilding tb : TypeBuilding.values()) {
                if (tb != Vide && tb != Construction && tb != HDV) {
                    if ((tb == Garnison || tb == Banque || tb == Garnison || tb == Marche || tb == Taverne) && hdvLvl>=2){
                        ajouteTypeBuilding(myVillage, dispo, tb);
                    }
                    if (tb != Garnison && tb != Banque && tb != Garnison && tb != Marche && tb != Taverne) {
                        ajouteTypeBuilding(myVillage, dispo, tb);
                    }

                }
            }
        }

        return dispo;
    }

    private static int chercheNiveauHDV(ArrayList<Building> actuel) {
        int niveau = 0;
        for (Building b : actuel) {
            if (b.getTbBuilding() == HDV) {
                niveau = b.getiLevel();
            }
        }
        return niveau;
    }

    private static void ajouteTypeBuilding(Village myVillage, ArrayList<TypeBuilding> dispo, TypeBuilding tb) {
        int indexList = 0;
        boolean bool = true;
        while (indexList < 4 && bool) {
            if (myVillage.getAllRessource().get(indexList).getQte() < tb.constructionPrice().get(indexList).getQte()) {
                bool = false;
            }
            indexList++;
        }
        if (bool) {
            dispo.add(tb);
        }
    }


    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getiTaille() {
        return iTaille;
    }

    public void setiTaille(int iTaille) {
        this.iTaille = iTaille;
    }

    public int getiTpsConstruct() {
        return iTpsConstruct;
    }

    public void setiTpsConstruct(int iTpsConstruct) {
        this.iTpsConstruct = iTpsConstruct;
    }

    public int getiHomeCapacity() {
        return iHomeCapacity;
    }

    public void setiHomeCapacity(int iHomeCapacity) {
        this.iHomeCapacity = iHomeCapacity;
    }

    public int getiProductionCapacity() {
        return iProductionCapacity;
    }

    public void setiProductionCapacity(int iProductionCapacity) {
        this.iProductionCapacity = iProductionCapacity;
    }

    public String getsProdutionType() {
        return sProdutionType;
    }

    public void setsProdutionType(String sProdutionType) {
        this.sProdutionType = sProdutionType;
    }

    public int getiPriceWood() {
        return iPriceWood;
    }

    public void setiPriceWood(int iPriceWood) {
        this.iPriceWood = iPriceWood;
    }

    public int getiPriceFood() {
        return iPriceFood;
    }

    public void setiPriceFood(int iPriceFood) {
        this.iPriceFood = iPriceFood;
    }

    public int getiPriceRock() {
        return iPriceRock;
    }

    public void setiPriceRock(int iPriceRock) {
        this.iPriceRock = iPriceRock;
    }

    public int getiPriceGold() {
        return iPriceGold;
    }

    public void setiPriceGold(int iPriceGold) {
        this.iPriceGold = iPriceGold;
    }

    public int getiDefensePoint() {
        return iDefensePoint;
    }

    public void setiDefensePoint(int iDefensePoint) {
        this.iDefensePoint = iDefensePoint;
    }

    public String getsNameFile() {
        return sNameFile;
    }

    public void setsNameFile(String sNameFile) {
        this.sNameFile = sNameFile;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getiStockageCapacity() {
        return iStockageCapacity;
    }

    public void setiStockageCapacity(int iStockageCapacity) {
        this.iStockageCapacity = iStockageCapacity;
    }

    public int getiId_typebuilding() {
        return iId_typebuilding;
    }

    public void setiId_typebuilding(int iId_typebuilding) {
        this.iId_typebuilding = iId_typebuilding;
    }
}
