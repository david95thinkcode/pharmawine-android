package com.jmaplus.pharmawine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.jmaplus.pharmawine.R;

public class EditPharmacyActivity extends AppCompatActivity {

    public static final String PHARMACY_ID_KEY = "com.jmaplus.pharmawine.activities.EditPharmacyActivity.pharmacyId";
    private int pharmacyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pharmacy);

        pharmacyId = getIntent().getIntExtra(PHARMACY_ID_KEY, -1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
