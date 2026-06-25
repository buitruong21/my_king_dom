package com.example.mykingdom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Tên bảng và các cột
    public static final String TABLE_TOYS = "toys";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PRICE = "price";
    public static final String COL_IMAGE = "image_name";

    public static final String TABLE_CART = "cart_table";
    public static final String TABLE_USERS = "users";

    public DatabaseHelper(Context context) {
        super(context, "ToyStore.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng sản phẩm
        db.execSQL("CREATE TABLE " + TABLE_TOYS + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT, " + COL_PRICE + " REAL, " + COL_IMAGE + " TEXT)");

        // Tạo bảng giỏ hàng
        db.execSQL("CREATE TABLE " + TABLE_CART + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price REAL, imageName TEXT)");

        // Tạo bảng người dùng
        db.execSQL("CREATE TABLE " + TABLE_USERS + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT, "
                + "password TEXT, "
                + "email TEXT, "
                + "phone TEXT, "
                + "address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // --- QUẢN LÝ SẢN PHẨM ---
    public void addToy(String name, double price, String imageName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_PRICE, price);
        values.put(COL_IMAGE, imageName);
        db.insert(TABLE_TOYS, null, values);
        db.close();
    }

    public List<Toy> getAllToys() {
        List<Toy> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TOYS, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Toy(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // Hàm xóa sản phẩm cho AdminAdapter
    public void deleteToy(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOYS, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // --- QUẢN LÝ GIỎ HÀNG ---
    public boolean addToCart(Toy toy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", toy.getName());
        values.put("price", toy.getPrice());
        values.put("imageName", toy.getImageName());
        long result = db.insert(TABLE_CART, null, values);
        db.close();
        return result != -1;
    }

    // Hàm lấy giỏ hàng cho CartActivity
    public List<Toy> getCartItems() {
        List<Toy> cartList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART, null);
        if (cursor.moveToFirst()) {
            do {
                cartList.add(new Toy(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartList;
    }

    // Hàm xóa giỏ hàng cho CartAdapter
    public void deleteCartItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // --- QUẢN LÝ NGƯỜI DÙNG & XÁC THỰC ---
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, "username=? AND password=?",
                new String[]{username, password}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public User getUserInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, "id=?", new String[]{"1"}, null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
            cursor.close();
        }
        db.close();
        return user;
    }

    // Trong DatabaseHelper.java
    public boolean updateUserInfo(int id, String email, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("phone", phone);
        values.put("address", address);

        // update trả về số dòng bị ảnh hưởng, > 0 nghĩa là thành công
        long result = db.update("users", values, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

}