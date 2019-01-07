package com.jmaplus.pharmawine.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.LaboratoryDetailsActivity;
import com.jmaplus.pharmawine.models.Laboratory;

import java.util.ArrayList;

public class LaboratoryAdapter extends RecyclerView.Adapter<LaboratoryAdapter.ViewHolder> {

    private final ArrayList<Laboratory> laboratoryList;
    private Context mContext;

    public LaboratoryAdapter(ArrayList<Laboratory> laboratories, Context context) {
        super();
        this.laboratoryList = laboratories;
        this.mContext = context;
    }

    @NonNull
    @Override
    public LaboratoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.laboratory_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Laboratory laboratory = laboratoryList.get(position);
        laboratory.load();

        if (laboratory.isValid()) {
            holder.tvLaboratoryName.setText(laboratory.getName());
            Glide.with(mContext)
                    .load(laboratory.getLogo())
                    .into(holder.imgLaboratoryLogo);
        }
    }

    @Override
    public int getItemCount() {
        return this.laboratoryList.size();
    }

    public void setLaboratoryList(ArrayList<Laboratory> laboratories) {
        this.laboratoryList.clear();
        this.laboratoryList.addAll(laboratories);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvLaboratoryName;
        final ImageView imgLaboratoryLogo;

        ViewHolder(View itemView) {
            super(itemView);
            tvLaboratoryName = itemView.findViewById(R.id.tv_laboratory_name);
            imgLaboratoryLogo = itemView.findViewById(R.id.img_laboratory_logo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mContext.startActivity(new Intent(mContext, LaboratoryDetailsActivity.class).putExtra(LaboratoryDetailsActivity.LABORATORY_ID_KEY, laboratoryList.get(position).getId()));
                }
            });
        }
    }
}
