package com.jmaplus.pharmawine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.NetworkMemberAdapter;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.jmaplus.pharmawine.models.Network;
import com.jmaplus.pharmawine.models.NetworkMember;
import com.jmaplus.pharmawine.models.SimpleUser;
import com.jmaplus.pharmawine.services.ApiClient;
import com.jmaplus.pharmawine.services.ApiInterface;
import com.jmaplus.pharmawine.services.responses.NetworkMembersResponse;
import com.jmaplus.pharmawine.utils.PrefManager;
import com.jmaplus.pharmawine.utils.RetrofitCalls.NetworkCalls;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworksActivity extends AppCompatActivity implements NetworkCalls.Callbacks {

    private LinearLayout layGoToMe, layGoToSupervisor;
    private TextView tvName, tvNetworkLabel, tvProgress;
    private ImageView imgProfil, imgGoToMe, imgGoToSuperviseur;
    private RoundCornerProgressBar progressBar;
    private FloatingActionButton fabPersonalGoals;

    private AuthenticatedUser me;

    private AuthUser mAuthUser;
    private List<SimpleUser> mNetworkMembers;


    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;

    private ArrayList<NetworkMember> networkMemberList;
    private NetworkMemberAdapter networkMemberAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PrefManager prefManager;

    private boolean isMembersLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layGoToMe = findViewById(R.id.lay_goto_me);
        layGoToSupervisor = findViewById(R.id.lay_goto_supervisor);
        imgGoToMe = findViewById(R.id.img_goto_me);
        imgGoToSuperviseur = findViewById(R.id.img_goto_supervisor);

        imgProfil = findViewById(R.id.img_profil);
        tvName = findViewById(R.id.tv_name);
        tvNetworkLabel = findViewById(R.id.tv_network_label);
        tvProgress = findViewById(R.id.tv_progress);
        progressBar = findViewById(R.id.progress_network);

        fabPersonalGoals = findViewById(R.id.fab_personal_goals);

        recyclerView = findViewById(R.id.rv_network_members);
        mSwipeRefreshLayout = findViewById(R.id.swipe_network_members);

//        me = AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm);
        prefManager = new PrefManager(this);

        mAuthUser = AuthUser.getAuthenticatedUser(this);
        mNetworkMembers = new ArrayList();

        initViews();

        networkMemberList = new ArrayList<>();

        getMembers();

        initList();
    }

    public void getMembers() {
        try {
            NetworkCalls.getNetworkMembers(
                    this, AuthUser.getToken(this), mAuthUser.getNetworkId());
        } catch (Exception e) {
            Log.e(TAG, "getMembers: " + e.getMessage());
            Toast.makeText(this, R.string.une_erreur_s_est_produite, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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
//        goToMe();
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

        fabPersonalGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetworksActivity.this, PersonalGoalsActivity.class));
            }
        });
    }

    public void goToMe() {
        layGoToSupervisor.setVisibility(View.VISIBLE);
        layGoToMe.setVisibility(View.GONE);

        if (me.getProfilePicture() != null) {
            if (!me.getProfilePicture().isEmpty())
                Glide.with(this).load(me.getProfilePicture()).into(imgProfil);
        }
        tvName.setText(me.getName().concat(" ").concat(me.getLastName()));
        tvNetworkLabel.setText("Réseau - " + me.getNetworkName());
        progressBar.setProgress(85);
        tvProgress.setText(String.valueOf(85).concat(" %"));
    }

    public void goToSupervisor() {

        NetworkMember superviseur = NetworkMember.getSupervisor(PharmaWine.mRealm);
        superviseur.load();

        if (superviseur != null) {
            if (superviseur.isValid()) {
                layGoToMe.setVisibility(View.VISIBLE);
                layGoToSupervisor.setVisibility(View.GONE);

                imgProfil.setImageResource(R.drawable.profil_sup);
                tvName.setText(superviseur.getName().concat(" ").concat(superviseur.getLastName()));
                tvNetworkLabel.setText(String.valueOf("Superviseur réseau - ").concat(superviseur.getNetworkName()));
                progressBar.setProgress(50);
                tvProgress.setText(String.valueOf(50).concat(" %"));
                if (superviseur.getProfilePicture() != null) {
                    if (!superviseur.getProfilePicture().isEmpty())
                        Glide.with(this).load(superviseur.getProfilePicture()).into(imgProfil);
                }
                return;
            }
        }

        Toast.makeText(this, "Aucun superviseur retrouvé !", Toast.LENGTH_SHORT).show();
    }

    private void initList() {

//        Init the adapter
        networkMemberAdapter = new NetworkMemberAdapter(this, networkMemberList);

//        Set the adapter to recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(networkMemberAdapter);

//        Get the member's list
//        getNetworkMembers();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNetworkMembers();
            }
        });
    }

    private void getNetworkMembers() {
        networkMemberList.clear();
        networkMemberList.addAll(NetworkMember.getAll(PharmaWine.mRealm));
        notifyChanges();

//        Get the fresh member's list if not loaded & connected

        if (!isMembersLoaded) {

            if (PharmaWine.getInstance().isOnline()) {

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<NetworkMembersResponse> call = apiService.getNetworkMembers(me.getNetworkId(), ApiClient.TOKEN_TYPE + prefManager.getToken());

                mSwipeRefreshLayout.setRefreshing(true);
                mSwipeRefreshLayout.setColorSchemeResources(R.color.orange);
                call.enqueue(new Callback<NetworkMembersResponse>() {
                    @Override
                    public void onResponse(Call<NetworkMembersResponse> call, Response<NetworkMembersResponse> response) {

                        if (!response.isSuccessful() || response.body() == null) {
                            notifyChanges();
                            Toast.makeText(NetworksActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                        } else {

                            try {

                                if (response.body().getStatusCode() == 200 || response.body().getStatusCode() == 404) {

                                    if (response.body().getNetworkMembers() == null || response.body().getStatusCode() == 404) {
                                        Snackbar.make(mSwipeRefreshLayout, getResources().getString(R.string.no_data_returned), Snackbar.LENGTH_LONG).show();
                                    } else {

                                        NetworkMember.saveAll(PharmaWine.mRealm, response.body().getNetworkMembers());
                                        networkMemberList.clear();
                                        networkMemberList.addAll(response.body().getNetworkMembers());
                                        isMembersLoaded = true;

                                        //        Notify changes after 3 seconds
                                        mSwipeRefreshLayout.setColorSchemeResources(R.color.green);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                notifyChanges();
                                            }
                                        }, 3000);
                                    }
                                } else {
//                                Toast.makeText(NetworksActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                                    Snackbar.make(mSwipeRefreshLayout, getResources().getString(R.string.no_data_returned), Snackbar.LENGTH_LONG).show();
                                    notifyChanges();
                                }

                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NetworkMembersResponse> call, Throwable t) {
//                    Toast.makeText(mContext,"Echec de la requete", Toast.LENGTH_SHORT).show();
                        notifyChanges();
                        Toast.makeText(NetworksActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(this, getResources().getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            }
        } else {
//            Toast.makeText(mContext, "Liste à jour !", Toast.LENGTH_SHORT).show();
            if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void notifyChanges() {
        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        networkMemberAdapter.notifyDataSetChanged();
    }

    // ======================================= CALLBACKS =======================================


    @Override
    public void onNetworkMembersResponse(@Nullable List<SimpleUser> networkMembers) {
        Log.i(TAG, "onNetworkMembersResponse: Members fetched");
        Toast.makeText(this, "Membres recuperes avec succes", Toast.LENGTH_SHORT).show();
        if (networkMembers.isEmpty()) {
            Toast.makeText(this, "Aucun membre trouvé dans votre reseau", Toast.LENGTH_SHORT).show();
        }

        for (SimpleUser s : networkMembers) {
            mNetworkMembers.add(s);

            // todo: notify adapter
        }

    }

    @Override
    public void onNetworkMembersFailure() {
        Toast.makeText(this, "Echec de la requete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkDetailsResponse(@Nullable Network network) {

    }

    @Override
    public void onNetworkDetailsFailure() {
        Toast.makeText(this, "Echec de la requete", Toast.LENGTH_SHORT).show();
    }
}
