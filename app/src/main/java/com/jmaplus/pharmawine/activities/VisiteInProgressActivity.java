package com.jmaplus.pharmawine.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.ToBeginReportFragment;

public class VisiteInProgressActivity extends AppCompatActivity {

    private FrameLayout mFrameContainer;
    private ToBeginReportFragment mToBeginReportFragment = new ToBeginReportFragment();
    private Fragment mFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite_in_progress);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View customActionBarVisite = mInflater.inflate(R.layout.actionbar_visit, null);

        ImageView iconClose = customActionBarVisite.findViewById(R.id.actionbar_ic_close);
        TextView titre = customActionBarVisite.findViewById(R.id.title_text);

        getSupportActionBar().setCustomView(customActionBarVisite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        titre.setText("Client");
        iconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confimationDialog();
            }
        });

        mFrameContainer = findViewById(R.id.frame_container_visite);
        showFragment(mToBeginReportFragment);


    }

    private void confimationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage(R.string.msg_cancel_visite);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        builder.show();
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container_visite, fragment);
        mFragmentTransaction.commit();
        mFragment = fragment;
    }


}
