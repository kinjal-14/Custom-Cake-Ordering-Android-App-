package com.example.cakedream;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cart_screen extends AppCompatActivity {
    ImageButton backbtn;
    AppCompatButton checkOutButton;
    ArrayList<AddiProductData> addiProductItemData = new ArrayList<AddiProductData>();;
    ArrayList<cart_model> cart_product_data = new ArrayList<>();
    LinearLayoutManager linearLayoutManager,linearLayoutManager1;
    LinearLayout withdata, withoutdata;
    RecyclerView recyclerView, recyclerView1;
    cartAddiAdapter cartAddiAdapter;
    cartAdapter cartAdapter;
    FirebaseFirestore firestore;
    String uid;
    ImageView home_btn;
    double subtotal = 0.0;
    private RecyclerView cartRecycleView;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cart_screen);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            uid = mFirebaseUser.getUid();
        } else {
            uid = null;
        }

        withdata = findViewById(R.id.with_data);
        withoutdata = findViewById(R.id.no_item_found);

        recyclerView = findViewById(R.id.addi_product_slider);
        recyclerView1 = findViewById(R.id.cart_recyclerview);

        checkOutButton = findViewById(R.id.checkout_btn);

        home_btn= findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cart_screen.this,pre_made_base.class);
                startActivity(intent);
            }
        });


        setData();
     checkOutButton.setOnClickListener(view -> {
        Intent intent  = new Intent(getBaseContext(), shipping.class);
        intent.putExtra("SubTotal", subtotal);
              startActivity(intent);
           });



        setProductInformation();
    }


    private void setData() {
        firestore.collection("users").document(uid).collection("cart").get()
                                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                                if (!queryDocumentSnapshots.isEmpty()) {

                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                    for (DocumentSnapshot d : list) {
                                                        cart_model productData = d.toObject(cart_model.class);
                                                        assert productData != null;
                                                        String itemId = productData.getProductid();
                                                        Log.d("itemid",itemId);
                                                        double price = Double.valueOf(productData.getPrice());
                                                        double count = Double.valueOf(productData.getQuantity());
                                                        subtotal = subtotal + (price * count);
                                                        Log.d("total", String.valueOf(subtotal));
                                                        cart_product_data.add(productData);
                                                        Log.d("data", String.valueOf(cart_product_data));
                                                    }
                                                } else {
                                                    Toast.makeText(getBaseContext(), "No data found in Database", Toast.LENGTH_SHORT).show();

                                                }
                                            }).addOnFailureListener(e -> Toast.makeText(getBaseContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show())
                .addOnCompleteListener(task  -> setCartAdapter());

                }


    private void setCartAdapter() {
        cartAdapter = new cartAdapter(cart_product_data);
        recyclerView1.setAdapter(cartAdapter);
        recyclerView1.setHasFixedSize(true);
        linearLayoutManager1 = new LinearLayoutManager(cart_screen.this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(linearLayoutManager1);
    }


    private void setProductInformation() {
        firestore.collection("AddProducts").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()){
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list){
                            AddiProductData data = d.toObject(AddiProductData.class);
                            assert data != null;
                            addiProductItemData.add(data);
                        }
                    }else {
                        Toast.makeText(this, "products are not avalible for this time", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(this, "Opps!,Fail to fetch data", Toast.LENGTH_SHORT).show())
                .addOnCompleteListener(task -> setAdapter());
    }

    private void setAdapter() {
        cartAddiAdapter = new cartAddiAdapter(addiProductItemData);
        recyclerView.setAdapter(cartAddiAdapter);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(cart_screen.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void reloadActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}

