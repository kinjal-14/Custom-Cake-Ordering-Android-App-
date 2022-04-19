package com.example.cakedream;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cakedream.pre_made_Adapter.preMadeHomeAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class pre_made_base extends AppCompatActivity {

    Button premadehome, customize;
    TextView premade,username;
    RecyclerView recyclerView;
    ImageView account,cart_icon;
    ArrayList<productData> productItemData;
    LinearLayoutManager linearLayoutManager;
    preMadeHomeAdapter preMadeHomeAdapter;
    FirebaseFirestore firebaseFirestore;
    private String uid;
    private String userName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pre_made_home);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            uid = mFirebaseUser.getUid();
            getUsername();
        }else {
            uid = null;
        }

        recyclerView = findViewById(R.id.pre_made_slider);
        productItemData = new ArrayList<productData>();

        premadehome = findViewById(R.id.pre_made_home);
        customize = findViewById(R.id.customize);

        premade = findViewById(R.id.pre_made_home1);
        username = findViewById(R.id.userName);

        account = findViewById(R.id.home_icon);
        cart_icon = findViewById(R.id.cart_icon);

        setProductInformation();
        premadehome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uid != null){
                    Intent intent = new Intent(v.getContext(), pre_made_full_screen.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(v.getContext(), login_screen.class);
                    startActivity(intent);
                }
            }
        });
        premade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uid != null){
                    Intent intent = new Intent(v.getContext(), pre_made_full_screen.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(v.getContext(), login_screen.class);
                    startActivity(intent);
                }
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uid != null){
                    Intent intent = new Intent(v.getContext(), account_screen.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(v.getContext(), login_screen.class);
                    startActivity(intent);
                }
            }
        });

        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uid != null){
                    Intent intent = new Intent(v.getContext(), customizeCreate.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(v.getContext(), login_screen.class);
                    startActivity(intent);
                }

            }
        });

        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uid != null){
                    Intent intent = new Intent(v.getContext(), cart_screen.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(v.getContext(), login_screen.class);
                    startActivity(intent);
                }

            }
        });


    }

    private void getUsername() {
        DocumentReference docRef = firebaseFirestore.collection("users").document(uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    userData data = document.toObject(userData.class);
                    assert data != null;
                    userName = "Hello, "+ data.name;
                    username.setText(userName);
                } else {
                    username.setText("Hello, Guest User");
                    Log.d("document Not Found", "No such document");
                }
            } else {
                Log.d("error", "get failed with ", task.getException());
            }
        }).addOnFailureListener(e -> Log.d("error", e.toString()));
    }


    private void setAdapter() {
        preMadeHomeAdapter  = new preMadeHomeAdapter(productItemData);
        recyclerView.setAdapter(preMadeHomeAdapter);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(pre_made_base.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    private void setProductInformation() {
        firebaseFirestore.collection("products").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()){
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list){
                            productData data = d.toObject(productData.class);
                            assert data != null;
                            String type = data.getType();
                            productItemData.add(data);
                            Log.d("data", String.valueOf(data));
                        }
                    }else {
                        Toast.makeText(this, "Cakes are not avalible for this time", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(this, "Opps!,Fail to fetch data", Toast.LENGTH_SHORT).show())
                .addOnCompleteListener(task -> setAdapter());
    }

}