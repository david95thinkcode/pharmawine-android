package com.jmaplus.pharmawine.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.Laboratory;
import com.jmaplus.pharmawine.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final ArrayList<Product> productList;
    private Context mContext;
    public static final Integer LABORATORY = 1, REFERENCE = 2;
    private Integer listType;

    public ProductAdapter(ArrayList<Product> products, Context context, @NonNull Integer listType) {
        super();
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
        final Product product = productList.get(position);
        product.load();

        if(product.isValid()) {
            holder.tvLeft.setText(product.getName());

            if (this.listType.equals(LABORATORY)) {
                holder.tvRight.setText(product.getLaboratory());

            } else if (this.listType.equals(REFERENCE)) {
                holder.tvRight.setText(product.getReference());
            }
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

                    if(listType.equals(LABORATORY)) {
                        int position = getAdapterPosition();
                        Product p = productList.get(position);
                        p.load();

                        final Dialog dialog = new Dialog(v.getContext());
                        dialog.setContentView(R.layout.product_info_dialog);
                        dialog.setCancelable(true);

                        TextView tvName, tvCategory, tvLaboratory, tvGlobalPrice, tvPc, tvPp;

                        tvName = dialog.findViewById(R.id.tv_product_name);
                        tvCategory = dialog.findViewById(R.id.tv_product_category);
                        tvLaboratory = dialog.findViewById(R.id.tv_product_laboratory);

                        tvGlobalPrice = dialog.findViewById(R.id.tv_global_price_ht);
                        tvPc = dialog.findViewById(R.id.tv_pc_price);
                        tvPp = dialog.findViewById(R.id.tv_pp_price);

                        if(p.isLoaded() && p.isValid()) {
                            tvName.setText(p.getName());
                            tvCategory.setText(p.getCategory());
                            tvLaboratory.setText(p.getLaboratory());
                            tvGlobalPrice.setText(String.valueOf(p.getPrice()));
                            tvPc.setText(String.valueOf(p.getPriceM()));
                            tvPp.setText(String.valueOf(p.getPriceP()));

                            dialog.show();
                        }
                    }
                }
            });
        }
    }
}
