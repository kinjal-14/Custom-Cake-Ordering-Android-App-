package com.example.cakedream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class cartAddiAdapter extends RecyclerView.Adapter<cartAddiAdapter.MyViewHolder> {
    private List<AddiProductData> addiProductItemData = new ArrayList<>();;

    public cartAddiAdapter( ArrayList<AddiProductData> addiProductItemData) {
        this.addiProductItemData = addiProductItemData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private ImageView productimg;
        private TextView productname;
        private TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_cart_add_product);
            productimg = itemView.findViewById(R.id.product_img);
            productname = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,@SuppressLint("RecyclerView") int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart_add_product,parent,false);
        return new cartAddiAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
        String productname = addiProductItemData.get(position).getName();
        String productprice = "$ " + addiProductItemData.get(position).getPrice();

        Picasso.get()
                .load(addiProductItemData.get(position).getImage())
                .placeholder(R.drawable.home_img)
                .error(R.drawable.home_img)
                .into(holder.productimg);
        holder.productname.setText(productname);
        holder.price.setText(productprice);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Addi_Product_Detail.class);
                intent.putExtra("ProductId1",addiProductItemData.get(position).getId());
                intent.putExtra("Productimg",addiProductItemData.get(position).getImage());
                intent.putExtra("ProductPrice",addiProductItemData.get(position).getPrice());

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { return addiProductItemData.size(); }


}
