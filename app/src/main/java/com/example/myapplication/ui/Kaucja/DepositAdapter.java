package com.example.myapplication.ui.Kaucja;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;
import java.util.Locale;

public class DepositAdapter extends RecyclerView.Adapter<DepositAdapter.DepositViewHolder> {

    private List<Deposit> depositList;
    private OnDepositInteractionListener listener;

    public interface OnDepositInteractionListener {
        void onEditDeposit(Deposit deposit);
        void onDeleteDeposit(Deposit deposit);
    }

    public DepositAdapter(List<Deposit> depositList, OnDepositInteractionListener listener) {
        this.depositList = depositList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DepositViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deposit_item, parent, false);
        return new DepositViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DepositViewHolder holder, int position) {
        Deposit currentDeposit = depositList.get(position);
        holder.packagingType.setText(currentDeposit.getPackagingType());
        holder.depositValue.setText(String.format(Locale.getDefault(), "%.2f zł", currentDeposit.getDepositValue()));
        holder.barcode.setText(currentDeposit.getBarcode());

        holder.editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditDeposit(currentDeposit);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Potwierdź usunięcie")
                    .setMessage("Czy na pewno chcesz usunąć tę kaucję?")
                    .setPositiveButton("Tak", (dialog, which) -> {
                        if (listener != null) {
                            listener.onDeleteDeposit(currentDeposit);
                        }
                    })
                    .setNegativeButton("Nie", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return depositList.size();
    }

    public void setDeposits(List<Deposit> deposits) {
        this.depositList = deposits;
        notifyDataSetChanged();
    }

    static class DepositViewHolder extends RecyclerView.ViewHolder {
        private final TextView packagingType;
        private final TextView depositValue;
        private final TextView barcode;
        private final ImageButton editButton;
        private final ImageButton deleteButton;

        public DepositViewHolder(@NonNull View itemView) {
            super(itemView);
            packagingType = itemView.findViewById(R.id.deposit_packaging_type);
            depositValue = itemView.findViewById(R.id.deposit_value);
            barcode = itemView.findViewById(R.id.deposit_barcode);
            editButton = itemView.findViewById(R.id.edit_deposit_button);
            deleteButton = itemView.findViewById(R.id.delete_deposit_button);
        }
    }
}