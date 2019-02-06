package com.jmaplus.pharmawine.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.ApiProduct;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final List<ApiProduct> productList;
    private Context mContext;
    public static final Integer LABORATORY = 1, REFERENCE = 2;
    private Integer listType;

    public ProductAdapter(List<ApiProduct> products, Context context, @NonNull Integer listType) {
        this.mContext = context;
        this.productList = products;
        this.listType = listType;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_2_column_item, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ViewHolder holder, final int position) {
        final ApiProduct product = productList.get(position);

        try {
            holder.tvLeft.setText(product.getName());
            holder.tvRight.setText(product.getLaboratory().getName());

//            if (this.listType.equals(LABORATORY)) {
//                holder.tvRight.setText(product.getLaboratory().getName());
//
//            } else if (this.listType.equals(REFERENCE)) {
//                holder.tvRight.setText(product.getReference());
//            }
        } catch (Exception e) {
            Log.e(getClass().getName(), "onBindViewHolder: Une erreur s'est produite ==> " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvLeft, tvRight;

        ViewHolder(View itemView) {
            super(itemView);
            tvLeft = itemView.findViewById(R.id.tv_left);
            tvRight = itemView.findViewById(R.id.tv_right);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if(listType.equals(LABORATORY)) {
//                        int position = getAdapterPosition();
//                        ApiProduct p = productList.get(position);
//
//                        final Dialog dialog = new Dialog(v.getContext());
//                        dialog.setContentView(R.layout.product_info_dialog);
//                        dialog.setCancelable(true);
//
//                        TextView tvName, tvCategory, tvLaboratory, tvGlobalPrice, tvPc, tvPp;
//
//                        tvName = dialog.findViewById(R.id.tv_product_name);
//                        tvCategory = dialog.findViewById(R.id.tv_product_category);
//                        tvLaboratory = dialog.findViewById(R.id.tv_product_laboratory);
//
//                        tvGlobalPrice = dialog.findViewById(R.id.tv_global_price_ht);
//                        tvPc = dialog.findViewById(R.id.tv_pc_price);
//                        tvPp = dialog.findViewById(R.id.tv_pp_price);
//
//                        try {
//                            tvName.setText(p.getName());
////                            tvCategory.setText(p.getCategory());
//                            tvLaboratory.setText(p.getLaboratory().getName());
//                            tvGlobalPrice.setText(String.valueOf(p.getPrixGht()));
//                            tvPc.setText(String.valueOf(p.getPrixCession()));
//                            tvPp.setText(String.valueOf(p.getPrixPublic()));
//
//                            dialog.show();
//                        } catch (Exception e) {
//                            Log.e(getClass().getName(), "onClick: " + e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
                }
            });
        }
    }
}
