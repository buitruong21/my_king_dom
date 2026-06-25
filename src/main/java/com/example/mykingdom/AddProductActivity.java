package com.example.mykingdom;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {
    EditText etName, etPrice, etImage;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product); // Bạn cần tạo layout này

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etImage = findViewById(R.id.etImage);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String priceStr = etPrice.getText().toString();
            String image = etImage.getText().toString();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseHelper db = new DatabaseHelper(this);
            db.addToy(name, Double.parseDouble(priceStr), image);
            Toast.makeText(this, "Đã thêm thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng màn hình này, quay về AdminActivity
        });
    }
}