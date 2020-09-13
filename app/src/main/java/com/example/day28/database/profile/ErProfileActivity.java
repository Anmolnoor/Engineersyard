package com.example.day28.database.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.day28.MainActivity;
import com.example.day28.R;
import com.example.day28.database.LocalDataBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class ErProfileActivity extends AppCompatActivity {
    ImageView imageViewErProfileActivity;
    TextView usernameErProfileActivity,branchErProfileActivity,experienceErProfileActivity,emailErProfileActivity,addressErProfileActivity,phoneNumberErProfileActivity;
    Button buttonErProfileActivity;
    FirebaseAuth mAuth;
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;
        String firstName, lastName, userName, email, phoneNumber, address, gender,experience, branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_profile);
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        phoneNumber = intent.getStringExtra("phoneNumber");
        address = intent.getStringExtra("address");
        experience = intent.getStringExtra("exp");
        branch = intent.getStringExtra("branch");

        imageViewErProfileActivity = findViewById(R.id.profileImageErProfileActivity);
        usernameErProfileActivity = findViewById(R.id.userNameErProfileActivity);
        emailErProfileActivity = findViewById(R.id.emailErProfileActivity);
        branchErProfileActivity = findViewById(R.id.branchErProfileActivity);
        phoneNumberErProfileActivity = findViewById(R.id.phoneNumberErProfileActivity);
        addressErProfileActivity = findViewById(R.id.addressErProfileActivity);
        buttonErProfileActivity = findViewById(R.id.buttonErProfileActivity);
        experienceErProfileActivity = findViewById(R.id.experienceErProfileActivity);
        mAuth = FirebaseAuth.getInstance();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference().child("Engineer").child(mAuth.getUid());
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                firstName = snapshot.child("firstName").getValue(String.class);
//                lastName = snapshot.child("lastName").getValue(String.class);
//                userName = snapshot.child("userName").getValue(String.class);
//                email = snapshot.child("email").getValue(String.class);
//                phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
//                address = snapshot.child("address").getValue(String.class);
//                gender = snapshot.child("gender").getValue(String.class);
//                experience = snapshot.child("experience").getValue(String.class);
//                branch = snapshot.child("branch").getValue(String.class);
//
//                emailErProfileActivity.setText(email);
//                usernameErProfileActivity.setText(firstName+" "+lastName);
//                phoneNumberErProfileActivity.setText(phoneNumber);
//                addressErProfileActivity.setText(address);
//                branchErProfileActivity.setText(branch);
//                experienceErProfileActivity.setText(experience+" Years Experience");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                FancyToast.makeText(getApplicationContext(),error.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//            }
//        });
        setContentTOTextView();
        buttonErProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone(phoneNumber);
            }
        });
    }

    private void setContentTOTextView() {
                emailErProfileActivity.setText(email);
                usernameErProfileActivity.setText(userName);
                phoneNumberErProfileActivity.setText(phoneNumber);
                addressErProfileActivity.setText(address);
                branchErProfileActivity.setText(branch);
                experienceErProfileActivity.setText(experience);
    }

    private void dialContactPhone(String phoneNumber) {
        FancyToast.makeText(getApplicationContext(),phoneNumber,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
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