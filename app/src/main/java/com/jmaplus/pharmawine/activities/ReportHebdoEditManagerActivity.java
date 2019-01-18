package com.jmaplus.pharmawine.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.rapportHebdo.ActiviteMeneGardeFragment;
import com.jmaplus.pharmawine.fragments.rapportHebdo.ObjectifNexWkPrescripteurFragment;
import com.jmaplus.pharmawine.fragments.rapportHebdo.ObjectionFragment;
import com.jmaplus.pharmawine.fragments.rapportHebdo.StatistiqueHebdoFragment;
import com.jmaplus.pharmawine.models.RapportHebdo;
import com.jmaplus.pharmawine.models.ActiviteMene;

public class ReportHebdoEditManagerActivity extends AppCompatActivity {

    String activityTitle;
    ImageView fillChecker;
    TextView titleOfStepReportHebdo;
    ImageButton backToReportHebdoActivity;
    Intent mIntent = null;
    FragmentManager fragManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_hebdo_edit_manager);

        LayoutInflater mInflater = LayoutInflater.from(this);
        mIntent = getIntent();

        View mView = mInflater.inflate(R.layout.custom_actionbar_step_hebdo_report, null);
        getSupportActionBar().setCustomView(mView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        fillChecker = mView.findViewById(R.id.iv_actionbar_to_check_fill_of_report);
        titleOfStepReportHebdo = mView.findViewById(R.id.title_of_champ_hebdo_report);
        backToReportHebdoActivity = mView.findViewById(R.id.imageButtonBackToReportActivity);

        FragmentTransaction fragTransaction = fragManager.beginTransaction();

        activityTitle = mIntent.getStringExtra("ActivityTitle");
        if (activityTitle.equals(getResources().getString(R.string.statistique_medicale_week))) {
            fillChecker.setImageDrawable(getResources().getDrawable(R.drawable.valideicon));
            titleOfStepReportHebdo.setText(getResources().getString(R.string.statistique_medicale_week));
            StatistiqueHebdoFragment statistiqueHebdoFragment = new StatistiqueHebdoFragment();
            fragTransaction.add(R.id.fragment_container_report_hebdo, statistiqueHebdoFragment);
            fragTransaction.commit();


            // show statistique fragment


        } else if (activityTitle.equals(getResources().getString(R.string.activite_menee))) {
            titleOfStepReportHebdo.setText(getResources().getString(R.string.activite_menee));
            ActiviteMeneGardeFragment activiteMeneGardeFragment = new ActiviteMeneGardeFragment();
            fragTransaction.add(R.id.fragment_container_report_hebdo, activiteMeneGardeFragment);
            fragTransaction.commit();


            //show activite mene fragment

        } else if (activityTitle.equals(getResources().getString(R.string.objection))) {
            titleOfStepReportHebdo.setText(getResources().getString(R.string.objection));
            ObjectionFragment objectionFragment = new ObjectionFragment();
            fragTransaction.add(R.id.fragment_container_report_hebdo, objectionFragment);
            fragTransaction.commit();
            //show objection fragment

        } else if (activityTitle.equals(getResources().getString(R.string.objectif_next_week))) {
            titleOfStepReportHebdo.setText(getResources().getString(R.string.objectif_next_week));
            ObjectifNexWkPrescripteurFragment objectifNexWkPrescripteurFragment = new ObjectifNexWkPrescripteurFragment();
            fragTransaction.add(R.id.fragment_container_report_hebdo, objectifNexWkPrescripteurFragment);
            fragTransaction.commit();
            //show fragment Objectif Next Week
        }


    }

    public void backBehavior(View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
