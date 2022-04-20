package com.example.cakedream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class product_detail extends AppCompatActivity {

    TextView cakename, cakeprice, desc, calories, size, countview, addtocart,ck1;
    Toolbar toolbar;
    ImageButton add, remove;
    ImageView cakeimg, backbtn;
    FirebaseUser mfirebaseuser;
    String productid, cake_image;
    Float cake_price1;
    String uid;
    int count = 1;

    ArrayList<String> productIds = new ArrayList<>();
    ArrayList<String> cakeImages = new ArrayList<>();
    ArrayList<Float> cakeprices = new ArrayList<Float>();

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        product_detail.this.setTitle("");
        Intent intent = getIntent();
        productid = intent.getExtras().getString("ProductId");
        cake_image = intent.getExtras().getString("cake_image");
        cake_price1 = intent.getExtras().getFloat("cake_price1");


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            uid = mFirebaseUser.getUid();
        }else {
            uid = null;
        }
        XmlId();
        createId();

        add.setOnClickListener(view -> {
            count = count + 1;
            countview.setText("" + count);
        });

        remove.setOnClickListener(view -> {
            if (count > 1) {
                count = count - 1;
                countview.setText("" + count);
            }
        });
        addtocart.setOnClickListener(v -> addToCart());

        backbtn.setOnClickListener(view -> onBackPressed());
    }

    private void createId() {
        DocumentReference documentReference = firestore.collection("products").document(productid);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentReference1 = task.getResult();
                Log.d("documentReference1", String.valueOf(documentReference1));
                if (documentReference1.exists()) {
                    productData data = documentReference1.toObject(productData.class);
                    Log.d("data", String.valueOf(data));
                    cakename.setText(data.getName());
                    cakeprice.setText("$" + data.getPrice());
                    desc.setText(data.getDesc());
                    calories.setText(data.getCalories());
                    size.setText(data.getSize());
                    Picasso.get()
                            .load(data.getImage())
                            .placeholder(R.drawable.home_cake_img)
                            .error(R.drawable.home_cake_img)
                            .into(cakeimg);


                } else {
                    Log.d("", "" + task.getException());
                    Toast.makeText(this, "document Not Found" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("error", "get failed with database", task.getException());
                Toast.makeText(this, "failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Log.d("error", e.toString()));
    }

    private void XmlId() {

        backbtn = findViewById(R.id.back_button);

        cakeimg = findViewById(R.id.pre_made_cake_image);
        cakename = findViewById(R.id.product_name);
        desc = findViewById(R.id.cake_desc);

        cakeprice = findViewById(R.id.price1);
        calories = findViewById(R.id.calories1);
        size = findViewById(R.id.size1);

        countview = findViewById(R.id.count1);
        add = findViewById(R.id.plus);
        remove = findViewById(R.id.minus);

        addtocart = findViewById(R.id.cart);
    }
    public void addToCart(){
        String cake_name = (cakename).getText().toString();
        String cake_size = (size).getText().toString();
        String cake_qty = (countview).getText().toString();
        Log.d("qty ", cake_qty);
        String cake_price = (cakeprice).getText().toString();
        String cake_type = "pre-made cake";
        String price = String.valueOf(cake_price1);
        Log.d("price1",price);
        String notes = "";
        String flavour = "";
        String design = "";
        String shape =  "";
        String type = "";


        firestore.collection("users").document(uid).collection("cart").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if(Objects.requireNonNull(task.getResult()).size() > 0) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    cart_model data = document.toObject(cart_model.class);
                                    assert data != null;
                                    productIds.add(data.getProductid());
                                }
                            }
                            if(productIds.contains(productid)){
                                addProductToCart(cake_name,cake_size, cake_qty,cake_type,notes,flavour,design,shape,type);
                            }
                            else {
                                creatUser(cake_name,cake_size, cake_qty,cake_type,notes,flavour,design,shape,type);
                            }
                        } else {
                            creatUser(cake_name,cake_size, cake_qty,cake_type,notes,flavour,design,shape,type);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Task Fails to get cart products", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void creatUser(String cake_name, String cake_size, String cake_qty, String cake_type, String notes, String flavour, String design, String shape, String type) {

        DocumentReference documentReference = firestore.collection("users").document(uid).collection("cart").document(productid);
        Map<String, String> product = new HashMap<>();
        product.put("productid", productid);
        product.put("quantity", "" + count);
        product.put("name", cake_name);
        product.put("image",cake_image);
        product.put("price", String.valueOf(cake_price1));
        product.put("size", cake_size);
        product.put("caketype", cake_type);
        product.put("notes", notes);
        product.put("flavour", flavour);
        product.put("design",design);
        product.put("shape", shape);
        product.put("type",type);

        documentReference.set(product).addOnSuccessListener(unused -> {
            count = 0;
            countview.setText("" + count);
            Toast.makeText(getApplicationContext(), "Item Added successfully!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show());
    }




    private void addProductToCart(String cake_name, String cake_size,String cake_qty,String cake_type, String notes, String flavour, String design, String shape, String type) {
        Log.d("qty",cake_qty);
        int itemCountInt = Integer.parseInt(cake_qty);
        int updatedItemCount = itemCountInt + count;

        Map<String, Object> product = new HashMap<>();
        product.put("productid", productid);
        product.put("quantity", "" + updatedItemCount);
        product.put("name", cake_name);
        product.put("image",cake_image);
        product.put("price",String.valueOf(cake_price1));
        product.put("size", cake_size);
        product.put("caketype", cake_type);
        product.put("notes", notes);
        product.put("flavour", flavour);
        product.put("design",design);
        product.put("shape", shape);
        product.put("type",type);
        firestore.collection("users").document(uid).collection("cart").document(productid)
                .update(product)
                .addOnSuccessListener(unused -> {
                    count = 0;
                    countview.setText(""+count);
                    Toast.makeText(getApplicationContext(), "Item Added successfully!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show());

    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,pre_made_base.class);
        startActivity(intent);
    }
}
