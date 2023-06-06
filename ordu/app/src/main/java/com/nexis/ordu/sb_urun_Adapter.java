package com.nexis.ordu;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class sb_urun_Adapter extends RecyclerView.Adapter<sb_urun_Adapter.ViewHolder> {

    private ArrayList<sb_urunBilgi> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView sb_isim;
        private final TextView sb_fiyat;
        private final TextView sb_tanim;
        private final ImageView sb_foto;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            sb_isim = (TextView) view.findViewById(R.id.sb_txt_ana_item_isim);
            sb_fiyat = (TextView) view.findViewById(R.id.sb_txt_ana_item_fiyat);
            sb_tanim = (TextView) view.findViewById(R.id.sb_txt_ana_item_tanim);
            sb_foto = view.findViewById(R.id.sb_iv_ana_item_foto);
        }
    }


    public sb_urun_Adapter(ArrayList<sb_urunBilgi> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sb_analiste_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.sb_isim.setText(localDataSet.get(position).sb_isim);
        viewHolder.sb_fiyat.setText(localDataSet.get(position).sb_fiyat);
        viewHolder.sb_tanim.setText(localDataSet.get(position).sb_tanim);
        String url = localDataSet.get(position).sb_url;
        Picasso.get().load(url).into(viewHolder.sb_foto);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}