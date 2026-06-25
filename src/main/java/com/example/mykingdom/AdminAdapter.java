package com.example.mykingdom;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    private List<Toy> toys;

    public AdminAdapter(List<Toy> toys) {
        this.toys = toys;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_toy, parent, false);
        return new AdminViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        Toy toy = toys.get(position);
        holder.tvName.setText(toy.getName());
        holder.tvPrice.setText(String.format("%,d VNĐ", (int) toy.getPrice()));

        // 1. Hiển thị ảnh từ drawable
        int imgId = holder.itemView.getContext().getResources().getIdentifier(
                toy.getImageName(), "drawable", holder.itemView.getContext().getPackageName());
        if (imgId != 0) holder.imgToy.setImageResource(imgId);
        else holder.imgToy.setImageResource(R.drawable.ic_launcher_foreground);

        // 2. ẨN nút "Thêm vào giỏ" trong màn hình Admin
        holder.btnAddToCart.setVisibility(View.GONE);

        // 3. Nút Xóa
        holder.btnDelete.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(v.getContext());
            db.deleteToy(toy.getId());
            toys.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, toys.size());
        });

        // 4. Nút Sửa
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddProductActivity.class);
            intent.putExtra("TOY_ID", toy.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return toys != null ? toys.size() : 0;
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgToy;
        Button btnDelete, btnEdit, btnAddToCart; // Khai báo thêm btnAddToCart

        public AdminViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvPrice = v.findViewById(R.id.tvPrice);
            imgToy = v.findViewById(R.id.imgToy);
            btnDelete = v.findViewById(R.id.btnDelete);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnAddToCart = v.findViewById(R.id.btnAddToCart); // Ánh xạ
        }
    }
}