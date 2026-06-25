package com.example.mykingdom;

import android.content.Context;
import java.io.Serializable;

public class Toy implements Serializable {
    private int id;
    private String name;
    private double price;
    private String imageName;

    // Constructor đầy đủ
    public Toy(int id, String name, double price, String imageName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageName = imageName;
    }

    // Các Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    // Phương thức lấy ID tài nguyên ảnh
    public int getImageResourceId(Context context) {
        if (this.imageName == null || this.imageName.isEmpty()) {
            return 0;
        }
        return context.getResources().getIdentifier(
                this.getImageName(),
                "drawable",
                context.getPackageName()
        );
    }

    public class User {
        private int id;
        private String username, password, email, phone, address;

        // Constructor, Getters và Setters...
    }
}