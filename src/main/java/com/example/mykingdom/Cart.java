package com.example.mykingdom;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static List<Toy> cartItems = new ArrayList<>();

    public static void addToCart(Toy toy) {
        cartItems.add(toy);
    }

    public static List<Toy> getCartItems() {
        return cartItems;
    }
}