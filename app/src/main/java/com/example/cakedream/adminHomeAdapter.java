package com.example.cakedream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adminHomeAdapter extends RecyclerView.Adapter<adminHomeAdapter.MyViewHolder>{
    private List<orderdata> orderdata1;

    public adminHomeAdapter(List<orderdata> orderdata1) {
        this.orderdata1 = orderdata1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  TextView order;
        private  TextView date;
        private CardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order = itemView.findViewById(R.id.orderid);
            date = itemView.findViewById(R.id.date);
            card = itemView.findViewById(R.id.admin_home_card);
        }
    }

    @NonNull
    @Override
    public adminHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_admin_home, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
        String orderid = orderdata1.get(position).getOrderid();
        Log.d("orderid",orderid);
        String placed = orderdata1.get(position).getPlaced();
        Log.d("orderid",placed);


        holder.order.setText(orderid);
        holder.date.setText(placed);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),adminHome_orderdetail.class);
                intent.putExtra("orderId",orderdata1.get(position).getOrderid());
                intent.putExtra("ordertype",orderdata1.get(position).getOrdertype());
                intent.putExtra("placed",orderdata1.get(position).getPlaced());
                intent.putExtra("name",orderdata1.get(position).getName());
                intent.putExtra("contact",orderdata1.get(position).getPhone());
                intent.putExtra("email",orderdata1.get(position).getEmail());
                intent.putExtra("address",orderdata1.get(position).getAddress());
                intent.putExtra("total",orderdata1.get(position).getTotal());
                intent.putExtra("userid",orderdata1.get(position).getUid());
                v.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return orderdata1.size();
    }
}
