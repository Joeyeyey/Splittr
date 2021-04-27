package com.example.splittr.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splittr.AddEditOne;
import com.example.splittr.R;
import com.example.splittr.receiptobjects.Receipt;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReceiptSelectAdapter extends RecyclerView.Adapter<ReceiptSelectAdapter.MyViewHolder> {

    private static final String TAG = "ReceiptSelectAdapter";

    ArrayList<Receipt> receiptArrayList;
    Context context;
    private int selectedPos = RecyclerView.NO_POSITION;

    public ReceiptSelectAdapter(ArrayList<Receipt> receiptArrayList, Context context) {
        this.receiptArrayList = receiptArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //associate layout with holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_receipt, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewerHolder(" + holder.toString() + ", " + position + ")");
        holder.tv_receiptLabel.setText(receiptArrayList.get(position).getLabel());
        holder.tv_receiptDate.setText(String.valueOf(receiptArrayList.get(position).getCreationDate(DateTimeFormatter.ISO_LOCAL_DATE)));

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
        TextView tv_receiptLabel;
        TextView tv_receiptDate;
        ConstraintLayout parentLayout;

        //implement textviews
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_receiptLabel = itemView.findViewById(R.id.tv_receiptName);
            tv_receiptDate = itemView.findViewById(R.id.tv_receiptDate);
            parentLayout = itemView.findViewById(R.id.oneLineReceiptLayout);

        }
    }
}
