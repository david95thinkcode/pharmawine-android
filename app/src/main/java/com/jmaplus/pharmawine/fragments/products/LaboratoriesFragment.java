package com.jmaplus.pharmawine.fragments.products;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.ProductAdapter;
import com.jmaplus.pharmawine.models.ApiProduct;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.AuthUserResponse;
import com.jmaplus.pharmawine.utils.RetrofitCalls.AuthCalls;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaboratoriesFragment extends Fragment implements AuthCalls.Callbacks {

    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private OnFragmentInteractionListener mListener;

    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;
    private AuthUser mAuthUser;

    private List<ApiProduct> mProductsList;
    private Context mContext;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public LaboratoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_laboratories, container, false);

        mContext = requireContext();
        mProductsList = new ArrayList();
        productAdapter = new ProductAdapter(mProductsList, mContext, ProductAdapter.LABORATORY);

        recyclerView = view.findViewById(R.id.rv_products_lab);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_products_lab);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProductList();
            }
        });

//        Set the adapter to recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(productAdapter);

        getProductList();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_POSITION, recyclerView.getVerticalScrollbarPosition());
        super.onSaveInstanceState(outState);
    }

    private void getProductList() {
        mSwipeRefreshLayout.setRefreshing(true);

        AuthCalls.getAuthedUserProduct(this, AuthUser.getToken(mContext));
    }

    public void search(String query) {

        try {
            List<ApiProduct> models = mProductsList;
            List<ApiProduct> filteredModelList = new ArrayList();

            if (!query.isEmpty()) {
                query = query.toLowerCase();
                for (ApiProduct model : models) {
                    final String text = model.getName().toLowerCase();
                    final String text2 = model.getLaboratory().getName().toLowerCase();
                    if (text.contains(query) || text2.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            } else {
                filteredModelList = models;
                productAdapter = new ProductAdapter(filteredModelList, mContext, ProductAdapter.LABORATORY);
                recyclerView.setAdapter(productAdapter);
            }
        } catch (NullPointerException e) {
            // TODO: il y a null exception ici ==> recyclerView.setAdapter(productAdapter);
//            Toast.makeText(requireContext(), "Non disponible", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "search: Il y a un probleme ==> " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
//            Toast.makeText(mContext, "Non disponible", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "search: Il y a un probleme ==> " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onLoginResponse(@javax.annotation.Nullable AuthUserResponse response) {

    }

    @Override
    public void onLoginWrongCredentialsResponse() {

    }

    @Override
    public void onLoginFailure() {

    }

    @Override
    public void onFetchingDetailsResponse(@javax.annotation.Nullable AuthUser response) {

    }

    @Override
    public void onFetchingDetailsFailure() {

    }

    @Override
    public void onAuthProductsResponse(@javax.annotation.Nullable List<ApiProduct> products) {

        if (!products.isEmpty()) {
            mProductsList.clear();
            productAdapter.notifyDataSetChanged();

            for (ApiProduct p : products) {
                mProductsList.add(p);
                productAdapter.notifyItemInserted(mProductsList.size() - 1);
                try {
                    mListener.onProductNumberUpdated(mProductsList.size());
                } catch (NullPointerException e) {
                    // est declenchee quand l'activite parente est fermee avant l'execution du code du fragment
                    Log.w(TAG, "onAuthProductsResponse: Activite parente fermee trop tot");
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(mContext, "Auncun produit", Toast.LENGTH_SHORT).show();
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAuthProductFailure() {
        Toast.makeText(mContext, R.string.une_erreur_s_est_produite, Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onProductNumberUpdated(Integer productsNumber);

    }
}
