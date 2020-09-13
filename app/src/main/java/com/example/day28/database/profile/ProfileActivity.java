package com.example.day28.database.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day28.LoadingSystem;
import com.example.day28.MainActivity;
import com.example.day28.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ImageView imageViewProfileActivity;
    TextView usernameProfileActivity,emailProfileActivity,addressProfileActivity,phoneNumberProfileActivity;
    FirebaseAuth mAuth;
    FirebaseDatabase  firebaseDatabase;
    DatabaseReference databaseReference;
    String firstName, lastName, userName, email, phoneNumber, address, gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final LoadingSystem loadingSystem = new LoadingSystem(ProfileActivity.this);
        loadingSystem.startLoadingSystem();
        imageViewProfileActivity = findViewById(R.id.profileImageProfileActivity);
        usernameProfileActivity = findViewById(R.id.userNameProfileActivity);
        emailProfileActivity = findViewById(R.id.emailProfileActivity);
        phoneNumberProfileActivity = findViewById(R.id.phoneNumberProfileActivity);
        addressProfileActivity = findViewById(R.id.addressProfileActivity);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstName = snapshot.child("firstName").getValue(String.class);
                lastName = snapshot.child("lastName").getValue(String.class);
                userName = snapshot.child("userName").getValue(String.class);
                email = snapshot.child("email").getValue(String.class);
                phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                address = snapshot.child("address").getValue(String.class);
                gender = snapshot.child("gender").getValue(String.class);

                emailProfileActivity.setText(email);
                usernameProfileActivity.setText(firstName+" "+lastName);
                phoneNumberProfileActivity.setText(phoneNumber);
                addressProfileActivity.setText(address);
                loadingSystem.endLoadingsystem();
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
}