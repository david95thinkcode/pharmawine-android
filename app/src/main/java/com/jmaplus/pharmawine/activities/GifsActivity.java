package com.jmaplus.pharmawine.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.Gift;

public class GifsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showGiftList(View v) {

        Intent i = new Intent(GifsActivity.this, GiftListActivity.class);
        switch (v.getId()) {
            case R.id.cv_gift_medical_team:
                i.putExtra(Gift.MEDICAL_TEAM, true);
                break;
            case R.id.cv_gift_hospital:
                Toast.makeText(this, "Hôpitaux !", Toast.LENGTH_SHORT).show();
                return;
//                i.putExtra(Gift.HOSPITAL, true);
//                break;
            case R.id.cv_gift_pharmacy:
                i.putExtra(Gift.PHARMACY, true);
                break;
        }
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
