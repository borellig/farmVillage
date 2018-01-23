package com.android.group.farmvillage.Adapteur;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.group.farmvillage.Modele.PotionListAsk;
import com.android.group.farmvillage.R;

import java.util.List;

/**
 * Created by hm on 23/11/2017.
 */

public class Potionexchange_adapt extends ArrayAdapter<PotionListAsk> {
    ImageButton ibButtonPotion;

    public Potionexchange_adapt(Context context, List<PotionListAsk> askProduct) {
        super(context, 0, askProduct);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exchange_potionadapt, parent, false);
        }

        PotionViewHolder viewHolder = (PotionViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new PotionViewHolder();
            viewHolder.tvPseudo = (TextView) convertView.findViewById(R.id.TypePotion);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.QtitePotion);
            viewHolder.ibButtonPotion = (ImageButton) convertView.findViewById(R.id.PotionButtonValidate);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        final PotionListAsk requestPotion = getItem(position);
       // ibButtonPotion = (ImageButton) convertView.findViewById(R.id.PotionButtonValidate);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.tvPseudo.setText(requestPotion.getNamePuissane());
        viewHolder.tvDescription.setText(String.valueOf(requestPotion.getQtite()));
        viewHolder.ibButtonPotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infopotion(requestPotion);
            }
        });

        return convertView;
    }

    private class PotionViewHolder {
        public TextView tvPseudo;
        public TextView tvDescription;
        public ImageButton ibButtonPotion;

    }


    private void infopotion(final PotionListAsk potion){

        final int max;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MyDialogTheme);

        builder.setTitle("Information sur l'élément :");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);


        final TextView titleBox = new TextView(getContext());
        final TextView descriptBox = new TextView(getContext());

        switch(potion.getiPuissancePotion()){
            case 1:
                    titleBox.setText("Petite Puissance: + 10 ");

                break;
            case 2:
                titleBox.setText("Puissance Normal : + 40 ");

                break;
            case 3:
                titleBox.setText("Grande puissance : + 100 ");
                break;
            case 4:
                titleBox.setText("Puissance Colossale : +300 ");

                break;
        }
        layout.addView(titleBox);
        layout.addView(descriptBox);
        builder.setView(layout);
        //Controle de saisie
        descriptBox.setText(potion.getsDescription());

        builder.setNeutralButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.show();
    }



}

