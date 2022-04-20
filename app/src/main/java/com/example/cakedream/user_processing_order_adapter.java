package com.example.cakedream;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class user_processing_order_adapter extends RecyclerView.Adapter<user_processing_order_adapter.MyViewHolder> {
    List<orderdata> orderdata5;
    FirebaseFirestore firestore;
    String uid;


    public user_processing_order_adapter(List<orderdata> orderdata5) {
        this.orderdata5 = orderdata5;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        assert mFirebaseUser != null;
        uid = mFirebaseUser.getUid();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView order;
        private TextView date;
        private  TextView total;
        private ImageButton remove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order = itemView.findViewById(R.id.processing_orderid1);
            total = itemView.findViewById(R.id.processing_price1);
            date = itemView.findViewById(R.id.processing_placed1);
            remove = itemView.findViewById(R.id.order_cancel);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user_processing_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String orderid = orderdata5.get(position).getOrderid();
        String placed = orderdata5.get(position).getPlaced();
        String total = orderdata5.get(position).getTotal();

        holder.order.setText(orderid);
        holder.total.setText(total);
        holder.date.setText(placed);
        holder.remove.setOnClickListener(view -> removeItemCart(orderdata5.get(position).getOrderid(), view.getContext()));

    }

    private void removeItemCart(String orderid, Context context) {
        firestore.collection("users").document(uid).collection("order").document(orderid)
                .update("ordertype", "cancel")
                .addOnSuccessListener(unused -> {
                    Toast.makeText(context, "Your Order cancelled successfully!", Toast.LENGTH_SHORT).show();
                    ((user_Order) context).reloadActivity();
                }).addOnFailureListener(e -> Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show());

    }

    @Override
    public int getItemCount() {
        return orderdata5.size();
    }


}
