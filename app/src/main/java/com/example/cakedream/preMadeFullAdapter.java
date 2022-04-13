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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class preMadeFullAdapter  extends RecyclerView.Adapter<preMadeFullAdapter.MyViewHolder> {
    private List<productData> productItemData1 = new ArrayList<>();


    public preMadeFullAdapter(ArrayList<productData> productItemData1) {
        this.productItemData1 = productItemData1;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView cakeimg;
        private TextView cakename;
        private TextView cakeprice;
        private CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card_pre_made_full);
            cakeimg = itemView.findViewById(R.id.cake_img1);
            cakename = itemView.findViewById(R.id.cake_name1);
            cakeprice = itemView.findViewById(R.id.cake_price1);
        }
    }
    @NonNull
    @Override
    public preMadeFullAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pre_made_full,parent,false);
        return new preMadeFullAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull preMadeFullAdapter.MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
        String cakename = productItemData1.get(position).getName();
        String cakeprice = String.valueOf(productItemData1.get(position).getPrice());

        Picasso.get()
                .load(productItemData1.get(position).getImage())
                .placeholder(R.drawable.home_img)
                .error(R.drawable.home_img)
                .into(holder.cakeimg);
        holder.cakename.setText(cakename);
        holder.cakeprice.setText("$ " + cakeprice);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), product_detail.class);
                intent.putExtra("ProductId",productItemData1.get(position).getId());
                intent.putExtra("cake_image",productItemData1.get(position).getImage());
                intent.putExtra("cake_price1",productItemData1.get(position).getPrice());
                v.getContext().startActivity(intent);
            }
        });
    }
    public  void  lowTohigh (ArrayList<productData> copyItemData){
        productItemData1.clear();
        Collections.sort(copyItemData, Comparator.comparing(productData::getPrice));
        productItemData1.addAll(copyItemData);
        notifyDataSetChanged();
    }
    public  void  highToLow (ArrayList<productData> copyItemData){
        productItemData1.clear();
        Collections.sort(copyItemData, Comparator.comparing(productData::getPrice).reversed());
        productItemData1.addAll(copyItemData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {return productItemData1.size(); }
}
