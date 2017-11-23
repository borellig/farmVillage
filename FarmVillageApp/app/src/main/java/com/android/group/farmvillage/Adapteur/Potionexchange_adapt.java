package com.android.group.farmvillage.Adapteur;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.group.farmvillage.Modele.PotionAskExchange;
import com.android.group.farmvillage.R;

import java.util.List;

/**
 * Created by hm on 23/11/2017.
 */

public class Potionexchange_adapt extends ArrayAdapter<PotionAskExchange> {
    ImageButton ibButtonPotion;

    public Potionexchange_adapt(Context context, List<PotionAskExchange> askProduct) {
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
            viewHolder.tvPseudo = (TextView) convertView.findViewById(R.id.UserNameRemotePotion);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.ValueTypePotion);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        final PotionAskExchange requestPotion = getItem(position);
        ibButtonPotion = (ImageButton) convertView.findViewById(R.id.PotionButtonValidate);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.tvPseudo.setText(requestPotion.getsUserNameRemotePotion());
        viewHolder.tvDescription.setText(String.valueOf(requestPotion.getrRessource().getQte()));
        ibButtonPotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Les deguns ? ","oui");
            }
        });
        return convertView;
    }

    private class PotionViewHolder {
        public TextView tvPseudo;
        public TextView tvDescription;
        public ImageButton ibButtonPotion;
    }


}

