package com.android.group.farmvillage.Modele;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by geoffrey on 13/11/17.
 */

public class Village implements Serializable {
    protected int iId;
    protected String sUUID;
    protected String sName;
    protected int iWood;
    protected int iFood;
    protected int iRock;
    protected int iGold;
    protected int iDefensePoint;
    protected ArrayList<Building> listBuilding;
    protected ArrayList<ObjetBanque> listeBanque;
    protected long lLastmaj;


    public Village(int iId, String sUUID, String sName, int iWood, int iFood, int iRock, int iGold, int defensePoint, ArrayList<Building> listBuilding) {
        this.iId = iId;
        this.sUUID=sUUID;
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
        this.listeBanque=new ArrayList<ObjetBanque>();
    }

    public void recolte() {
        int stockageCapacity=getStockageCapacity();
        for (Building b : this.listBuilding){
            if (b.getTbBuilding() != TypeBuilding.Vide && b.getTbBuilding() != TypeBuilding.Construction && b.getTbBuilding().getsProdutionType()!=null) {
                switch(b.getTbBuilding().getsProdutionType()){
                    case "food" :
                        this.iFood+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10);
                        if(this.iFood>stockageCapacity/4) {
                            this.iFood = stockageCapacity/4;
                        }
                        break;
                    case "wood" :
                        this.iWood+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10);
                        if(this.iWood>stockageCapacity/4) {
                            this.iWood = stockageCapacity/4;
                        }
                        break;
                    case "rock" :
                        this.iRock+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10);
                        if(this.iRock>stockageCapacity/4) {
                            this.iRock = stockageCapacity/4;
                        }
                        break;
                    case "gold" :
                        this.iGold+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10);
                        if(this.iGold>stockageCapacity/4) {
                            this.iGold = stockageCapacity/4;
                        }
                        break;
                }
            }
        }
    }

    public void recolteServeur() {
        for (Building b : this.listBuilding) {
            if (b.getTbBuilding().getsName() != "Vide" && b.getTbBuilding().getsName() != "Construction" && b.getTbBuilding().getsProdutionType() != null) {
                long now = new Date().getTime();
                int duree = 0;
                if(b.getdConstruct().getTime()+ Math.pow(b.getTbBuilding().getDuration(), 1 + ((double) (b.getiLevel() - 2) / 10)) < this.getlLastmaj()){
                    duree = (int) (now - this.getlLastmaj())/1000;
                }
                else {
                    duree = (int)(now - b.getdConstruct().getTime()-Math.pow(b.getTbBuilding().getDuration(), 1 + ((double) (b.getiLevel() - 2) / 10)))/1000;
                }
                int stockageCapacity=getStockageCapacity();
                switch(b.getTbBuilding().getsProdutionType()){
                    case "food" :
                        this.iFood+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10)*duree;
                        if(this.iFood>stockageCapacity/4) {
                            this.iFood = stockageCapacity/4;
                        }
                        break;
                    case "wood" :
                        this.iWood+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10)*duree;
                        if(this.iWood>stockageCapacity/4) {
                            this.iWood = stockageCapacity/4;
                        }
                        break;
                    case "rock" :
                        this.iRock+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10)*duree;
                        if(this.iRock>stockageCapacity/4) {
                            this.iRock = stockageCapacity/4;
                        }
                        break;
                    case "gold" :
                        this.iGold+=Math.pow(b.getTbBuilding().getiProductionCapacity(), 1+(double)b.iLevel/10)*duree;
                        if(this.iGold>stockageCapacity/4) {
                            this.iGold = stockageCapacity/4;
                        }
                        break;
                }
            }
        }
    }

    public void evement(ArrayList<Ressource> rConsequence){
        int stockageCapacity=getStockageCapacity();
        for(Ressource r : rConsequence){
            switch (r.getType()){
                case "food" :
                    this.iFood=this.iFood+r.getQte()*this.iFood/100;
                    if (this.iFood<0){
                        this.iFood=0;
                    }
                    if(this.iFood>stockageCapacity/4) {
                        this.iFood = stockageCapacity/4;
                    }
                    break;
                case "wood" :
                    this.iWood=this.iWood+r.getQte()*this.iWood/100;
                    if (this.iWood<0){
                        this.iWood=0;
                    }
                    if(this.iWood>stockageCapacity/4) {
                        this.iWood = stockageCapacity/4;
                    }
                    break;
                case "rock" :
                    this.iRock=this.iRock+r.getQte()*this.iRock/100;
                    if (this.iRock<0){
                        this.iRock=0;
                    }
                    if(this.iRock>stockageCapacity/4) {
                        this.iRock = stockageCapacity/4;
                    }
                    break;
                case "gold" :
                    this.iGold=this.iGold+r.getQte()*this.iGold/100;
                    if (this.iGold<0){
                        this.iGold=0;
                    }
                    if(this.iGold>stockageCapacity/4) {
                        this.iGold = stockageCapacity/4;
                    }
                    break;
            }
        }
    }

    /**
     * Permet d'ajouter un batiment à la liste des batilents du village
     * @param building
     */
    public void addBuilding(final Building building){
        listBuilding.set(building.indexList, building);
        //// TODO: 13/11/17 insert webService
    }

    public void resumeConstruction (Building bTemp, Building oldB){
        bTemp.setsName("En construction : "+oldB.getsName());
        bTemp.setiLevel(oldB.getiLevel());
        bTemp.tbBuilding.setiId_typebuilding(oldB.getTbBuilding().getiId_typebuilding());
        bTemp.setdConstruct(oldB.getdConstruct());
        listBuilding.set(bTemp.indexList, bTemp);
    }

    public void construction (Building bTemp, Building oldB){
        bTemp.setsName("En construction : "+oldB.getsName());
        bTemp.setiLevel(oldB.getiLevel());
        bTemp.setTbBuilding(oldB.getTbBuilding());
        //bTemp.tbBuilding.setiId_typebuilding(oldB.getTbBuilding().getiId_typebuilding());
        bTemp.setdConstruct(oldB.getdConstruct());
        listBuilding.set(bTemp.indexList, bTemp);
        setiFood(iFood-(int) Math.pow(oldB.tbBuilding.iPriceFood, 1+(double)(oldB.iLevel-1)/10));
        setiWood(iWood-(int) Math.pow(oldB.tbBuilding.iPriceWood, 1+(double)(oldB.iLevel-1)/10));
        setiRock(iRock-(int) Math.pow(oldB.tbBuilding.iPriceRock, 1+(double)(oldB.iLevel-1)/10));
        setiGold(iGold-(int) Math.pow(oldB.tbBuilding.iPriceGold, 1+(double)(oldB.iLevel-1)/10));
        sauvegarde();
    }

    /**
     * Retire du village le building selectionné
     * @param building
     */
    public void removeBuilding(Building building) {
        this.iFood += (int) Math.pow(building.tbBuilding.iPriceFood, 1+(double)(building.iLevel-1)/10);
        this.iWood += (int) Math.pow(building.tbBuilding.iPriceWood, 1+(double)(building.iLevel-1)/10);
        this.iRock += (int) Math.pow(building.tbBuilding.iPriceRock, 1+(double)(building.iLevel-1)/10);
        this.iGold += (int) Math.pow(building.tbBuilding.iPriceGold, 1+(double)(building.iLevel-1)/10);
        this.listBuilding.set(building.indexList, null);
        JSONObject jBuilding = new JSONObject();
        try {
            jBuilding.put("UUID", this.getsUUID());
            jBuilding.put("iIndex", building.getIndexList());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        requetePost(jBuilding, "http://artshared.fr/andev1/distribue/android/delete_building.php");
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

    public int getStockageCapacity(){
        int stockage=0;
        for(Building b : listBuilding){
            stockage+=Math.pow(b.getiStockageCapacity(), 1+(double)b.iLevel/100);
        }
        return stockage;
    }

    public void sauvegarde(){
        JSONObject jVillage=new JSONObject();

        try {
            jVillage.put("iId", this.getiId());
            jVillage.put("sUUID", this.getsUUID());
            jVillage.put("sName", this.getsName());
            jVillage.put("iWood", this.getiWood());
            jVillage.put("iFood", this.getiFood());
            jVillage.put("iRock", this.getiRock());
            jVillage.put("iGold", this.getiGold());
            jVillage.put("iDefensePoint", this.getiId());
            JSONArray building = new JSONArray();
            for(Building b : this.getListBuilding()){
                if(b.getTbBuilding()!=TypeBuilding.Vide) {
                    JSONObject jBuilding = new JSONObject();
                    jBuilding.put("iId", b.getiId());
                    if(b.isbEnable() && b.getiTpsConstruct()<=999){
                        jBuilding.put("bEnable", 1);
                    }
                    else {
                        jBuilding.put("bEnable", 0);
                    }
                    jBuilding.put("iLevel", b.getiLevel());
                    jBuilding.put("iMilitaryCount", b.getiMilitaryCount());
                    jBuilding.put("dConstruct", b.getdConstruct().getTime());
                    jBuilding.put("iId_typebuilding", b.getTbBuilding().getiId_typebuilding());
                    jBuilding.put("iIndex", b.getIndexList());
                    jBuilding.put("iTpsConstruct", b.getiTpsConstruct());
                    building.put(jBuilding);
                }
            }
            jVillage.put("building", building);
            jVillage.put("lastmaj", new Date().getTime());
            Log.e("jVillage", jVillage.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("erreurgeo",e.getMessage());
        }
        ////////////////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////////////////
        requetePost(jVillage, "http://artshared.fr/andev1/distribue/android/set_village.php");
    }

    private void requetePost(JSONObject jObject, final String url) {
        final OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jObject.toString());

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Your Token")
                .addHeader("cache-control", "no-cache")
                .build();
        client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String mMessage = e.getMessage().toString();
                    Log.e("failure Response", url+" "+mMessage);
                    //call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response)
                        throws IOException {

                    String mMessage = response.body().string();
                    if (response.isSuccessful()){

                        //Log.d("success POST", mMessage);

                    }
                }
            }
        );
    }

    public void sauvegardeRessource() {
        JSONObject jRessources=new JSONObject();
        try {
            jRessources.put("sUUID", this.getsUUID());
            jRessources.put("iFood", this.getiFood());
            jRessources.put("iWood", this.getiWood());
            jRessources.put("iRock", this.getiRock());
            jRessources.put("iGold", this.getiGold());
            jRessources.put("dateMAJ", new Date().getTime());
        }
            catch (Exception e){
                e.printStackTrace();
        }
        requetePost(jRessources, "http://artshared.fr/andev1/distribue/android/sauvegarde_ressource.php");
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

    public String getsUUID() {
        return sUUID;
    }

    public void setsUUID(String sUUID) {
        this.sUUID = sUUID;
    }

    public int getiDefensePoint() {
        return iDefensePoint;
    }

    public void setiDefensePoint(int iDefensePoint) {
        this.iDefensePoint = iDefensePoint;
    }

    public ArrayList<ObjetBanque> getListeBanque() {
        return listeBanque;
    }

    public void setListeBanque(ArrayList<ObjetBanque> listeBanque) {
        this.listeBanque = listeBanque;
    }

    public long getlLastmaj() {
        return lLastmaj;
    }

    public void setlLastmaj(long iLastmaj) {
        this.lLastmaj = iLastmaj;
    }

    @Override
    public String toString() {
        return "Village{" +
                "iId=" + iId +
                ", sName='" + sName + '\'' +
                ", iWood=" + iWood +
                ", iFood=" + iFood +
                ", iRock=" + iRock +
                ", iGold=" + iGold +
                ", iDefensePoint=" + iDefensePoint +
                ", listBuilding=" + listBuilding +
                '}';
    }

    public String toJson(){
        String jsonString="{";
        jsonString+="Village : {" +
                "iId:" + iId +
                ", sName:'" + sName + '\'' +
                ", iWood:" + iWood +
                ", iFood:" + iFood +
                ", iRock:" + iRock +
                ", iGold:" + iGold +
                ", iDefensePoint:" + iDefensePoint +
                ", listBuilding:" + listBuilding +
                '}';
        jsonString+="}";
        return jsonString;
    }
}
