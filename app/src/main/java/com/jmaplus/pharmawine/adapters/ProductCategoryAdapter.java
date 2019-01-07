package com.jmaplus.pharmawine.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.ProductCategoryActivity;
import com.jmaplus.pharmawine.models.ProductCategory;

import java.util.ArrayList;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {

    private final ArrayList<ProductCategory> productCategoryList;
    private Context mContext;

    public ProductCategoryAdapter(ArrayList<ProductCategory> productCategories, Context context) {
        super();
        this.mContext = context;
        this.productCategoryList = productCategories;
    }

    @NonNull
    @Override
    public ProductCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_1_column_item, parent, false);
        return new ProductCategoryAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ProductCategoryAdapter.ViewHolder holder, final int position) {
        holder.tvCenter.setText(productCategoryList.get(position).getLabel());
    }

    @Override
    public int getItemCount() {
        return this.productCategoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvCenter;

        ViewHolder(View itemView) {
            super(itemView);
            tvCenter = itemView.findViewById(R.id.tv_center);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    ProductCategory productCategory = productCategoryList.get(position);
                    productCategory.load();

                    if (productCategory.isValid()) {
                        mContext.startActivity(new Intent(mContext, ProductCategoryActivity.class).putExtra(ProductCategoryActivity.PRODUCT_CATEGORY_KEY, productCategory.getLabel()));
                    }
                }
            });
        }

    }
}
