package com.jmaplus.pharmawine.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmaplus.pharmawine.R;


public class RapportHebdoActivity extends AppCompatActivity {

    private TextView tvDureHebdo, tvNumClientVuSurAll;
    private ImageView ivseestatistiquehebdo, ivFillActiviteMenee, ivFillObjection, ivFillObjectifNextWeek, ivIsSeeStat, ivIsFillActiviteMene, ivIsFillObjection, ivIsFillObjectionNextWeek;
    private String VuStat;
    private String VuActiviteMene;
    private String VuObjection;
    private String VuObjNextWeek;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_hebdo);
        //getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, 1400);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar_rapport_hebdo, null);
        mCustomView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 150, Gravity.NO_GRAVITY));

        mContext = this;
        VuStat = getResources().getString(R.string.statistique_medicale_week);
        VuActiviteMene = getResources().getString(R.string.activite_menee);
        VuObjection = getResources().getString(R.string.objection);
        VuObjNextWeek = getResources().getString(R.string.objectif_next_week);

        /*
         * All Fab that contain Is is put there to change after fill of report
         * the close red icon to green check icon
         */

        ivIsSeeStat = findViewById(R.id.iv_stat_week_check);
        ivseestatistiquehebdo = findViewById(R.id.iv_edit_statistique_medical_week);
        ivFillActiviteMenee = findViewById(R.id.iv_edit_activite_menee);
        ivIsFillActiviteMene = findViewById(R.id.iv_activite_mene_check);
        ivFillObjection = findViewById(R.id.iv_edit_objection);
        ivIsFillObjection = findViewById(R.id.iv_objection_check);
        ivFillObjectifNextWeek = findViewById(R.id.iv_edit_objectif_next_week);
        ivIsFillObjectionNextWeek = findViewById(R.id.iv_objectif_next_week_check);


        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        ivseestatistiquehebdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ReportHebdoEditManagerActivity.class);
                i.putExtra("ActivityTitle", VuStat);
                startActivity(i);
            }
        });

        ivFillActiviteMenee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ReportHebdoEditManagerActivity.class);
                i.putExtra("ActivityTitle", VuActiviteMene);
                startActivity(i);
            }
        });

        ivFillObjection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ReportHebdoEditManagerActivity.class);
                i.putExtra("ActivityTitle", VuObjection);
                startActivity(i);
            }
        });

        ivFillObjectifNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ReportHebdoEditManagerActivity.class);
                i.putExtra("ActivityTitle", VuObjNextWeek);
                startActivity(i);
            }
        });







    }
}
