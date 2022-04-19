package com.example.cakedream.pre_made_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cakedream.R;
import com.example.cakedream.pre_made_base;
import com.example.cakedream.productData;
import com.example.cakedream.product_detail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class preMadeHomeAdapter  extends RecyclerView.Adapter<preMadeHomeAdapter.MyViewHolder>{
    private List<productData> productItemData;

    public preMadeHomeAdapter( ArrayList<productData> productItemData) {
        this.productItemData = productItemData;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView cakeimg;
        private TextView cakename;
        private CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card_pre_made);
            cakeimg = itemView.findViewById(R.id.cake_img);
            cakename = itemView.findViewById(R.id.cake_name);
        }
    }

    @NonNull
    @Override
    public preMadeHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pre_made,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
         String cakename = productItemData.get(position).getName();

        Picasso.get()
                .load(productItemData.get(position).getImage())
                .placeholder(R.drawable.home_img)
                .error(R.drawable.home_img)
                .into(holder.cakeimg);
        holder.cakename.setText(cakename);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), product_detail.class);
                intent.putExtra("ProductId",productItemData.get(position).getId());
                intent.putExtra("cake_image",productItemData.get(position).getImage());
                intent.putExtra("cake_price1",productItemData.get(position).getPrice());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {return productItemData.size(); }


}
