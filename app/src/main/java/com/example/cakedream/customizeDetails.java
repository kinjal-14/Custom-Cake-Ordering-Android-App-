package com.example.cakedream;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class customizeDetails extends AppCompatActivity {

    String caketype, cakesize, cakeshape, cakeflavour, cakedesign;
    AppCompatButton addToCart;
    ImageView cart_btn, home1;
    TextInputEditText cake_message;
    TextView cake_type, cake_type1, cake_size, cake_size1, cake_shape, cake_shape1, cake_flavour, cake_flavour1, cake_design, cake_design1;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    String uid,productid;
    String uniqueId;
    UUID uuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.customize_details_screen);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            uid = mFirebaseUser.getUid();
        }else {
            uid = null;
        }

        Intent intent = getIntent();
        uniqueId = intent.getExtras().getString("uniqueid");
        Log.d("id",uniqueId);
        caketype = intent.getExtras().getString("caketype");
        Log.d("type", caketype);
        cakesize = intent.getExtras().getString("cakesize");
        Log.d("size", cakesize);
        cakeshape = intent.getExtras().getString("cakeshape");
        Log.d("shape", cakeshape);
        cakedesign = intent.getExtras().getString("cakedesign");
        Log.d("design", cakedesign);
        cakeflavour = intent.getExtras().getString("cakeflavour");
        Log.d("flavour", cakeflavour);

        home1 = findViewById(R.id.home1);
        cart_btn = findViewById(R.id.cart_btn);


        cake_type = findViewById(R.id.cake_type);
        cake_size = findViewById(R.id.cake_size);
        cake_shape = findViewById(R.id.cake_shape);
        cake_flavour = findViewById(R.id.cake_flavour);
        cake_design = findViewById(R.id.cake_design);

        cake_type1 = findViewById(R.id.t1);
        cake_shape1 = findViewById(R.id.t2);
        cake_size1 = findViewById(R.id.t3);
        cake_flavour1 = findViewById(R.id.t4);
        cake_design1 = findViewById(R.id.t5);

        cake_message = findViewById(R.id.cake_message);
        addToCart = findViewById(R.id.continue_btn);

        cake_type.setText(caketype);
        cake_size.setText(cakesize);
        cake_shape.setText(cakeshape);
        cake_flavour.setText(cakeflavour);
        cake_design.setText(cakedesign);

        cake_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customizeDetails.this, customizeCreate.class);
                startActivity(intent);
            }
        });
        cake_size1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customizeDetails.this, customizeCreate.class);
                startActivity(intent);
            }
        });
        cake_shape1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customizeDetails.this, customizeCreate.class);
                startActivity(intent);
            }
        });
        cake_flavour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customizeDetails.this, customizeCreate.class);
                startActivity(intent);
            }
        });
        cake_design1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(customizeDetails.this, customizeCreate.class);
                startActivity(intent);
            }
        });
        addToCart.setOnClickListener(v -> addToCart());

        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customizeDetails.this,pre_made_base.class);
                startActivity(intent);
            }
        });
        cart_btn.setOnClickListener(v -> addtocart());
    }

    private void addtocart() {

    }

    private void addToCart() {
        String caketype = (cake_type.getText()).toString();
        String cakeshape = (cake_shape.getText()).toString();
        String cakesize = (cake_size.getText()).toString();
        String cakeflavour = (cake_flavour.getText()).toString();
        String cakedesign = (cake_design.getText()).toString();
        String cakenotes = (cake_message.getText()).toString();
        String cakeimage = "https://firebasestorage.googleapis.com/v0/b/cakedream-76261.appspot.com/o/cake_images%2Fcustom.jpeg?alt=media&token=3709ffe2-0626-4d35-b53b-c69388d56ef2";
        String cakeprice = "100";
        String cake_type = "customize cake";
        String name = "customize cake";
        int qty = 1;

        creatUser1(cake_type,cakeshape,cakesize, cakeflavour,cakedesign,cakenotes,cakeimage,cakeprice,cake_type,name,qty);

    }

    private void creatUser(String cake_type, String cakeshape, String cakesize, String cakeflavour, String cakedesign, String cakenotes, String cakeimage, String cakeprice, String cake_type1, String name, int qty) {
        String quantity = String.valueOf(qty);
        Map<String, Object> product = new HashMap<>();
        product.put("productid", uniqueId);
        product.put("quantity", quantity);
        product.put("name", name);
        product.put("image", cakeimage);
        product.put("price", cakeprice);
        product.put("size", cakesize);
        product.put("caketype", caketype);
        product.put("notes", cakenotes);
        product.put("flavour", cakeflavour);
        product.put("design", cakedesign);
        product.put("shape", cakeshape);
        product.put("type", cake_type);
    }
    private void creatUser1(String cake_type, String cakeshape, String cakesize, String cakeflavour, String cakedesign, String cakenotes, String cakeimage, String cakeprice, String cake_type1, String name, int qty){
            FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                DocumentReference documentReference = firestore.collection("users").document(uid).collection("cart").document(uniqueId);

        String quantity = String.valueOf(qty);
        Map<String, Object> product = new HashMap<>();
        product.put("productid", uniqueId);
        product.put("quantity", quantity);
        product.put("name", name);
        product.put("image", cakeimage);
        product.put("price", cakeprice);
        product.put("size", cakesize);
        product.put("caketype", caketype);
        product.put("notes", cakenotes);
        product.put("flavour", cakeflavour);
        product.put("design", cakedesign);
        product.put("shape", cakeshape);
        product.put("type", cake_type);

                documentReference.set(product).addOnSuccessListener(unused -> {
                    Toast.makeText(customizeDetails.this, "User Created", Toast.LENGTH_SHORT).show();
                    sendUserToNextActivity();
                }).addOnFailureListener(e ->  Toast.makeText(customizeDetails.this, "User Created", Toast.LENGTH_SHORT).show());
    }

    private void sendUserToNextActivity() {
        Intent intent= new Intent(customizeDetails.this,cart_screen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}

