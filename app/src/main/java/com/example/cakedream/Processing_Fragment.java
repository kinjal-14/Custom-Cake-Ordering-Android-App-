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

public class Processing_Fragment extends Fragment {
    FirebaseFirestore firestore;
    user_processing_order_adapter adapter;
    RecyclerView user_processing_recycleview;
    ArrayList<orderdata> orderdata5;
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
        View view = inflater.inflate(R.layout.fragment_processing, container, false);
        firestore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        assert mFirebaseUser != null;
         uid = mFirebaseUser.getUid();
        user_processing_recycleview= view.findViewById(R.id.user_processing_recyclerview);
        user_processing_recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        orderdata5 = new ArrayList<>();
        adapter = new user_processing_order_adapter(orderdata5);
        user_processing_recycleview.setAdapter(adapter);
        setdata();

        return view;
    }

    private void setdata() {
        firestore.collection("users").document(uid).collection("order").whereEqualTo("ordertype","confirm").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList1 = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot1 : snapshotList1) {
                    Log.d("data", snapshot1.getData().toString());
                    orderdata data = snapshot1.toObject(orderdata.class);
                    assert data != null;
                    orderdata5.add(data);
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
