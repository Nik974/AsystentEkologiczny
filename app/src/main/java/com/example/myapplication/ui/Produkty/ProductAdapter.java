package com.example.myapplication.ui.Produkty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter dla RecyclerView, który wyświetla listę produktów.
 * Zarządza tworzeniem i wiązaniem widoków dla każdego elementu listy.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnProductDeleteListener deleteListener;
    private OnProductEditListener editListener;

    /**
     * Interfejs do obsługi zdarzenia usunięcia produktu.
     */
    public interface OnProductDeleteListener {
        void onProductDelete(Product product);
    }

    /**
     * Interfejs do obsługi zdarzenia edycji produktu.
     */
    public interface OnProductEditListener {
        void onProductEdit(Product product);
    }

    /**
     * Konstruktor adaptera.
     * @param productList Lista produktów do wyświetlenia.
     * @param deleteListener Listener do obsługi zdarzenia usunięcia.
     * @param editListener Listener do obsługi zdarzenia edycji.
     */
    public ProductAdapter(List<Product> productList, OnProductDeleteListener deleteListener, OnProductEditListener editListener) {
        this.productList = productList;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
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

        // Podświetlanie przeterminowanych produktów
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date expiry = sdf.parse(currentProduct.getExpiryDate());
            if (expiry != null && expiry.before(new Date())) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFCDD2")); // Jasnoróżowy/czerwony
            } else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        // Listener do otwierania szczegółów produktu
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getBindingAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Product product = productList.get(adapterPosition);
                Context context = v.getContext();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("category", product.getCategory());
                intent.putExtra("expiryDate", product.getExpiryDate());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("shop", product.getShop());
                intent.putExtra("purchaseDate", product.getPurchaseDate());
                context.startActivity(intent);
            }
        });

        // Listener do usuwania produktu
        holder.deleteButton.setOnClickListener(v -> {
            int adapterPosition = holder.getBindingAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                final Product productToDelete = productList.get(adapterPosition);
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Potwierdź usunięcie")
                        .setMessage("Czy na pewno chcesz usunąć ten produkt?")
                        .setPositiveButton("Tak", (dialog, which) -> {
                            if (deleteListener != null) {
                                deleteListener.onProductDelete(productToDelete);
                            }
                        })
                        .setNegativeButton("Nie", null)
                        .show();
            }
        });

        // Listener do edycji produktu
        holder.editButton.setOnClickListener(v -> {
            int adapterPosition = holder.getBindingAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                if (editListener != null) {
                    editListener.onProductEdit(productList.get(adapterPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * Ustawia nową listę produktów i odświeża widok.
     * @param products Nowa lista produktów.
     */
    public void setProducts(List<Product> products) {
        this.productList = products;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder przechowujący widoki dla pojedynczego elementu listy.
     */
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView price;
        private final TextView category;
        private final TextView expiryDate;
        private final ImageButton deleteButton;
        private final ImageButton editButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            category = itemView.findViewById(R.id.product_category);
            expiryDate = itemView.findViewById(R.id.product_expiry_date);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }
}
