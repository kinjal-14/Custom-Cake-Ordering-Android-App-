package com.example.cakedream;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Addi_Product_Detail extends AppCompatActivity {

    TextView productname, productprice, desc, countview, addtocart;
    Toolbar toolbar;
    ImageButton add, remove;
    ImageView productimg, backbtn;

    FirebaseUser mfirebaseuser;
    String uid;
    int count = 1;
    String productid, cake_image;
    String cake_price1;
    ArrayList<String> productIds = new ArrayList<>();
    ArrayList<String> cakeImages = new ArrayList<>();
    ArrayList<Float> cakeprices = new ArrayList<Float>();


    FirebaseFirestore firestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addiproductdetails);
        Intent intent = getIntent();
        productid = intent.getExtras().getString("ProductId1");
        cake_image = intent.getExtras().getString("Productimg");
        cake_price1 = intent.getExtras().getString("ProductPrice");
        Log.d("id",productid);


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



        addtocart.setOnClickListener(v -> addToCart());

        backbtn.setOnClickListener(view -> onBackPressed());
    }

    private void addToCart() {
        String cake_name = (productname).getText().toString();
        String cake_size = "";
        String cake_qty = "1";
        String cake_price = (productprice).getText().toString();
        String cake_type = "Decoration Product";
        String price = String.valueOf(cake_price1);
        String notes = "";
        String flavour = "";
        String design = "";
        String shape =  "";
        String type = "";
        creatUser1(cake_type,shape,cake_size, flavour,design,notes,cake_type,cake_name,cake_qty);

    }

    private void creatUser1(String cake_type, String shape, String cake_size, String flavour, String design, String notes, String cake_type1, String cake_name, String cake_qty) {
        DocumentReference documentReference = firestore.collection("users").document(uid).collection("cart").document(productid);

        String quantity = String.valueOf(cake_qty);
        Map<String, Object> product = new HashMap<>();
        product.put("productid", productid);
        product.put("quantity", "1");
        product.put("name", cake_name);
        product.put("image", cake_image);
        product.put("price", cake_price1);
        product.put("size", cake_size);
        product.put("caketype", cake_type);
        product.put("notes", notes);
        product.put("flavour", flavour);
        product.put("design", design);
        product.put("shape", shape);
        product.put("type", cake_type);

        documentReference.set(product).addOnSuccessListener(unused -> {
            Toast.makeText(Addi_Product_Detail.this, "Product added", Toast.LENGTH_SHORT).show();
            sendUserToNextActivity();
        }).addOnFailureListener(e ->  Toast.makeText(Addi_Product_Detail.this, "Error while adding product", Toast.LENGTH_SHORT).show());

    }

    private void sendUserToNextActivity() {
        Intent intent= new Intent(Addi_Product_Detail.this,cart_screen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void XmlId() {
        backbtn = findViewById(R.id.back_button);

        productimg = findViewById(R.id.product_img1);
        productname = findViewById(R.id.product_name1);
        desc = findViewById(R.id.product_desc1);

        productprice = findViewById(R.id.product_price1);
        countview = findViewById(R.id.count1);
        add = findViewById(R.id.plus);
        remove = findViewById(R.id.minus);

        addtocart = findViewById(R.id.cart);


    }
    private void createId() {
        DocumentReference documentReference = firestore.collection("AddProducts").document(productid);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentReference1 = task.getResult();
                Log.d("documentReference1", String.valueOf(documentReference1));
                if (documentReference1.exists()) {
                    AddiProductData data = documentReference1.toObject(AddiProductData.class);
                    Log.d("data", String.valueOf(data));
                    productname.setText(data.getName());
                    productprice.setText("$ " + data.getPrice());
                    desc.setText(data.getDesc());
                    Picasso.get()
                            .load(data.getImage())
                            .placeholder(R.drawable.home_cake_img)
                            .error(R.drawable.home_cake_img)
                            .into(productimg);

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

}
