package com.example.cakedream;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class user_canceled_order_adapter extends RecyclerView.Adapter<user_canceled_order_adapter.MyViewHolder> {

    List<orderdata> orderdata7;

    public user_canceled_order_adapter(List<orderdata> orderdata7) {
        this.orderdata7 = orderdata7;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView order;
        private TextView date;
        private  TextView total;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order = itemView.findViewById(R.id.processing_orderid2);
            total = itemView.findViewById(R.id.processing_price2);
            date = itemView.findViewById(R.id.processing_placed2);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cancelled_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String orderid = orderdata7.get(position).getOrderid();
        String placed = orderdata7.get(position).getPlaced();
        String total = orderdata7.get(position).getTotal();

        holder.order.setText(orderid);
        holder.total.setText(total);
        holder.date.setText(placed);

    }

    @Override
    public int getItemCount() {
        return orderdata7.size();
    }


}
