package com.jmaplus.pharmawine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.jmaplus.pharmawine.R;

public class EditMedicalTeamActivity extends AppCompatActivity {


    public static final String MEDICAL_ID_KEY = "com.jmaplus.pharmawine.activities.medicalTeamId";
    private int medicalTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medical_team);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        medicalTeamId = getIntent().getIntExtra(MEDICAL_ID_KEY, -1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
