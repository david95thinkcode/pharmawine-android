package com.jmaplus.pharmawine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.utils.Constants;

public class TranslucentNewClientActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String CLIENT_TYPE_EXTRA_KEY = "client_type";

    private Button btnProspectInconnu;
    private Button btnProspectConnu;
    private Button btnInconnuPharmacie;
    private Button btnInconnuCorpsMedical;
    private Button btnConnuPharmacie;
    private Button btnConnuCorpsMedical;

    private Boolean isProspectConnuButtonsVisible;
    private Boolean isProspectInconnuButtonsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        bindView();
    }

    private void bindView() {
        btnProspectInconnu = findViewById(R.id.btn_prospect_inconnu);
        btnInconnuCorpsMedical = findViewById(R.id.btn_inconnu_cm);
        btnInconnuPharmacie = findViewById(R.id.btn_inconnu_pharmacie);
        btnProspectConnu = findViewById(R.id.btn_prospect_connu);
        btnConnuCorpsMedical = findViewById(R.id.btn_connu_cm);
        btnConnuPharmacie = findViewById(R.id.btn_connu_pharmacie);

        btnProspectInconnu.setOnClickListener(this);
        btnInconnuCorpsMedical.setOnClickListener(this);
        btnInconnuPharmacie.setOnClickListener(this);
        btnProspectConnu.setOnClickListener(this);
        btnConnuCorpsMedical.setOnClickListener(this);
        btnConnuPharmacie.setOnClickListener(this);

        // View init state
        changePCButtonsState(false);
        changePIButtonsState(false);
    }

    private void changePIButtonsState(Boolean enablePIButtons) {
        if (enablePIButtons) {
            btnInconnuPharmacie.setVisibility(View.VISIBLE);
            btnInconnuCorpsMedical.setVisibility(View.VISIBLE);
        } else {
            btnInconnuPharmacie.setVisibility(View.GONE);
            btnInconnuCorpsMedical.setVisibility(View.GONE);
        }
        isProspectInconnuButtonsVisible = enablePIButtons;
    }

    private void changePCButtonsState(Boolean enablePCButtons) {
        if (enablePCButtons) {
            btnConnuPharmacie.setVisibility(View.VISIBLE);
            btnConnuCorpsMedical.setVisibility(View.VISIBLE);
        } else {
            btnConnuPharmacie.setVisibility(View.GONE);
            btnConnuCorpsMedical.setVisibility(View.GONE);
        }
        isProspectConnuButtonsVisible = enablePCButtons;
    }

    private void startActivityForPC(String clientType) {
        Intent i = new Intent(this, NewProspectConnuActivity.class);
        i.putExtra(CLIENT_TYPE_EXTRA_KEY, clientType);
        startActivity(i);
    }

    private void startActivityForPI(String clientType) {
        Intent i = new Intent(this, NewProspectInconnuActivity.class);
        i.putExtra(CLIENT_TYPE_EXTRA_KEY, clientType);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prospect_inconnu: {
                changePIButtonsState(!isProspectInconnuButtonsVisible);
            }
            break;
            case R.id.btn_inconnu_cm: {
                startActivityForPI(Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY);
            }
            break;
            case R.id.btn_inconnu_pharmacie: {
                startActivityForPI(Constants.CLIENT_PHARMACY_TYPE_KEY);
            }
            break;
            case R.id.btn_prospect_connu: {
                changePCButtonsState(!isProspectConnuButtonsVisible);
            }
            break;
            case R.id.btn_connu_cm: {
                startActivityForPC(Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY);
            }
            break;
            case R.id.btn_connu_pharmacie: {
                startActivityForPC(Constants.CLIENT_PHARMACY_TYPE_KEY);
            }
            break;
            default: {
                // Nothing to do
            }
            break;
        }
    }
}
