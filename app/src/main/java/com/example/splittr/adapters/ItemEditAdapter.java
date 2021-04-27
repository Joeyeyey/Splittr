package com.example.splittr.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splittr.AddEditOne;
import com.example.splittr.R;
import com.example.splittr.ReceiptComponents;

import java.util.List;

public class ItemEditAdapter extends RecyclerView.Adapter<ItemEditAdapter.MyViewHolder> {

    List<ReceiptComponents> receiptArrayList;
    Context context;
    private int selectedPos = RecyclerView.NO_POSITION;

    public ItemEditAdapter(List<ReceiptComponents> receiptArrayList, Context context) {
        this.receiptArrayList = receiptArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //associate layout with holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_item.setText(receiptArrayList.get(position).getItem());
        holder.tv_cost.setText(String.valueOf(receiptArrayList.get(position).getCost()));
        holder.tv_person.setText(receiptArrayList.get(position).getPerson());
        holder.itemView.setSelected(selectedPos == position);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //change recyclerview item colors
                notifyItemChanged(selectedPos);
                selectedPos = position;
                notifyItemChanged(selectedPos);

                //send control to AddEditOne activity
                Intent intent = new Intent(context, AddEditOne.class);
                intent.putExtra("id", receiptArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receiptArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_item;
        TextView tv_cost;
        TextView tv_person;
        ConstraintLayout parentLayout;

        //implement textviews
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
            tv_cost = itemView.findViewById(R.id.tv_cost);
            tv_person = itemView.findViewById(R.id.tv_person);
            parentLayout = itemView.findViewById(R.id.oneLineItemLayout);

        }
    }
}