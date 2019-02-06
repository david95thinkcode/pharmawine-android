package com.jmaplus.pharmawine.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.NetworkMemberAdapter;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Network;
import com.jmaplus.pharmawine.models.SimpleUser;
import com.jmaplus.pharmawine.utils.RetrofitCalls.NetworkCalls;
import com.jmaplus.pharmawine.utils.RetrofitCalls.UserCalls;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class NetworksActivity extends AppCompatActivity implements
        NetworkCalls.Callbacks,
        UserCalls.Callbacks {

    private LinearLayout layGoToMe, layGoToSupervisor;
    private TextView tvName, tvNetworkLabel, tvProgress;
    private ImageView imgProfil, imgGoToMe, imgGoToSuperviseur;
    private RoundCornerProgressBar progressBar;
    //    private FloatingActionButton fabPersonalGoals;
    private ProgressBar mProgressBarOfMembers;

    private AuthUser mAuthUser;
    private List<SimpleUser> mNetworkMembers;
    private Network mNetwork = new Network();
    private SimpleUser mSupervisor = new SimpleUser();


    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;

    private NetworkMemberAdapter networkMemberAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuthUser = AuthUser.getAuthenticatedUser(this);
        mNetworkMembers = new ArrayList();

        initViews();

        getMembers();

        getNetworkDetails();

        initList();
    }

    public void getMembers() {
        try {
            mProgressBarOfMembers.setVisibility(View.VISIBLE);
            NetworkCalls.getNetworkMembers(
                    this, AuthUser.getToken(this), mAuthUser.getNetworkId());
        } catch (Exception e) {
            Log.e(TAG, "getMembers: " + e.getMessage());
            Toast.makeText(this, R.string.une_erreur_s_est_produite, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onNetworkMembersResponse(@Nullable List<SimpleUser> networkMembers) {
        Log.i(TAG, "onNetworkMembersResponse: Members fetched");
        mProgressBarOfMembers.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        if (networkMembers.isEmpty()) {
            Toast.makeText(this, "Aucun membre trouvé dans votre reseau", Toast.LENGTH_SHORT).show();
        } else {
            mNetworkMembers.clear();
            networkMemberAdapter.notifyDataSetChanged();

            for (SimpleUser s : networkMembers) {
                mNetworkMembers.add(s);
                networkMemberAdapter.notifyItemInserted(mNetworkMembers.size() - 1);
            }
        }
    }

    public void getNetworkDetails() {
        try {
            NetworkCalls.getDetails(this, AuthUser.getToken(this));
        } catch (Exception e) {
            Log.e(TAG, "getNetworkDetails: " + e.getMessage());
            Toast.makeText(this, R.string.une_erreur_s_est_produite, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetworkDetailsResponse(@Nullable Network network) {
        mNetwork = network;
        updateUIForNetworkDetails();
        Toast.makeText(this, "Network details fetched", Toast.LENGTH_SHORT).show();
    }

    public void getSupervisorDetails() {
        try {
            UserCalls.getDetails(mNetwork.getId(), this, AuthUser.getToken(this));
        } catch (Exception e) {
            Toast.makeText(this, R.string.une_erreur_s_est_produite, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "getSupervisorDetails: " + e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void initViews() {

        // Binding views
        layGoToMe = findViewById(R.id.lay_goto_me);
        layGoToSupervisor = findViewById(R.id.lay_goto_supervisor);
        imgGoToMe = findViewById(R.id.img_goto_me);
        imgGoToSuperviseur = findViewById(R.id.img_goto_supervisor);

        imgProfil = findViewById(R.id.img_profil);
        tvName = findViewById(R.id.tv_name);
        tvNetworkLabel = findViewById(R.id.tv_network_label);
        tvProgress = findViewById(R.id.tv_progress);
        progressBar = findViewById(R.id.progress_network);
        mProgressBarOfMembers = findViewById(R.id.progressBar_members);

//        fabPersonalGoals = findViewById(R.id.fab_personal_goals);

        recyclerView = findViewById(R.id.rv_network_members);
        mSwipeRefreshLayout = findViewById(R.id.swipe_network_members);

        // Initial states
        mProgressBarOfMembers.setVisibility(View.GONE);


        goToMe();

        // Listenners
        imgGoToMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMe();
            }
        });

        imgGoToSuperviseur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSupervisor();
            }
        });

//        fabPersonalGoals.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(NetworksActivity.this, PersonalGoalsActivity.class));
//            }
//        });
    }

    public void goToMe() {
        layGoToSupervisor.setVisibility(View.VISIBLE);
        layGoToMe.setVisibility(View.GONE);

        tvName.setText(mAuthUser.getFullName());
        tvNetworkLabel.setText("Réseau - " + mNetwork.getName());
        progressBar.setProgress(85);
        tvProgress.setText(String.valueOf(85).concat(" %"));

        if (mAuthUser.getAvatar() != null && !mAuthUser.getAvatar().isEmpty()) {
            Glide.with(this).load(mAuthUser.getAvatar()).into(imgProfil);
        }
    }

    public void goToSupervisor() {

        if (mSupervisor != null && mSupervisor.getFullName() != null && !mSupervisor.getFullName().isEmpty()) {
            try {
                layGoToMe.setVisibility(View.VISIBLE);
                layGoToSupervisor.setVisibility(View.GONE);

                imgProfil.setImageResource(R.drawable.profil_sup);
                tvName.setText(mSupervisor.getFullName());
                tvNetworkLabel.setText(String.valueOf("Superviseur réseau - ").concat(mNetwork.getName()));

                progressBar.setProgress(50);
                tvProgress.setText(String.valueOf(50).concat(" %"));

                if (mSupervisor.getAvatar() != null && !mSupervisor.getAvatar().isEmpty()) {
                    Glide.with(this).load(mSupervisor.getAvatar()).into(imgProfil);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                Toast.makeText(this, "Impossible d'afficher les donnees du superviseur", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initList() {

//        Init the adapter
        networkMemberAdapter = new NetworkMemberAdapter(this, mNetworkMembers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(networkMemberAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMembers();
            }
        });
    }

    private void notifyChanges() {
        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        networkMemberAdapter.notifyDataSetChanged();
    }

    // ======================================= CALLBACKS =======================================

    private void updateUIForNetworkDetails() {
        tvName.setText(mAuthUser.getFullName());
        tvNetworkLabel.setText("Réseau - " + mNetwork.getName());

        // Todo: set correct value for the goal progress bar
        progressBar.setProgress(25);
        tvProgress.setText(String.valueOf(25).concat(" %"));
    }

    @Override
    public void onNetworkMembersFailure() {
        Toast.makeText(this, "Echec de la requete", Toast.LENGTH_SHORT).show();
        mProgressBarOfMembers.setVisibility(View.GONE);
    }


    @Override
    public void onNetworkDetailsFailure() {
        Toast.makeText(this, "Echec de la requete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserDetailsResponse(@android.support.annotation.Nullable SimpleUser response) {
        mSupervisor = response;
        Toast.makeText(this, "Supervisor details fetched", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserDetailsFailure() {

    }
}
