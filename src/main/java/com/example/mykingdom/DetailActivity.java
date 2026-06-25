package com.example.mykingdom;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private Toy currentToy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        currentToy = (Toy) getIntent().getSerializableExtra("toy_object");

        // Tìm các View
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);
        // Đây là dòng gây lỗi nếu XML chưa có ID này
        TextView tvDescription = findViewById(R.id.tvDetailDescription);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnAddToCart = findViewById(R.id.btnDetailAddToCart);

        if (currentToy != null) {
            tvName.setText(currentToy.getName());

            // Định dạng giá tiền
            String formattedPrice = String.format("%,d VNĐ", (int) currentToy.getPrice());
            tvPrice.setText("Giá: " + formattedPrice);

            // Hiển thị mô tả
            tvDescription.setText("Đây là sản phẩm " + currentToy.getName() + ". Chất lượng cao, phù hợp cho mọi lứa tuổi.");
        }

        btnBack.setOnClickListener(v -> finish());

        btnAddToCart.setOnClickListener(v -> {
            if (currentToy != null) {
                Cart.addToCart(currentToy);
                Toast.makeText(this, "Đã thêm vào giỏ!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}