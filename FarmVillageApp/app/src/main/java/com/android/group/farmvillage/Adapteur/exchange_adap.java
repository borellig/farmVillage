package com.android.group.farmvillage.Adapteur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.group.farmvillage.Modele.AskExchange;
import com.android.group.farmvillage.R;

import java.util.List;

/**
 * Created by hm on 14/11/2017.
 */

public class exchange_adap extends ArrayAdapter<AskExchange> {

    public exchange_adap(Context context, List<AskExchange> askProduct) {
        super(context, 0, askProduct);
    }
    ImageButton ButtonValidate;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exchange_adapt, parent, false);
        }

        AskViewHolder viewHolder = (AskViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new AskViewHolder();
            viewHolder.tvPseudo = (TextView) convertView.findViewById(R.id.UserNameRemote);
            viewHolder.tvText = (TextView) convertView.findViewById(R.id.ValueExchange);
            viewHolder.ibButtonValide = (ImageButton) convertView.findViewById(R.id.ValideExchange);
          //  viewHolder.ibButtonValide = (ImageButton) convertView.findViewById(R.id.)
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        AskExchange request = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.tvPseudo.setText(request.getsUserNameRemote());
        viewHolder.tvText.setText(String.valueOf(request.getrRessource().getQte()));


        return convertView;
    }

    private class AskViewHolder {
        public TextView tvPseudo;
        public TextView tvText;
        public ImageButton ibButtonValide;
    }
}

