package com.jmaplus.pharmawine.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.Client;
import com.jmaplus.pharmawine.utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisiteInProgressActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView tvNomPrenom, tvTypeClient, tvCategoryClient;
    private Button btnVisiteEnd;
    private Client mClient = new Client();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite_in_progress);

        mClient.setId(getIntent().getStringExtra(Constants.CLIENT_ID_KEY));
        mClient.setFirstName(getIntent().getStringExtra(Constants.CLIENT_FIRSTNAME_KEY));
        mClient.setLastName(getIntent().getStringExtra(Constants.CLIENT_LASTNAME_KEY));
        mClient.setAvatarUrl(getIntent().getStringExtra(Constants.CLIENT_AVATAR_URL_KEY));
        mClient.setStatus(getIntent().getStringExtra(Constants.CLIENT_STATUS_KEY));
        mClient.setSpeciality(getIntent().getStringExtra(Constants.CLIENT_SPECIALITY_KEY));

        initViews();

        updateViewsContent();
    }

    private void initViews() {

        profileImage = findViewById(R.id.img_profil_client);
        tvNomPrenom = findViewById(R.id.tv_nom_client);
        tvTypeClient = findViewById(R.id.tv_type_client);
        tvCategoryClient = findViewById(R.id.tv_category_client);
        btnVisiteEnd = findViewById(R.id.btn_visit_fini);

        btnVisiteEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialogToEditRapport();
            }
        });
    }

    private void updateViewsContent() {
        tvNomPrenom.setText(mClient.getFullName());
        tvTypeClient.setText(mClient.getSpeciality());
        tvCategoryClient.setText(mClient.getStatus());

        if (!mClient.getAvatarUrl().isEmpty()) {
            Glide.with(this).load(mClient.getAvatarUrl()).into(profileImage);
        }

    }

    private void confirmationDialogToEditRapport() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Visite terminée ?");
        builder.setMessage(R.string.msg_confim_visite_end);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: Go to edit rapport activity and pass the client ID to it
                //
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

    private void confirmationDialog() {
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
