package com.jmaplus.pharmawine.fragments.productCategory;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.ProductCategoryActivity;
import com.jmaplus.pharmawine.activities.ProductsActivity;
import com.jmaplus.pharmawine.adapters.ProductAdapter;
import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.jmaplus.pharmawine.models.Product;
import com.jmaplus.pharmawine.services.ApiClient;
import com.jmaplus.pharmawine.services.ApiInterface;
import com.jmaplus.pharmawine.services.responses.ProductsResponse;
import com.jmaplus.pharmawine.utils.PrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaboratoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;

    private ArrayList<Product> productList;
    private ProductCategoryActivity mContext;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PrefManager prefManager;

    public LaboratoriesFragment() {
        // Required empty public constructor
        productList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_laboratories, container, false);

        recyclerView = view.findViewById(R.id.rv_products_lab);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_products_lab);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = (ProductCategoryActivity) getActivity();
        prefManager = new PrefManager(mContext);

//        Set the adapter
        productAdapter = new ProductAdapter(productList, mContext, ProductAdapter.LABORATORY);


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

//        Get the fresh product's list if not loaded & connected
        mSwipeRefreshLayout.setRefreshing(true);
        productList.clear();

        productList.addAll(Product.getAllByCategory(PharmaWine.mRealm, mContext.selectedProductCategory));
        if(mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);

        notifyChanges();
    }

    private void notifyChanges() {
        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        productAdapter.notifyDataSetChanged();

        if(mContext.selectedProductCategory.isEmpty() || productList.isEmpty()) {
            Snackbar.make(mSwipeRefreshLayout, getResources().getString(R.string.no_data_returned), Snackbar.LENGTH_LONG).show();
        }

        mContext.updateBottomView(R.drawable.pill, mContext.getResources().getString(R.string.products_number).replace("%", String.valueOf(productList.size())));
    }

    public void search(String query) {
        ArrayList<Product> models = productList;
        ArrayList<Product> filteredModelList = new ArrayList<>();

        if (!query.isEmpty()) {
            query = query.toLowerCase();
            for (Product model : models) {
                final String text = model.getName().toLowerCase();
                final String text2 = model.getLaboratory().toLowerCase();
                if (text.contains(query) || text2.contains(query)) {
                    filteredModelList.add(model);
                }
            }
        } else {
            filteredModelList = models;
        }
        productAdapter = new ProductAdapter(filteredModelList, mContext, ProductAdapter.LABORATORY);
        recyclerView.setAdapter(productAdapter);

//        Mettre à jour le nombre de produits total
        mContext.updateBottomView(R.drawable.pill, mContext.getResources().getString(R.string.products_number).replace("%", String.valueOf(filteredModelList.size())));
    }
}
