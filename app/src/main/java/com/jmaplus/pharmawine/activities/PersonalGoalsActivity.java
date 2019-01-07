package com.jmaplus.pharmawine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.AuthenticatedUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalGoalsActivity extends AppCompatActivity {

    private AuthenticatedUser me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_goals);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);

        TextView title, subTitle;
        title = mCustomView.findViewById(R.id.title_text);
        subTitle = mCustomView.findViewById(R.id.subtitle_text);
        ImageView icon = mCustomView.findViewById(R.id.actionbar_icon);
        CircleImageView imgLogo = mCustomView.findViewById(R.id.actionbar_logo);

        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_left);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        icon.setImageResource(R.drawable.ic_action_left);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        me = AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm);

        if (me != null) {
            title.setText(getResources().getString(R.string.activity_personal_goals_title));
            subTitle.setText(me.getName().concat(" ").concat(me.getLastName()));
            Glide.with(this).load(me.getProfilePicture()).into(imgLogo);
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
