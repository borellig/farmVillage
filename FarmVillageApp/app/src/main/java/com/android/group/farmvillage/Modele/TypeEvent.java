package com.android.group.farmvillage.Modele;

import java.util.ArrayList;

/**
 * Created by geoffrey on 15/11/17.
 */

public enum TypeEvent {

    Seisme("Séisme", "Un séisme de magnitude 9 sur l'echelle de Richter s'est produit ! \nC'est une catastrophe ! Votre village est salement amoché...", new ArrayList<Ressource>(){{add(new Ressource("rock", -10));add(new Ressource("wood", -5));add(new Ressource("gold", -2));}}, 10),
    Glace("Aire Glacière", "Venue de nulle part, une aire glacière s'abbat sur toute la région ! \nVos druides ne l'avaient clairement pas vue venir celle la...", new ArrayList<Ressource>(){{add(new Ressource("wood", -15));add(new Ressource("food", -15));}}, 10),
    Trahison("Trahison de votre second", "Votre Second, votre ami de toujours, votre frère ! Comment à t'il pu vous trahir ?! \nVous auriez pu vous en douter quand vou l'avez surpris en train de comploter une nuit de pleine lune avec vos détracteurs...", new ArrayList<Ressource>(){{add(new Ressource("gold", -20));}}, 10),
    Alien("Débarquement Extra-Terrestre", "Des ennemis, vous en avez vu plus d'un. \nDes animaux aussi ! \nMais ça, vous l'aviez clairement jamais vu ! \nVous n'en revenez toujours pas, mais eux, avec ce qu'ils vous ont pris, ils pensent surement à revenir !", new ArrayList<Ressource>(){{add(new Ressource("gold", -10));add(new Ressource("food", -10));add(new Ressource("wood", -10));add(new Ressource("rock", -10));}}, 10),
    Ruines("Découverte ancestrale", "Une de vos patrouilles est tombée par hasard sur des ruines antiques à une dizaine de mètres de l'entrée !\nVous vous demandez comment vous avez pu les louper jusqu'à maintenant ! \nVous récupérez toutes les richesses disponibles !", new ArrayList<Ressource>(){{add(new Ressource("gold", 25));add(new Ressource("rock", 10));}}, 10),
    Determination("Population au TOP", "Votre population s'est démenée corps et ames cette saison ! \nEt ça paye !", new ArrayList<Ressource>(){{add(new Ressource("gold", 10));add(new Ressource("rock", 10));add(new Ressource("wood", 10));add(new Ressource("food", 10));}}, 10),
    Temporel("Un visiteur, venu d'ailleur", "Un voyageur temporel venant d'un lointant futur et se faisant appelé John Connor vous rend visite ! \nEt il n'est pas venu les mains vides !", new ArrayList<Ressource>(){{add(new Ressource("gold", 20));}}, 10),
    Bosquet("Oh, un joli petit bosquet à fraise", "Au grès d'une de vos ballade, vous tombez sur un joli bosquet à fraise ! \nVous en ramenez des bassines et des bassines ! \nCe bosquet à l'air intarrissable... Etrange !", new ArrayList<Ressource>(){{add(new Ressource("food", 20));add(new Ressource("wood", 5));}}, 10),
    Cadeau("Joyeux anniversaire", "Comment ça c'est pas votre anniversaire ? \nNe dites rien et acceptez quand meme les cadeaux mon vieux !", new ArrayList<Ressource>(){{add(new Ressource("gold", 5));add(new Ressource("rock", 15));add(new Ressource("food", 15));add(new Ressource("wood", 15));}}, 10),
    Deluge("Déluge", "Les Dieux se déchainent !! Le ciel vous tombe sur la tete ! \nDes pluies diluviennes se sont abattues sur votre village, et personne n'avait appri à nager....", new ArrayList<Ressource>(){{add(new Ressource("wood", -15));add(new Ressource("gold", -12));add(new Ressource("food", -15));}}, 10);


    protected String sTitre;
    protected String sDefinition;
    protected ArrayList<Ressource> rConsequence;
    protected int iProbability;

    TypeEvent(String sTitre, String sDefinition, ArrayList<Ressource> rConsequence, int iProbability) {
        this.sTitre = sTitre;
        this.sDefinition = sDefinition;
        this.rConsequence = rConsequence;
        this.iProbability = iProbability;
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
