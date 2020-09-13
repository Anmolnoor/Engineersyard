package com.example.day28.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ErAdapter extends RecyclerView.Adapter<myERViewholder> {
    @NonNull
    @Override
    public myERViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull myERViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class myERViewholder  extends RecyclerView.ViewHolder{

    public myERViewholder(@NonNull View itemView) {
        super(itemView);
    }
}
