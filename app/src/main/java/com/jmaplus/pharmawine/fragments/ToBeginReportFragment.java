package com.jmaplus.pharmawine.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jmaplus.pharmawine.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToBeginReportFragment extends Fragment {

    private CircleImageView profileImage;
    private TextView tvNomPrenom, tvTypeClient, tvCategoryClient;
    private Button btnVisiteEnd;
    private Fragment mFragment = null;


    public ToBeginReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_begin_report, container, false);
        profileImage = view.findViewById(R.id.img_profil_client);
        tvNomPrenom = view.findViewById(R.id.tv_nom_client);
        tvTypeClient = view.findViewById(R.id.tv_type_client);
        tvCategoryClient = view.findViewById(R.id.tv_category_client);
        btnVisiteEnd = view.findViewById(R.id.btn_visit_fini);

        initView();

        return view;
    }


    private void initView() {
        /*
         *TODO populate view by current select client
         */

//        btnVisiteEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                confirmationDialog();
//            }
//
//
//        });
    }

//    private void confirmationDialog() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("");
//        builder.setMessage(R.string.msg_confim_visite_end);
//        builder.setCancelable(false);
//
//        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mFragment = new ReportsFragment();
//                switchFragment(mFragment);
//            }
//        });
//
//        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//
//        });
//
//        builder.show();
//    }

//    private void switchFragment(Fragment fragment) {
//        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();
//        mFragmentTransaction.replace(R.id.frame_container_visite, fragment);
//        mFragmentTransaction.commit();
//        mFragment = fragment;
//
//    }


}




