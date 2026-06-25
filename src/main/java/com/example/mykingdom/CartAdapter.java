package com.example.mykingdom;

import android.content.Context; // Dòng này là thứ bạn đang thiếu
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Toy> cartItems;

    public CartAdapter(List<Toy> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_toy_user, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Toy toy = cartItems.get(position);
        holder.tvName.setText(toy.getName());
        holder.tvPrice.setText(String.format("%,d VNĐ", Math.round(toy.getPrice())));

        int resID = toy.getImageResourceId(holder.itemView.getContext());
        holder.imgToy.setImageResource(resID != 0 ? resID : R.drawable.ic_launcher_foreground);

        holder.btnAddToCart.setText("Xóa");
        holder.btnAddToCart.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(v.getContext());
            // Giả sử Toy có phương thức getId()
            db.deleteCartItem(toy.getId());
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
        });
    }

    @Override
    public int getItemCount() {
        return cartItems != null ? cartItems.size() : 0;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgToy;
        Button btnAddToCart;

        public CartViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvPrice = v.findViewById(R.id.tvPrice);
            imgToy = v.findViewById(R.id.imgToy);
            btnAddToCart = v.findViewById(R.id.btnAddToCart);
        }
    }
}