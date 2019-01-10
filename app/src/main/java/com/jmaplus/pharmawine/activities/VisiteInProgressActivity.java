package com.jmaplus.pharmawine.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmaplus.pharmawine.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisiteInProgressActivity extends AppCompatActivity {

    private FrameLayout mFrameContainer;
    private Fragment mFragment = null;
    private CircleImageView profileImage;
    private TextView tvNomPrenom, tvTypeClient, tvCategoryClient;
    private Button btnVisiteEnd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite_in_progress);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View customActionBarVisite = mInflater.inflate(R.layout.actionbar_visit, null);

        profileImage = findViewById(R.id.img_profil_client);
        tvNomPrenom = findViewById(R.id.tv_nom_client);
        tvTypeClient = findViewById(R.id.tv_type_client);
        tvCategoryClient = findViewById(R.id.tv_category_client);
        btnVisiteEnd = findViewById(R.id.btn_visit_fini);

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

        btnVisiteEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confimationDialog();
            }
        });




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


}
