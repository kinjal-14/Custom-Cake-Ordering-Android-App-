package com.example.cakedream;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.MyViewHolder>{
    private final ArrayList<cart_model> cart_product_data;
    FirebaseFirestore firestore;
    String uid;

    public cartAdapter(ArrayList<cart_model> cart_product_data) {
        this.cart_product_data = cart_product_data;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        assert mFirebaseUser != null;
        uid = mFirebaseUser.getUid();


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final  ImageView cart_product_img;
       private final TextView cake_name;
       private final TextView qty;
       private final TextView cart_card_price;
       private final ImageButton cart_remove;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_product_img = itemView.findViewById(R.id.cart_product_img);
            cake_name = itemView.findViewById(R.id.cake_name);
            qty = itemView.findViewById(R.id.qty);
            cart_card_price = itemView.findViewById(R.id.cart_card_price);
            cart_remove = itemView.findViewById(R.id.cart_remove);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = cart_product_data.get(position).getName();
        String qty = cart_product_data.get(position).getQuantity();
        String price = String.valueOf(cart_product_data.get(position).getPrice());

        holder.cake_name.setText(name);
        holder.cart_card_price.setText(price);
        holder.qty.setText(qty);
        Picasso.get()
                .load(cart_product_data.get(position).getImage())
                .placeholder(R.drawable.home_img)
                .error(R.drawable.home_img)
                .into(holder.cart_product_img);

        holder.cart_remove.setOnClickListener(view -> removeItemCart(cart_product_data.get(position).getProductid(), view.getContext()));

    }

    private void removeItemCart(String productid, Context context) {
        firestore.collection("users").document(uid).collection("cart").document(productid)
                .delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(context, "Item removed From Cart successfully!", Toast.LENGTH_SHORT).show();
                    ((cart_screen) context).reloadActivity();
                })
                .addOnFailureListener(e -> Log.w("error", e.toString()));
    }

    @Override
    public int getItemCount() {
        return cart_product_data.size();
    }
}
