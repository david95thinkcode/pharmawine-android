package com.jmaplus.pharmawine.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.WholesalerDetailsActivity;
import com.jmaplus.pharmawine.models.Wholesaler;

import java.util.ArrayList;

public class WholesalerAdapter extends RecyclerView.Adapter<WholesalerAdapter.ViewHolder> {

    private final ArrayList<Wholesaler> wholesalerList;
    private Context mContext;

    public WholesalerAdapter(ArrayList<Wholesaler> wholesalers, Context context) {
        super();
        this.wholesalerList = wholesalers;
        this.mContext = context;
    }

    @NonNull
    @Override
    public WholesalerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wholesaler_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Wholesaler wholesaler = wholesalerList.get(position);

        holder.tvWholesalerName.setText(wholesaler.getName());
        Glide.with(mContext)
                .load(wholesaler.getLogo())
                .into(holder.imgWholesalerLogo);
    }

    @Override
    public int getItemCount() {
        return this.wholesalerList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvWholesalerName;
        final ImageView imgWholesalerLogo;
        final CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_wholesaler_item);
            tvWholesalerName = itemView.findViewById(R.id.tv_wholesaler_name);
            imgWholesalerLogo = itemView.findViewById(R.id.img_wholesaler_logo);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mContext.startActivity(new Intent(mContext, WholesalerDetailsActivity.class).putExtra(WholesalerDetailsActivity.WHOLESALER_ID_KEY, wholesalerList.get(position).getId()));
                }
            });
        }
    }
}
