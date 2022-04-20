package com.example.cakedream;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class admin_home extends AppCompatActivity {
    ArrayList<orderdata> orderdata1;
    FirebaseFirestore firestore;
    ImageView admin_order_detail,logout_button;
    String uid = "";
    RecyclerView admin_recyclerview;
    adminHomeAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    ArrayList<String> userid = new ArrayList<>();
    ArrayList<String> name2 = new ArrayList<>();
    ArrayList<String> address1 = new ArrayList<>();
    ArrayList<String> address2 = new ArrayList<>();
    ArrayList<String> city = new ArrayList<>();
    ArrayList<String> state = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    ArrayList<String> totalprice = new ArrayList<>();


    ArrayList<String> docid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        setData();

        admin_recyclerview = findViewById(R.id.admin_recyclerview);
        admin_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        orderdata1 = new ArrayList<>();
        adapter = new adminHomeAdapter(orderdata1);
        admin_recyclerview.setAdapter(adapter);

        logout_button = findViewById(R.id.logout_button);
        admin_order_detail = findViewById(R.id.admin_order_detail);


        logout_button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),pre_made_base.class);
            startActivity(intent);
        });
        admin_order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this,admin_order.class);
                startActivity(intent);
            }
        });

    }


    public void setData() {
        firestore.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot: snapshotList){
                    String docId = snapshot.getId().toString();
                    Log.d("user id",docId);

                    firestore.collection("users").document(docId).collection("order").whereEqualTo("ordertype","new").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> snapshotList1 = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot snapshot1:snapshotList1){
                                Log.d("data", snapshot1.getData().toString());
                                orderdata data = snapshot1.toObject(orderdata.class);
                                assert data != null;
                                orderdata1.add(data);
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("no data", String.valueOf(e));
                        }
                    });
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("no data", String.valueOf(e));
            }
        });


    }
    }