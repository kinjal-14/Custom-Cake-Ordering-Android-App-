package com.example.cakedream;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adminDelivered_orderdetailAdapter extends RecyclerView.Adapter<adminDelivered_orderdetailAdapter.MyViewHolder> {
    private List<adminProductData> adminProductData2 = new ArrayList<>();

    public adminDelivered_orderdetailAdapter(List<adminProductData> adminProductData2) {
        this.adminProductData2 = adminProductData2;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView cake_type;
        private TextView qty;
        private TextView size;
        private TextView shape;
        private TextView flavour;
        private TextView design;
        private TextView type;
        private TextView price;
        private TextView notes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cake_type = itemView.findViewById(R.id.product_type);
            qty = itemView.findViewById(R.id.qty);
            size = itemView.findViewById(R.id.size);
            shape = itemView.findViewById(R.id.shape);
            flavour = itemView.findViewById(R.id.flavour);
            design = itemView.findViewById(R.id.design);
            type = itemView.findViewById(R.id.type);
            price = itemView.findViewById(R.id.price);
            notes = itemView.findViewById(R.id.notes);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String caketype = adminProductData2.get(position).getName();
        String qty1 = adminProductData2.get(position).getQuantity();
        String size = adminProductData2.get(position).getSize();
        String shape = adminProductData2.get(position).getShape();
        String flavour = adminProductData2.get(position).getFlavour();
        String design = adminProductData2.get(position).getDesign();
        String type = adminProductData2.get(position).getType();
        String price = adminProductData2.get(position).getPrice();
        String notes = adminProductData2.get(position).getNotes();

        holder.cake_type.setText(caketype);
        holder.qty.setText(qty1);
        holder.size.setText(size);
        holder.shape.setText(shape);
        holder.flavour.setText(flavour);
        holder.design.setText(design);
        holder.type.setText(type);
        holder.price.setText(price);
        holder.notes.setText(notes);

    }

    @Override
    public int getItemCount() {
        return adminProductData2.size();
    }


}
