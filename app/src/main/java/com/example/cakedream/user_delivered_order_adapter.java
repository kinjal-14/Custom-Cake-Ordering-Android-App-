package com.example.cakedream;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class user_delivered_order_adapter extends RecyclerView.Adapter<user_delivered_order_adapter.MyViewHolder> {

    List<orderdata> orderdata6;

    public user_delivered_order_adapter(List<orderdata> orderdata6) {
        this.orderdata6 = orderdata6;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView order;
        private TextView date;
        private  TextView total;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order = itemView.findViewById(R.id.processing_orderid1);
            total = itemView.findViewById(R.id.processing_price1);
            date = itemView.findViewById(R.id.processing_placed1);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_delivered_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String orderid = orderdata6.get(position).getOrderid();
        String placed = orderdata6.get(position).getPlaced();
        String total = orderdata6.get(position).getTotal();

        holder.order.setText(orderid);
        holder.total.setText(total);
        holder.date.setText(placed);
    }

    @Override
    public int getItemCount() {
        return orderdata6.size();
    }

}
