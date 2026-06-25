package com.example.mykingdom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; // Đừng quên import Toast
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ToyAdapter extends RecyclerView.Adapter<ToyAdapter.ToyViewHolder> {
    private List<Toy> toys;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Toy toy);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ToyAdapter(List<Toy> toys) {
        this.toys = toys;
    }

    @NonNull
    @Override
    public ToyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_toy_user, parent, false);
        return new ToyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ToyViewHolder holder, int position) {
        Toy toy = toys.get(position);
        holder.tvName.setText(toy.getName());

        String formattedPrice = String.format("%,d VNĐ", Math.round(toy.getPrice()));
        holder.tvPrice.setText(formattedPrice);

        // Hiển thị ảnh
        int resID = toy.getImageResourceId(holder.itemView.getContext());
        if (resID != 0) {
            holder.imgToy.setImageResource(resID);
        } else {
            holder.imgToy.setImageResource(R.drawable.ic_launcher_foreground);
        }

        // Xử lý sự kiện "Thêm vào giỏ"
        holder.btnAddToCart.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(v.getContext());
            boolean success = db.addToCart(toy);

            if (success) {
                Toast.makeText(v.getContext(), "Đã thêm " + toy.getName() + " vào giỏ!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Sản phẩm đã có trong giỏ!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(toy);
        });
    }

    @Override
    public int getItemCount() {
        return toys != null ? toys.size() : 0;
    }

    public static class ToyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgToy;
        Button btnAddToCart;

        public ToyViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvPrice = v.findViewById(R.id.tvPrice);
            imgToy = v.findViewById(R.id.imgToy);
            btnAddToCart = v.findViewById(R.id.btnAddToCart);
        }
    }
}