package com.example.mykingdom;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // Nút quay lại
        Button btnBack = findViewById(R.id.btnBackToShop);
        btnBack.setOnClickListener(v -> finish());

        // Nút thanh toán
        Button btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            Toast.makeText(this, "Đang xử lý thanh toán...", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 1. Khởi tạo DatabaseHelper
        DatabaseHelper db = new DatabaseHelper(this);

        // 2. Lấy danh sách sản phẩm trong giỏ hàng
        List<Toy> cartItems = db.getCartItems();

        // 3. Gán vào Adapter và thiết lập cho RecyclerView
        // Lưu ý: Nếu bạn có CartAdapter riêng thì dùng nó ở đây
        CartAdapter adapter = new CartAdapter(cartItems);
        rvCart.setAdapter(adapter);
    }
}