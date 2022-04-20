package com.example.cakedream;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Cancelled_Fragment extends Fragment {
    FirebaseFirestore firestore;
    user_canceled_order_adapter adapter;
    RecyclerView user_canceled_recyclerview;
    ArrayList<orderdata> orderdata7;
    String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancelled, container, false);
        firestore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        assert mFirebaseUser != null;
        uid = mFirebaseUser.getUid();
        user_canceled_recyclerview= view.findViewById(R.id.user_canceled_recyclerview);
        user_canceled_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        orderdata7 = new ArrayList<>();
        adapter = new user_canceled_order_adapter(orderdata7);
        user_canceled_recyclerview.setAdapter(adapter);
        setdata();
        return  view;
    }

    private void setdata() {
        firestore.collection("users").document(uid).collection("order").whereEqualTo("ordertype","cancel").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList1 = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot1 : snapshotList1) {
                    Log.d("data", snapshot1.getData().toString());
                    orderdata data = snapshot1.toObject(orderdata.class);
                    assert data != null;
                    orderdata7.add(data);
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