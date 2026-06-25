package com.example.mykingdom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private EditText etEmail, etPhone, etAddress;
    private Button btnSave, btnExit; // Thêm biến btnExit
    private DatabaseHelper db;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Kiểm tra trạng thái đăng nhập
        if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_profile);

        // Khởi tạo DatabaseHelper
        db = new DatabaseHelper(this);

        // Ánh xạ View
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);
        btnExit = findViewById(R.id.btnExit); // Ánh xạ nút Thoát

        // Load thông tin hiện tại
        loadUserInfo();

        // Xử lý nút lưu
        btnSave.setOnClickListener(v -> saveUserInfo());

        // Xử lý nút thoát
        btnExit.setOnClickListener(v -> finish()); // Đóng màn hình Profile
    }

    private void loadUserInfo() {
        User user = db.getUserInfo();
        if (user != null) {
            etEmail.setText(user.getEmail());
            etPhone.setText(user.getPhone());
            etAddress.setText(user.getAddress());
        }
    }

    private void saveUserInfo() {
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật Database (Lưu ý: đảm bảo ID user khớp với dữ liệu thật)
        boolean isUpdated = db.updateUserInfo(1, email, phone, address);
        if (isUpdated) {
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            loadUserInfo(); // Làm mới giao diện
        } else {
            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}