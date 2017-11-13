package com.android.group.farmvillage.Modele;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

/**
 * Created by geoffrey on 13/11/17.
 */

public class Batiment implements ListAdapter {

    protected int iId;
    protected boolean bActif;
    protected TypeBatiment tbBatiment;
    protected int iCoordX;
    protected int iCoordY;
    protected String sSens;
    protected int iTaille;


    public int getiId() {
        return iId;
    }

    public TypeBatiment getTbBatiment() {
        return tbBatiment;
    }

    public Batiment(int id, TypeBatiment tbBatiment, boolean bEtat, int x, int y){
        this.iId=id;
        this.bActif=true;
        this.tbBatiment=TypeBatiment.valueOf(tbBatiment.name());
        this.iCoordX=x;
        this.iCoordY=y;

    }
    /**
     * Change l'état d'un batiment, le rend innactif si il était actif et actif si il était inactif.
     */
    protected void changeEtat(){
        this.bActif=!bActif;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
