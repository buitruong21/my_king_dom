package com.example.mykingdom;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    private RecyclerView recyclerView;
    private ToyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadToysFromDatabase();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // CẬP NHẬT LOGIC CHUYỂN MÀN HÌNH
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_products) {
                // Đang ở trang chủ
                return true;
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            } else if (id == R.id.nav_admin) {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                return true;
            } else if (id == R.id.nav_profile) { // Tên ID này phải khớp trong file menu.xml
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadToysFromDatabase();
    }

    private void loadToysFromDatabase() {
        List<Toy> toyList = db.getAllToys();
        if (toyList != null) {
            adapter = new ToyAdapter(toyList);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(toy -> {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("toy_object", toy);
                startActivity(intent);
            });
        }
    }
}