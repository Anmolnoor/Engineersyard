package com.example.day28.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.day28.R;

import java.util.zip.Inflater;

public class useradapter extends RecyclerView.Adapter<myuserviewholder> {


    @NonNull
    @Override
    public myuserviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_erlist, parent, false);
        return new myuserviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myuserviewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class myuserviewholder extends RecyclerView.ViewHolder{
    TextView username;
    TextView branch;
    TextView exp;

    public myuserviewholder(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.erListRecyclerUserName);
        branch = itemView.findViewById(R.id.erListRecyclerBranch);
        exp = itemView.findViewById(R.id.erListRecyclerExp);
    }
}