package com.example.day28.database.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.day28.LoadingSystem;
import com.example.day28.R;
import com.example.day28.engineer.ErMainActivity;
import com.example.day28.user.UserMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginVerifierActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference ;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verifier);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child(mAuth.getUid());
        final LoadingSystem  loadingSystem = new LoadingSystem(LoginVerifierActivity.this);
        loadingSystem.startLoadingSystem();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                role = snapshot.child("role").getValue(String.class);
                if (role.equals("User")){
                    loadingSystem.endLoadingsystem();
                    startActivity(new Intent(LoginVerifierActivity.this, UserMainActivity.class));
                    finish();
                } else if (role.equals("Engineer")) {
                    loadingSystem.endLoadingsystem();
                    startActivity(new Intent(LoginVerifierActivity.this, ErMainActivity.class));
                    finish();
                }else {
                    FancyToast.makeText(getApplicationContext(),"Some thing Went Wrong users information not found",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FancyToast.makeText(getApplicationContext(),error.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }
        });
    }
}