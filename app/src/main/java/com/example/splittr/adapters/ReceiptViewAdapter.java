package com.example.splittr.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splittr.R;
import com.example.splittr.ReceiptEditActivity;
import com.example.splittr.receiptobjects.Receipt;

import java.util.List;

public class ReceiptViewAdapter extends RecyclerView.Adapter<ReceiptViewAdapter.ViewHolder> {

    private List<Receipt> values;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;

        public ViewHolder(View view) {
            super(view);
            layout = view;
            txtHeader = (TextView) view.findViewById(R.id.firstLine);
            txtFooter = (TextView) view.findViewById(R.id.secondLine);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReceiptViewAdapter(List<Receipt> myDataset) {
        values = myDataset;
    }

    public void add(int position, Receipt item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.receipt_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Receipt receipt = values.get(position);
        holder.txtHeader.setText(receipt.getLabel());
        holder.txtFooter.setText(receipt.getCreationTime().toString());
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layout.getContext().startActivity(new Intent(holder.layout.getContext(), ReceiptEditActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}
