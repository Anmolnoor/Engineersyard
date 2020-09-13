package com.example.day28.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.day28.MainActivity;
import com.example.day28.R;
import com.example.day28.database.ListAdapter;
import com.example.day28.database.LocalDataBase;
import com.example.day28.database.firedatabase.ErModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ErlistActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    String fname, lname, username, exp, branch, role,phoneNumber,address,emailAddress;
    RecyclerView recyclerView;
    com.example.day28.database.ListAdapter myerAdapter;
    ArrayList<LocalDataBase> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erlist);
        final Intent intent = getIntent();
        final String intentExtra = intent.getStringExtra("type");
        Log.i("mytag", "What got in Intent"+intentExtra+"");
        mAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference();
        recyclerView = findViewById(R.id.erListRecyclerView);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i("mytag", "before");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        ErModel localDataBase = dataSnapshot.getValue(ErModel.class);
//                        Log.i("myTag",localDataBase.getEmail());
                        branch = dataSnapshot.child("branch").getValue(String.class);
                        Log.i("mytag", "Branch : " +branch+ "");
                          assert branch != null;
                        role = dataSnapshot.child("role").getValue(String.class);
                         assert role != null;
                                if (role.equals("Engineer")) {
                                    if (branch != null){
                                    if (branch.equals(intentExtra.toString())){
                                            fname = dataSnapshot.child("firstName").getValue(String.class);
                                            lname = dataSnapshot.child("lastName").getValue(String.class);
                                            username = fname + " " + lname;
                                            exp = dataSnapshot.child("experience").getValue(String.class) + " Years Experience";
                                            branch = dataSnapshot.child("branch").getValue(String.class);
                                            phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                                            address = dataSnapshot.child("address").getValue(String.class);
                                            emailAddress = dataSnapshot.child("email").getValue(String.class);
                                            list.add(new LocalDataBase(username, exp, branch,phoneNumber,address,emailAddress));

                                    }
                                    }
                                }
                        recycle();
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    FancyToast.makeText(getApplicationContext(),error.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionMode mode;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.singout) {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);


    }
    void recycle(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        myerAdapter = new ListAdapter(ErlistActivity.this, list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myerAdapter);
    }
}