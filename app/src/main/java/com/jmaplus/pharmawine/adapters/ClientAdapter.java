package com.jmaplus.pharmawine.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.ClientDetailsActivity;
import com.jmaplus.pharmawine.models.MedicalTeam;
import com.jmaplus.pharmawine.models.Pharmacy;
import com.jmaplus.pharmawine.utils.Constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {


    private ArrayList<MedicalTeam> medicalTeamList;
    private ArrayList<Pharmacy> pharmacyList;
    private Context mContext;
    public static final Integer MEDICAL_TEAM = 1, PHARMACY = 2;
    private Integer listType;

    public ClientAdapter(ArrayList<MedicalTeam> medicalTeams, ArrayList<Pharmacy> pharmacies, Context context, @NonNull Integer listType) {
        super();
        this.mContext = context;
        this.medicalTeamList = medicalTeams;
        this.pharmacyList = pharmacies;
        this.listType = listType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        int fillingLevel = 0;

        if (listType.equals(MEDICAL_TEAM)) {
            final MedicalTeam medicalTeam = medicalTeamList.get(position);
            medicalTeam.load();

            if (medicalTeam.isLoaded() && medicalTeam.isValid()) {
                Glide.with(mContext).load(medicalTeam.getProfilePicture()).into(holder.imgClient);
                holder.tvClientName.setText(medicalTeam.getName().concat(" ").concat(medicalTeam.getLastName()));
                holder.tvClientSubtitle.setText(medicalTeam.getCategory());
                holder.tvProgressLevel.setText(String.valueOf(medicalTeam.getFillingLevel()) + " %");
                holder.progressBar.setProgress(medicalTeam.getFillingLevel());

                fillingLevel = medicalTeam.getFillingLevel();
            }

        } else if (listType.equals(PHARMACY)) {
            final Pharmacy pharmacy = pharmacyList.get(position);
            pharmacy.load();

            if (pharmacy.isLoaded() && pharmacy.isValid()) {
                Glide.with(mContext).load(R.drawable.pharma_icon).into(holder.imgClient);
                holder.tvClientName.setText(pharmacy.getName());
                holder.tvClientSubtitle.setText(pharmacy.getAddress());
                holder.tvProgressLevel.setText(String.valueOf(pharmacy.getFillingLevel()) + " %");
                holder.progressBar.setProgress(pharmacy.getFillingLevel());

                fillingLevel = pharmacy.getFillingLevel();
            }
        }

        if (fillingLevel < 35) {
            holder.progressBar.setProgressColor(mContext.getResources().getColor(R.color.red));
        } else if (fillingLevel < 69) {
            holder.progressBar.setProgressColor(mContext.getResources().getColor(R.color.orange));
        } else {
            holder.progressBar.setProgressColor(mContext.getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return (listType.equals(MEDICAL_TEAM)) ? this.medicalTeamList.size() : this.pharmacyList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        final View item;
        final CircleImageView imgClient;
        final TextView tvClientName, tvClientSubtitle, tvProgressLevel;
        final RoundCornerProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);

            item = itemView;
            imgClient = itemView.findViewById(R.id.img_client);
            tvClientName = itemView.findViewById(R.id.tv_client_name);
            tvClientSubtitle = itemView.findViewById(R.id.tv_client_category);
            tvProgressLevel = itemView.findViewById(R.id.tv_progress);
            progressBar = itemView.findViewById(R.id.progress_filling);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Intent i = new Intent(mContext, ClientDetailsActivity.class);

                    if(listType.equals(MEDICAL_TEAM)) {

                        MedicalTeam medicalTeam = medicalTeamList.get(position);
                        medicalTeam.load();

                        if(medicalTeam.isValid()) {
                            i.putExtra(ClientDetailsActivity.CLIENT_TYPE_KEY, Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY);
                            i.putExtra(ClientDetailsActivity.CLIENT_ID_KEY, medicalTeam.getId());
                            mContext.startActivity(i);
                        } else {
                            Toast.makeText(mContext, "Lecture de donnée non réussie. Réessayez s'il vous plait", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(listType.equals(PHARMACY)) {

                        Pharmacy pharmacy = pharmacyList.get(position);
                        pharmacy.load();

                        if(pharmacy.isValid()) {
                            i.putExtra(ClientDetailsActivity.CLIENT_TYPE_KEY, Constants.CLIENT_PHARMACY_TYPE_KEY);
                            i.putExtra(ClientDetailsActivity.CLIENT_ID_KEY, pharmacy.getId());
                            mContext.startActivity(i);
                        } else {
                            Toast.makeText(mContext, "Lecture de donnée non réussie. Réessayez s'il vous plait", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
