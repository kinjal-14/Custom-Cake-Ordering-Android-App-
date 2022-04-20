package com.example.cakedream;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Processing_Admin_Fragment extends Fragment {
    FirebaseFirestore firestore;
    admin_processing_order_adapter adapter;
    RecyclerView admin_processing_recyclerview;
    ArrayList<orderdata> orderdata2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firestore = FirebaseFirestore.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_processing, container, false);
        firestore = FirebaseFirestore.getInstance();
        admin_processing_recyclerview= view.findViewById(R.id.admin_processing_recyclerview);
        admin_processing_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        orderdata2 = new ArrayList<>();
        adapter = new admin_processing_order_adapter(orderdata2);
        admin_processing_recyclerview.setAdapter(adapter);
        setdata();

        return view;


    }

    private void setdata() {
        firestore.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot: snapshotList){
                    String docId = snapshot.getId().toString();
                    Log.d("user id",docId);

                    firestore.collection("users").document(docId).collection("order").whereEqualTo("ordertype","confirm").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> snapshotList1 = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot snapshot1:snapshotList1){
                                Log.d("data", snapshot1.getData().toString());
                                orderdata data = snapshot1.toObject(orderdata.class);
                                assert data != null;
                                orderdata2.add(data);
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
