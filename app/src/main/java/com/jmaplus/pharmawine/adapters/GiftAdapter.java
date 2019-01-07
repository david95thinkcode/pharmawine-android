package com.jmaplus.pharmawine.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.Gift;
import com.jmaplus.pharmawine.models.MedicalTeam;
import com.jmaplus.pharmawine.models.Pharmacy;

import java.util.ArrayList;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ViewHolder> {

    private final ArrayList<Gift> giftList;
    private Context mContext;
    private String GIFT_TYPE;

    public GiftAdapter(ArrayList<Gift> gifts, Context context, @NonNull String giftType) {
        super();
        this.mContext = context;
        this.giftList = gifts;
        this.GIFT_TYPE = giftType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gift_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Gift gift = giftList.get(position);

        holder.tvValue.setText(String.valueOf(gift.getValue()).concat(" €"));

        switch (GIFT_TYPE) {
            case Gift.MEDICAL_TEAM:
                if (gift.getDonatorType().equals(Gift.MEDICAL_TEAM)) {
                    MedicalTeam medicalTeam = MedicalTeam.getById(PharmaWine.mRealm, gift.getWhoId());

                    if(medicalTeam != null){
                        Glide.with(mContext).load(medicalTeam.getProfilePicture()).into(holder.imgProfile);
                        holder.tvName.setText(medicalTeam.getName().concat(" ").concat(medicalTeam.getLastName()));
                        holder.tvSubtitle.setText(medicalTeam.getCategory());
                    }
                }

                break;
            case Gift.HOSPITAL:
                if (gift.getDonatorType().equals(Gift.HOSPITAL)) {
//                    TODO Implement gifts from hospitals
                }
                break;
            case Gift.PHARMACY:
                if (gift.getDonatorType().equals(Gift.PHARMACY)) {
                    Pharmacy pharmacy = Pharmacy.getById(PharmaWine.mRealm, gift.getWhoId());

                    if(pharmacy != null) {
                        Glide.with(mContext).load(R.drawable.pharma_icon).into(holder.imgProfile);
                        holder.tvName.setText(pharmacy.getName());
                        holder.tvSubtitle.setText((pharmacy.getEmployees() > 50) ? "Grosse" : "Petite");
                    }
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.giftList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgProfile;
        final TextView tvName, tvSubtitle, tvValue;

        ViewHolder(View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.img_gift_who_profile);
            tvName = itemView.findViewById(R.id.tv_gift_who_name);
            tvSubtitle = itemView.findViewById(R.id.tv_gift_subtitle);
            tvValue = itemView.findViewById(R.id.tv_gift_value);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
//                    TODO : Implement product click action here
                }
            });
        }
    }
}
