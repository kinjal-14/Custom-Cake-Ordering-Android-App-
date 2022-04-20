package com.example.cakedream;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class admin_delivered_orderdetail extends AppCompatActivity {
    ImageView backbtn, order;
    TextView orderid,type;
    TextView name,contact,email,address,total;
    String uname,ucontact,uemail,uaddress,utotal,uorderid,utype,uplaced;
    String uid;
    AppCompatButton confirm;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    ArrayList<adminProductData> adminProductData2;
    adminDelivered_orderdetailAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_order_processing);
        Intent intent = getIntent();
        uorderid = intent.getExtras().getString("orderId");
        utype = intent.getExtras().getString("ordertype");
        uplaced = intent.getExtras().getString("placed");
        uname = intent.getExtras().getString("name");
        ucontact = intent.getExtras().getString("contact");
        uemail = intent.getExtras().getString("email");
        uaddress = intent.getExtras().getString("address");
        utotal = intent.getExtras().getString("total");
        uid = intent.getExtras().getString("userid");
        Log.d("userid", uid);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.admin_delivered_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminProductData2 = new ArrayList<>();
        adapter = new adminDelivered_orderdetailAdapter(adminProductData2);
        recyclerView.setAdapter(adapter);

        backbtn = findViewById(R.id.back_button);
        order = findViewById(R.id.cart_button);

        orderid = findViewById(R.id.order_id);
        type = findViewById(R.id.order_type);

        name = findViewById(R.id.order_name);
        contact = findViewById(R.id.order_contact);
        email = findViewById(R.id.order_email);
        address = findViewById(R.id.order_address);
        total = findViewById(R.id.subtotal);

        confirm = findViewById(R.id.order_completed);

        orderid.setText(uorderid);
        type.setText(utype);
        name.setText(uname);
        contact.setText(ucontact);
        email.setText(uemail);
        address.setText(uaddress);
        total.setText("$" +utotal);
        setdata();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(admin_delivered_orderdetail.this,admin_home.class);
                startActivity(intent1);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_delivered_orderdetail.this,admin_home.class);
                updatedata();
                startActivity(intent);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_delivered_orderdetail.this,admin_order.class);
                startActivity(intent);
            }
        });

    }


    private void updatedata() {
        firestore.collection("users").document(uid).collection("order").document(uorderid)
                .update("ordertype", "completed")
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(),"order is confirmed",Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show());

    }

    private void setdata() {
        firestore.collection("users").document(uid)
                .collection("order").document(uorderid)
                .collection("products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot:snapshotList){
                            adminProductData data = snapshot.toObject(adminProductData.class);
                            assert data != null;
                            adminProductData2.add(data);
                            Log.d("productdata",snapshot.getData().toString());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("data no found", String.valueOf(e));
            }
        });
    }
}
