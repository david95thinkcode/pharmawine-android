package com.jmaplus.pharmawine.activities;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.Laboratory;

import de.hdodenhof.circleimageview.CircleImageView;

public class LaboratoryDetailsActivity extends AppCompatActivity {

    public static String LABORATORY_ID_KEY = "com.jmaplus.pharmawine.activities.LaboratoryDetailsActivity.LABORATORY_ID_KEY";
    private Integer laboratoryId;
    private Laboratory laboratory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory_details);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);

        TextView title, subTitle;
        title = mCustomView.findViewById(R.id.title_text);
        subTitle = mCustomView.findViewById(R.id.subtitle_text);
        ImageView icon = mCustomView.findViewById(R.id.actionbar_icon);
        CircleImageView imgLogo = mCustomView.findViewById(R.id.actionbar_logo);

        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        laboratoryId = getIntent().getIntExtra(LABORATORY_ID_KEY, -1);
        laboratory =  Laboratory.getLaboratory(PharmaWine.mRealm, laboratoryId);

        Glide.with(this).load(laboratory.getLogo()).into(imgLogo);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(laboratory != null) {
            Toast.makeText(this, "Laboratoire : " +laboratory.getName(), Toast.LENGTH_LONG).show();
            title.setText(laboratory.getName());
            subTitle.setText(getResources().getString(R.string.laboratory));
        }else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
