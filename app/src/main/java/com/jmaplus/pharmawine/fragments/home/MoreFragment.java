package com.jmaplus.pharmawine.fragments.home;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.CalendarActivity;
import com.jmaplus.pharmawine.activities.InfosActivity;
import com.jmaplus.pharmawine.activities.LoginActivity;
import com.jmaplus.pharmawine.activities.NetworksActivity;
import com.jmaplus.pharmawine.activities.PlanningActivity;
import com.jmaplus.pharmawine.activities.ProductsActivity;
import com.jmaplus.pharmawine.activities.StatsActivity;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.PrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    private int userID;
    private Context mContext;
    private PrefManager prefManager;
    private SharedPreferences sp;

    private LinearLayout mPlanningCv;
    //    private LinearLayout mClientsCv;
    private LinearLayout mStatsCv;
    private LinearLayout mProductsCv;
    private LinearLayout mCalendarCv;
    private LinearLayout mNetworkCv;
    private LinearLayout mInfosCv;
    private LinearLayout mLogoutCv;
    private ProgressDialog dialog;


    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        prefManager = new PrefManager(mContext);

        sp = mContext.getSharedPreferences(Constants.F_PROFIL,
                Context.MODE_PRIVATE);
        userID = sp.getInt(Constants.SP_ID_KEY, -1);

        // imnportant
        dialog = new ProgressDialog(mContext);
        dialog.setCancelable(false);
        dialog.cancel();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_more, container, false);

        mPlanningCv = rootView.findViewById(R.id.cv_menu_planning);
        mStatsCv = rootView.findViewById(R.id.cv_menu_stats);
        mProductsCv = rootView.findViewById(R.id.cv_menu_products);
        mNetworkCv = rootView.findViewById(R.id.cv_menu_network);
        mCalendarCv = rootView.findViewById(R.id.cv_menu_cal);
        mInfosCv = rootView.findViewById(R.id.cv_menu_infos);
        mLogoutCv = rootView.findViewById(R.id.cv_menu_logout);
        //        mClientsCv = rootView.findViewById(R.id.cv_menu_clients);

        return rootView;
    }

    private void initViewsForDelegue() {

    }

    private void initViewsForSupervisor() {

    }

    private void initViewsForAdmin() {

    }

    private void disconnectUser() {

        dialog.setMessage("Deconnection en cours....");
        dialog.cancel();

        new AlertDialog.Builder(mContext)
                .setTitle("Confirmation")
                .setMessage("Êtes-vous sûr de vouloir vous déconnecter ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog.show();

                        final Boolean isUserDisconnected = AuthUser.deleteUserDatas(mContext);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (isUserDisconnected) {

                                    // Ui update
                                    dialog.cancel();
                                    Toast.makeText(mContext, "Déconnecté !", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    ((Activity) mContext).finish();

                                } else {
                                    Toast.makeText(mContext, "Nous rencontrons un probleme lors de la deconnecter", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 2000);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


    public void clicksHandler(View view) {
        switch (view.getId()) {
            case R.id.cv_menu_planning:
                startActivity(new Intent(mContext, PlanningActivity.class));
                break;
//            case R.id.cv_menu_clients :
//                startActivity(new Intent(mContext, ClientsActivity.class));
//                break;
            case R.id.cv_menu_stats:
                startActivity(new Intent(mContext, StatsActivity.class));
                break;
            case R.id.cv_menu_products:
                startActivity(new Intent(mContext, ProductsActivity.class));
                break;
            case R.id.cv_menu_cal:
                startActivity(new Intent(mContext, CalendarActivity.class));
                break;
            case R.id.cv_menu_network:
                startActivity(new Intent(mContext, NetworksActivity.class));
                break;
            case R.id.cv_menu_infos:
                startActivity(new Intent(mContext, InfosActivity.class));
                break;
            case R.id.cv_menu_logout:
                disconnectUser();
                break;
            default:
                // Nothing to do
                //...
                break;
        }
    }

}
