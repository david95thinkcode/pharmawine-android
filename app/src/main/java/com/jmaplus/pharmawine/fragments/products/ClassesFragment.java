package com.jmaplus.pharmawine.fragments.products;


import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.ProductsActivity;
import com.jmaplus.pharmawine.adapters.ProductCategoryAdapter;
import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.jmaplus.pharmawine.models.Product;
import com.jmaplus.pharmawine.models.ProductCategory;
import com.jmaplus.pharmawine.services.ApiClient;
import com.jmaplus.pharmawine.services.ApiInterface;
import com.jmaplus.pharmawine.services.responses.ProductCategoriesResponse;
import com.jmaplus.pharmawine.utils.FakeData;
import com.jmaplus.pharmawine.utils.PrefManager;

import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassesFragment extends Fragment {

    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;

    private ArrayList<ProductCategory> productCategoryList;
    private ProductsActivity mContext;
    private ProductCategoryAdapter productCategoryAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PrefManager prefManager;


    public ClassesFragment() {
        // Required empty public constructor
        productCategoryList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_classes, container, false);

        recyclerView = view.findViewById(R.id.rv_products_classes);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_products_classes);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = (ProductsActivity) getActivity();
        prefManager = new PrefManager(mContext);


//        Set the adapter
        productCategoryAdapter = new ProductCategoryAdapter(productCategoryList, mContext);


//        Set the adapter to recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(productCategoryAdapter);

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

        productCategoryList.clear();
        productCategoryList.addAll(ProductCategory.getAll(PharmaWine.mRealm));
        notifyChanges();

//        Get the fresh product's list if not loaded & connected

        if (!mContext.isProdClassesLoaded) {
            if (PharmaWine.getInstance().isOnline()) {

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ProductCategoriesResponse> call = apiService.getDelegateProductCategories(AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm).getId(), ApiClient.TOKEN_TYPE + prefManager.getToken());

                mSwipeRefreshLayout.setRefreshing(true);
                mSwipeRefreshLayout.setColorSchemeResources(R.color.orange);

                call.enqueue(new Callback<ProductCategoriesResponse>() {
                    @Override
                    public void onResponse(Call<ProductCategoriesResponse> call, Response<ProductCategoriesResponse> response) {

                        if (!response.isSuccessful() || response.body() == null) {
                            Toast.makeText(mContext, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                        } else {
                            if (response.body().getStatusCode() == 200 || response.body().getStatusCode() == 404) {

                                if (response.body().getProductCategories() == null || response.body().getStatusCode() == 404) {
                                    Snackbar.make(mSwipeRefreshLayout, getResources().getString(R.string.no_data_returned), Snackbar.LENGTH_LONG).show();
                                } else {
                                    ProductCategory.saveAll(PharmaWine.mRealm, response.body().getProductCategories());
                                    productCategoryList.clear();
                                    productCategoryList.addAll(response.body().getProductCategories());
                                    mContext.isProdClassesLoaded = true;

                                    mSwipeRefreshLayout.setColorSchemeResources(R.color.green);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //        Notify changes
                                            notifyChanges();
                                        }
                                    }, 3000);
                                }
                            } else {
                                notifyChanges();
                                Toast.makeText(mContext, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductCategoriesResponse> call, Throwable t) {
//                    Toast.makeText(mContext,"Echec de la requete", Toast.LENGTH_SHORT).show();
                        notifyChanges();
                        Toast.makeText(mContext, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            }
        } else {
//            Toast.makeText(mContext, "Liste à jour !", Toast.LENGTH_SHORT).show();
            if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void notifyChanges() {

        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        productCategoryAdapter.notifyDataSetChanged();

        mContext.updateBottomView(R.drawable.format_list_bulleted, mContext.getResources().getString(R.string.product_classes_number).replace("%", String.valueOf(productCategoryList.size())));
    }

    public void search(String query) {
        ArrayList<ProductCategory> models = productCategoryList;
        ArrayList<ProductCategory> filteredModelList = new ArrayList<>();

        if (!query.isEmpty()) {
            query = query.toLowerCase();
            for (ProductCategory model : models) {
                final String text = model.getLabel().toLowerCase();
                if (text.contains(query)) {
                    filteredModelList.add(model);
                }
            }
        } else {
            filteredModelList = models;
        }
        productCategoryAdapter = new ProductCategoryAdapter(filteredModelList, mContext);
        recyclerView.setAdapter(productCategoryAdapter);

//        Mettre à jour le nombre de catégories de produits total
        mContext.updateBottomView(R.drawable.format_list_bulleted, mContext.getResources().getString(R.string.product_classes_number).replace("%", String.valueOf(filteredModelList.size())));
    }
}
