package com.example.cakedream;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class cart_screen extends AppCompatActivity {
    ImageButton backbtn;
    AppCompatButton checkOutButton;
    LinearLayout withdata, noData;
    private final ArrayList<cart_product_data> cartProductData = new ArrayList<>();
    FirebaseFirestore firestore;
    String uid;
    private RecyclerView cartRecycleView;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_screen);
    }
}
