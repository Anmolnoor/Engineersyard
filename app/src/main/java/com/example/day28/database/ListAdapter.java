package com.example.day28.database;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.day28.R;
import com.example.day28.database.profile.ErProfileActivity;
import com.example.day28.user.ErlistActivity;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<myViewHolder> {
    Context context;
    ArrayList<LocalDataBase> list;

    public ListAdapter(Context context, ArrayList<LocalDataBase> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.erlistrecyclerview, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        final LocalDataBase localDataBase = list.get(position);
        holder.username.setText(localDataBase.username);
        holder.branch.setText(localDataBase.branch);
        holder.exp.setText(localDataBase.exp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String branchName = localDataBase.getBranch();
                String userName = localDataBase.getUsername();
                String exp = localDataBase.getExp();
                String email = localDataBase.getEmailAddress();
                String phoneNumber = localDataBase.getPhoneNumber();
                String address = localDataBase.getAddress();


                Intent intent =  new Intent(context, ErProfileActivity.class);
                intent.putExtra("branch",branchName);
                intent.putExtra("username",userName);
                intent.putExtra("exp",exp);
                intent.putExtra("email",email);
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("address",address);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


class myViewHolder extends RecyclerView.ViewHolder{
        TextView username, exp, branch;
    public myViewHolder(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.erListRecyclerUserName);
        exp = itemView.findViewById(R.id.erListRecyclerExp);
        branch = itemView.findViewById(R.id.erListRecyclerBranch);
    }
}