package com.example.mykingdom;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText etUser, etPass;
    Button btnReg;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Hãy tạo layout này tương tự activity_login

        db = new DatabaseHelper(this);
        etUser = findViewById(R.id.etUserReg);
        etPass = findViewById(R.id.etPassReg);
        btnReg = findViewById(R.id.btnRegisterAction);

        btnReg.setOnClickListener(v -> {
            boolean success = db.registerUser(etUser.getText().toString(), etPass.getText().toString());
            if (success) {
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}