package com.jmaplus.pharmawine.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.NetworkMember;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NetworkMemberAdapter extends RecyclerView.Adapter<NetworkMemberAdapter.ViewHolder> {


    private ArrayList<NetworkMember> networkMemberList;
    private Context mContext;

    public NetworkMemberAdapter(Context context, ArrayList<NetworkMember> networkMembers) {
        super();
        this.mContext = context;
        this.networkMemberList = networkMembers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.network_member_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final NetworkMember networkMember = networkMemberList.get(position);
        networkMember.load();

        if (networkMember.isValid()) {
            holder.tvMemberName.setText(networkMember.getName().concat(" ").concat(networkMember.getLastName()));
            holder.tvProgressLevel.setText(String.valueOf(networkMember.getDailyGoalLevel()).concat(" %"));

            holder.progressBar.setProgress(networkMember.getDailyGoalLevel());
            if (networkMember.getDailyGoalLevel() < 35) {
                holder.progressBar.setProgressColor(mContext.getResources().getColor(R.color.red));
            }
            if (networkMember.getDailyGoalLevel() >= 35 && networkMember.getDailyGoalLevel() < 70) {
                holder.progressBar.setProgressColor(mContext.getResources().getColor(R.color.orange));
            }

            if (networkMember.getDailyGoalLevel() > 70){
                holder.progressBar.setProgressColor(mContext.getResources().getColor(R.color.green));
            }
            Glide.with(mContext)
                    .load(networkMember.getProfilePicture())
                    .into(holder.imgMember);
        }
    }

    @Override
    public int getItemCount() {
        return this.networkMemberList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        final View item;
        final CircleImageView imgMember;
        final TextView tvMemberName, tvProgressLevel;
        final RoundCornerProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);

            item = itemView;
            imgMember = itemView.findViewById(R.id.img_member_picture);
            tvMemberName = itemView.findViewById(R.id.tv_member_name);
            tvProgressLevel = itemView.findViewById(R.id.tv_member_progress);
            progressBar = itemView.findViewById(R.id.progress_member_goal);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });

        }

    }
}
