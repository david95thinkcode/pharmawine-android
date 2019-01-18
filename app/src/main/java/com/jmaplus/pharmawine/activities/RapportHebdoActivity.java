package com.jmaplus.pharmawine.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.ActiviteMene;
import com.jmaplus.pharmawine.models.ObjectifNextWeek;
import com.jmaplus.pharmawine.models.RapportHebdo;
import com.jmaplus.pharmawine.utils.Constants;


public class RapportHebdoActivity extends AppCompatActivity {

    private TextView tvDureHebdo, tvNumClientVuSurAll;
    private ImageView ivseestatistiquehebdo, ivFillActiviteMenee, ivFillObjection, ivFillObjectifNextWeek, ivIsSeeStat, ivIsFillActiviteMene, ivIsFillObjection, ivIsFillObjectifNextWeek;
    private String VuStat;
    private String VuActiviteMene;
    private String VuObjection;
    private String VuObjNextWeek;
    private String contentObjection, contentAmPharma, contentAmGarde, contentAmRelationPubliq, contentAmReunion, contentAmZoneProfond, contentAmParcoursFidel, contentObNxWkPharma, contentObNxWkPrescripteurs;
    private boolean filledObjection, filledAmPharma, filledAmGarde, filledAmRelationPubliq, filledAmReunion, filledAmZoneProfond, filledAmParcoursFidel, filledObNxWkPharma, filledObNxWkPrescripteurs, seenStat;
    private Context mContext;
    private Button btnSendReportHebdo;
    private SharedPreferences spTogetFillOfReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_hebdo);
        //getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, 1400);
        spTogetFillOfReport = PreferenceManager.getDefaultSharedPreferences(this);
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
        ivIsFillObjectifNextWeek = findViewById(R.id.iv_objectif_next_week_check);
        btnSendReportHebdo = findViewById(R.id.btn_send_rapport_hebdo);

        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        btnSendReportHebdo.setBackgroundColor(getResources().getColor(R.color.white_gray));

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

    @Override
    protected void onResume() {
        super.onResume();
        contentObjection = spTogetFillOfReport.getString(Constants.REPORT_HEBDO_OBJECTION, "");
        contentAmGarde = spTogetFillOfReport.getString(Constants.REPORT_HEBDO_AM_GARDE, "");
        contentAmPharma = spTogetFillOfReport.getString(Constants.REPORT_HEBDO_AM_PHARMACY, "");
        contentAmRelationPubliq = spTogetFillOfReport.getString(Constants.REPORT_HEBDO_AM_RELATION_PUBLIQ, "");
        contentAmParcoursFidel = spTogetFillOfReport.getString(Constants.REPORT_HEBDO_AM_PARCOURS_FIDEL, "");
        contentAmReunion = spTogetFillOfReport.getString(Constants.REPORT_HEBDO_AM_REUNIONS, "");
        contentAmZoneProfond = spTogetFillOfReport.getString(Constants.REPORT_HEBDO_AM_ZONE_PROFOND, "");
        contentObNxWkPharma = spTogetFillOfReport.getString(Constants.REPORT_HEBDO_OBJECTIF_NEXT_WEEK_PHARMACY, "");
        contentObNxWkPrescripteurs = spTogetFillOfReport.getString(Constants.REPORT_HEBDO_OBJECTIF_NEXT_WEEK_PRESCRIPTEUR, "");
        seenStat = spTogetFillOfReport.getBoolean(Constants.REPORT_HEBDO_SEE_STATISTIQUE, false);


        filledObjection = contentObjection.isEmpty();
        filledAmGarde = contentAmGarde.isEmpty();
        filledAmPharma = contentAmPharma.isEmpty();
        filledAmRelationPubliq = contentAmRelationPubliq.isEmpty();
        filledAmParcoursFidel = contentAmParcoursFidel.isEmpty();
        filledAmReunion = contentAmReunion.isEmpty();
        filledAmZoneProfond = contentAmZoneProfond.isEmpty();
        filledObNxWkPharma = contentObNxWkPharma.isEmpty();
        filledObNxWkPrescripteurs = contentObNxWkPrescripteurs.isEmpty();

        if (!filledObjection) {
            ivIsFillObjection.setImageDrawable(getResources().getDrawable(R.drawable.valideicon));
        }

        if (!(filledAmGarde && filledAmPharma && filledAmReunion && filledAmZoneProfond && filledAmParcoursFidel && filledAmRelationPubliq)) {
            ivIsFillActiviteMene.setImageDrawable(getResources().getDrawable(R.drawable.valideicon));
        }

        if (!(filledObNxWkPharma && filledObNxWkPrescripteurs)) {
            ivIsFillObjectifNextWeek.setImageDrawable(getResources().getDrawable(R.drawable.valideicon));
        }
        if (seenStat) {
            ivIsSeeStat.setImageDrawable(getResources().getDrawable(R.drawable.valideicon));
        }

        if (!(filledObjection && filledAmGarde && filledAmPharma && filledAmReunion && filledAmZoneProfond && filledAmParcoursFidel && filledAmRelationPubliq && filledObNxWkPharma && filledObNxWkPrescripteurs)) {
            btnSendReportHebdo.setBackgroundColor(getResources().getColor(R.color.colorAccent));


            btnSendReportHebdo.setOnClickListener(new View.OnClickListener() {
                ActiviteMene activiteMene = new ActiviteMene(contentAmGarde, contentAmReunion, contentAmRelationPubliq, contentAmPharma, contentAmParcoursFidel, contentAmZoneProfond);
                ObjectifNextWeek objectifNextWeek = new ObjectifNextWeek(contentObNxWkPharma, contentObNxWkPrescripteurs);
                RapportHebdo rapportHebdo = new RapportHebdo(true, activiteMene, contentObjection, objectifNextWeek);
                Gson JSONTransRH = new Gson();
                String RapportHebdoInJSON = JSONTransRH.toJson(rapportHebdo);

                @Override
                public void onClick(View v) {
                    /*
                     * Send the JSON string to Server
                     * May be Save it to local database too
                     */
                    Log.e(getClass().getName(), RapportHebdoInJSON);
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_OBJECTION).apply();
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_SEE_STATISTIQUE).apply();
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_AM_GARDE).apply();
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_AM_PHARMACY).apply();
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_AM_RELATION_PUBLIQ).apply();
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_AM_PARCOURS_FIDEL).apply();
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_AM_REUNIONS).apply();
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_AM_ZONE_PROFOND).apply();
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_OBJECTIF_NEXT_WEEK_PHARMACY).apply();
                    spTogetFillOfReport.edit().remove(Constants.REPORT_HEBDO_OBJECTIF_NEXT_WEEK_PRESCRIPTEUR).apply();
                    finish();

                }
            });
        }


    }
}
