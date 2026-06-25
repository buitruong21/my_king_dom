package com.example.mykingdom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText etUser, etPass;
    Button btnLogin, btnRegister;
    DatabaseHelper db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 1. Khởi tạo DatabaseHelper và SharedPreferences
        db = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // 2. Ánh xạ các View
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // 3. Xử lý sự kiện nút Đăng nhập
        btnLogin.setOnClickListener(v -> {
            String username = etUser.getText().toString().trim();
            String password = etPass.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (db.checkUser(username, password)) {
                // Lưu trạng thái đăng nhập
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply();

                // ĐIỀU HƯỚNG CHÍNH XÁC: Chuyển sang màn hình danh sách sản phẩm (MainActivity)
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish(); // Xóa màn hình Login khỏi stack để không quay lại được khi nhấn Back
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });

        // 4. Xử lý sự kiện nút Đăng ký
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}