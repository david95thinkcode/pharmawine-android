package com.jmaplus.pharmawine.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmaplus.pharmawine.R;

public class ReportHebdoEditManagerActivity extends AppCompatActivity {

    String activityTitle;
    ImageView fillChecker;
    TextView titleOfStepReportHebdo;
    ImageButton backToReportHebdoActivity;
    Intent mIntent = null;
    private Context mContext;
    private Fragment mFrag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_hebdo_edit_manager);

        LayoutInflater mInflater = LayoutInflater.from(this);
        mContext = this;
        mIntent = getIntent();

        View mView = mInflater.inflate(R.layout.custom_actionbar_step_hebdo_report, null);
        getSupportActionBar().setCustomView(mView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        fillChecker = mView.findViewById(R.id.fab_actionbar_to_check_fill_of_report);
        titleOfStepReportHebdo = mView.findViewById(R.id.title_of_champ_hebdo_report);
        backToReportHebdoActivity = mView.findViewById(R.id.imageButtonBackToReportActivity);

        activityTitle = mIntent.getStringExtra("ActivityTitle");
        if (activityTitle.equals(getResources().getString(R.string.statistique_medicale_week))) {
            fillChecker.setImageDrawable(getResources().getDrawable(R.drawable.check_green));
            titleOfStepReportHebdo.setText(getResources().getString(R.string.statistique_medicale_week));
            // show statistique fragment


        } else if (activityTitle.equals(getResources().getString(R.string.activite_menee))) {
            titleOfStepReportHebdo.setText(getResources().getString(R.string.activite_menee));
            //show activite mene fragment

        } else if (activityTitle.equals(getResources().getString(R.string.objection))) {
            titleOfStepReportHebdo.setText(getResources().getString(R.string.objection));
            //show objection fragment

        } else if (activityTitle.equals(getResources().getString(R.string.objectif_next_week))) {
            titleOfStepReportHebdo.setText(getResources().getString(R.string.objectif_next_week));
            //show fragment Objectif Next Week
        }


    }
}
