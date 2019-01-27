package com.jmaplus.pharmawine.fragments.products;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.ProductsActivity;
import com.jmaplus.pharmawine.adapters.ProductAdapter;
import com.jmaplus.pharmawine.models.ApiProduct;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReferencesFragment extends Fragment {


    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;

    private ArrayList<ApiProduct> productList;
    private ProductsActivity mContext;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public ReferencesFragment() {
        // Required empty public constructor
        mContext = (ProductsActivity) getActivity();
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, mContext, ProductAdapter.REFERENCE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_references, container, false);

        recyclerView = view.findViewById(R.id.rv_products_ref);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_products_ref);


        getProductList();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        Set the adapter to recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(productAdapter);

//        Get the product's list
        getProductList();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProductList();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_POSITION, recyclerView.getVerticalScrollbarPosition());
        super.onSaveInstanceState(outState);
    }

    private void getProductList() {

        mSwipeRefreshLayout.setRefreshing(true);
        productList.clear();
//
//        productList.addAll(Product.getAll(PharmaWine.mRealm));
//        if(mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
//
//        productAdapter.notifyDataSetChanged();
    }

    public void search(String query) {
        ArrayList<ApiProduct> models = productList;
        ArrayList<ApiProduct> filteredModelList = new ArrayList<>();

        if (!query.isEmpty()) {
            query = query.toLowerCase();
            for (ApiProduct model : models) {
                final String text = model.getName().toLowerCase();
                final String text2 = model.getReference().toLowerCase();
                if (text.contains(query) || text2.contains(query)) {
                    filteredModelList.add(model);
                }
            }
        } else {
            filteredModelList = models;
        }
        productAdapter = new ProductAdapter(filteredModelList, mContext, ProductAdapter.REFERENCE);
        recyclerView.setAdapter(productAdapter);
    }
}
