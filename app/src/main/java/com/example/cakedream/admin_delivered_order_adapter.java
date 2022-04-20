package com.example.cakedream;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class admin_delivered_order_adapter extends RecyclerView.Adapter<admin_delivered_order_adapter.MyViewHolder> {
    private List<orderdata> orderdata3;

    public admin_delivered_order_adapter(List<orderdata> orderdata3) {
        this.orderdata3 = orderdata3;
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
        String orderid = orderdata3.get(position).getOrderid();
        String placed = orderdata3.get(position).getPlaced();
        String total = orderdata3.get(position).getTotal();

        holder.order.setText(orderid);
        holder.total.setText(total);
        holder.date.setText(placed);

    }

    @Override
    public int getItemCount() {
        return orderdata3.size();
    }


}
