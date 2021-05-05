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

import com.example.splittr.AddEditItem;
import com.example.splittr.R;
import com.example.splittr.receiptobjects.Item;

import java.util.ArrayList;
import java.util.Locale;

// adapter class to help with displaying and handling items in the item RecyclerView
public class ItemEditAdapter extends RecyclerView.Adapter<ItemEditAdapter.MyViewHolder> {

    // initialize variables
    ArrayList<Item> itemArrayList;
    Context context;
    private int selectedPos = RecyclerView.NO_POSITION;

    // constructor
    public ItemEditAdapter(ArrayList<Item> itemArrayList, Context context) {
        this.itemArrayList = itemArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //associate layout with holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_item,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_item.setText(itemArrayList.get(position).getName());
        holder.tv_cost.setText("$" + String.format(Locale.US, "%.2f",
                itemArrayList.get(position).getCost()));
        holder.tv_person.setText(String.join(", ", itemArrayList.get(position).getOwners()));
        holder.itemView.setSelected(selectedPos == position);

        holder.parentLayout.setOnClickListener(view -> {

            //change recyclerview item colors
            notifyItemChanged(selectedPos);
            selectedPos = position;
            notifyItemChanged(selectedPos);

            //send control to AddEditItem activity
            Intent intent = new Intent(context, AddEditItem.class);
            intent.putExtra("id", itemArrayList.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
