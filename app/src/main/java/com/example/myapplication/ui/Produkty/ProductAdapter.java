package com.example.myapplication.ui.Produkty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = productList.get(position);
        holder.name.setText(currentProduct.getName());
        holder.price.setText(String.format(Locale.getDefault(), "%.2f zł", currentProduct.getPrice()));
        holder.category.setText(currentProduct.getCategory());
        holder.expiryDate.setText(currentProduct.getExpiryDate());

        // Highlight expired products
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date expiry = sdf.parse(currentProduct.getExpiryDate());
            if (expiry != null && expiry.before(new Date())) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFCDD2")); // Light pink/red
            } else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("name", currentProduct.getName());
            intent.putExtra("price", currentProduct.getPrice());
            intent.putExtra("category", currentProduct.getCategory());
            intent.putExtra("expiryDate", currentProduct.getExpiryDate());
            intent.putExtra("description", currentProduct.getDescription());
            intent.putExtra("shop", currentProduct.getShop());
            intent.putExtra("purchaseDate", currentProduct.getPurchaseDate());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProducts(List<Product> products) {
        this.productList = products;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView price;
        private final TextView category;
        private final TextView expiryDate;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            category = itemView.findViewById(R.id.product_category);
            expiryDate = itemView.findViewById(R.id.product_expiry_date);
        }
    }
}
