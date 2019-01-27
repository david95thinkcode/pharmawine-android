package com.jmaplus.pharmawine.fragments.products;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.ProductAdapter;
import com.jmaplus.pharmawine.models.ApiProduct;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.AuthUserResponse;
import com.jmaplus.pharmawine.utils.PrefManager;
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
    private ArrayList<ApiProduct> productList;
    private Context mContext;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PrefManager prefManager;

    public LaboratoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_laboratories, container, false);

        mContext = requireContext();
        prefManager = new PrefManager(mContext);
        mProductsList = new ArrayList();
        productList = new ArrayList();
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

        AuthCalls.getAuthedUserProduct(this, AuthUser.getToken(requireContext()));

//        productList.clear();
//        productList.addAll(Product.getAll(PharmaWine.mRealm));
//        notifyChanges();
//
////        Get the fresh product's list if not loaded & connected
//
//        if (!mContext.isProductsLoaded) {
//
//            if (PharmaWine.getInstance().isOnline()) {
//
//                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//                Call<ProductsResponse> call = apiService.getDelegateProducts(AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm).getId(), ApiClient.TOKEN_TYPE + prefManager.getToken());
//
//                mSwipeRefreshLayout.setRefreshing(true);
//                mSwipeRefreshLayout.setColorSchemeResources(R.color.orange);
//                call.enqueue(new Callback<ProductsResponse>() {
//                    @Override
//                    public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
//
//                        if (!response.isSuccessful() || response.body() == null) {
//                            notifyChanges();
//                            Toast.makeText(mContext, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
//                        } else {
//                            if (response.body().getStatusCode() == 200 || response.body().getStatusCode() == 404) {
//
//                                if (response.body().getProducts() == null || response.body().getStatusCode() == 404) {
//                                    Snackbar.make(mSwipeRefreshLayout, getResources().getString(R.string.no_data_returned), Snackbar.LENGTH_LONG).show();
//                                } else {
//                                    Product.saveAll(PharmaWine.mRealm, response.body().getProducts());
//                                    productList.clear();
//                                    productList.addAll(response.body().getProducts());
//                                    mContext.isProductsLoaded = true;
//
//                                    //        Notify changes after 3 seconds
//                                    mSwipeRefreshLayout.setColorSchemeResources(R.color.green);
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                           notifyChanges();
//                                        }
//                                    }, 3000);
//                                }
//                            } else {
//                                Toast.makeText(mContext, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
//                                Snackbar.make(mSwipeRefreshLayout, getResources().getString(R.string.no_data_returned), Snackbar.LENGTH_LONG).show();
//                                notifyChanges();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ProductsResponse> call, Throwable t) {
////                    Toast.makeText(mContext,"Echec de la requete", Toast.LENGTH_SHORT).show();
//                        notifyChanges();
//                        Toast.makeText(mContext, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            } else {
//                Toast.makeText(mContext, mContext.getResources().getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
//            }
//        } else {
////            Toast.makeText(mContext, "Liste à jour !", Toast.LENGTH_SHORT).show();
//            if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
//        }
    }

    private void notifyChanges() {
        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        productAdapter.notifyDataSetChanged();


//        mContext.updateBottomView(R.drawable.pill, mContext.getResources().getString(R.string.products_number).replace("%", String.valueOf(productList.size())));
    }

    public void search(String query) {
        ArrayList<ApiProduct> models = productList;
        ArrayList<ApiProduct> filteredModelList = new ArrayList<>();

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
        }
        productAdapter = new ProductAdapter(filteredModelList, mContext, ProductAdapter.LABORATORY);
        recyclerView.setAdapter(productAdapter);

//        Mettre à jour le nombre de produits total
//        requireActivity().updateBottomView(
//                R.drawable.pill, mContext.getResources().getString(R.string.products_number).replace("%", String.valueOf(filteredModelList.size())));
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
                mListener.onProductNumberUpdated(mProductsList.size());
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
