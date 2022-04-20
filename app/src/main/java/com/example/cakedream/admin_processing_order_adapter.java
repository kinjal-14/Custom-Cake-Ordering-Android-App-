package com.example.cakedream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class admin_processing_order_adapter extends RecyclerView.Adapter<admin_processing_order_adapter.MyViewHolder> {

    private List<orderdata> orderdata2;

    public admin_processing_order_adapter(List<orderdata> orderdata2) {
        this.orderdata2 = orderdata2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView order;
        private TextView date;
        private  TextView total;
        private CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order = itemView.findViewById(R.id.processing_orderid);
            total = itemView.findViewById(R.id.processing_price);
            date = itemView.findViewById(R.id.processing_placed);
            card = itemView.findViewById(R.id.processing_card);
        }
    }
    @NonNull
    @Override
    public admin_processing_order_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_processing_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_processing_order_adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String orderid = orderdata2.get(position).getOrderid();
        String placed = orderdata2.get(position).getPlaced();
        String total = orderdata2.get(position).getTotal();

        holder.order.setText(orderid);
        holder.total.setText(total);
        holder.date.setText(placed);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),admin_delivered_orderdetail.class);
                intent.putExtra("orderId",orderdata2.get(position).getOrderid());
                intent.putExtra("ordertype",orderdata2.get(position).getOrdertype());
                intent.putExtra("placed",orderdata2.get(position).getPlaced());
                intent.putExtra("name",orderdata2.get(position).getName());
                intent.putExtra("contact",orderdata2.get(position).getPhone());
                intent.putExtra("email",orderdata2.get(position).getEmail());
                intent.putExtra("address",orderdata2.get(position).getAddress());
                intent.putExtra("total",orderdata2.get(position).getTotal());
                intent.putExtra("userid",orderdata2.get(position).getUid());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderdata2.size();
    }


}
