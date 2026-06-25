package com.example.mykingdom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView rvAdminList;
    private Button btnAddNew, btnExit; // Khai báo thêm nút Thoát
    private AdminAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // 1. Khởi tạo Database
        db = new DatabaseHelper(this);

        // 2. Ánh xạ View
        rvAdminList = findViewById(R.id.rvAdminList);
        btnAddNew = findViewById(R.id.btnAddNew);
        btnExit = findViewById(R.id.btnExit); // Ánh xạ nút Thoát

        // 3. Thiết lập RecyclerView
        rvAdminList.setLayoutManager(new LinearLayoutManager(this));

        // 4. Sự kiện nút "Thêm sản phẩm mới"
        btnAddNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddProductActivity.class);
            startActivity(intent);
        });

        // 5. Sự kiện nút "Thoát" (Quay về trang chủ User)
        btnExit.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, MainActivity.class);
            // Xóa sạch ngăn xếp Activity để tránh lỗi quay lại màn hình Admin
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Luôn load lại danh sách khi quay lại màn hình này
        loadProductList();
    }

    private void loadProductList() {
        List<Toy> list = db.getAllToys();
        adapter = new AdminAdapter(list);
        rvAdminList.setAdapter(adapter);
    }
}