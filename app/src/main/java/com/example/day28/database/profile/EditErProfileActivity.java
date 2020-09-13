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
import android.widget.EditText;

import com.example.day28.LoadingSystem;
import com.example.day28.MainActivity;
import com.example.day28.R;
import com.example.day28.database.firedatabase.ErModel;
import com.example.day28.engineer.ErMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

public class EditErProfileActivity extends AppCompatActivity {
    String firstName, lastName, userName, email, password, phoneNumber, address, gender, experience, branch,role;
    EditText fname,lname,emailedittext,phonenumber,addressedittext,experienceedittext;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_er_profile);
        final LoadingSystem loadingSystem = new LoadingSystem(EditErProfileActivity.this);
        loadingSystem.startLoadingSystem();
        btn = findViewById(R.id.goBackEditErProfile);
        btn.setVisibility(View.GONE);
        fname = findViewById(R.id.firstNameEditErProfileActivity);
        lname = findViewById(R.id.lastNameEditErProfileActivity);
        emailedittext = findViewById(R.id.emailEditErProfileActivity);
        phonenumber = findViewById(R.id.phoneEditErProfileActivity);
        experienceedittext = findViewById(R.id.experienceEditErProfileActivity);
        addressedittext = findViewById(R.id.addressEditErProfileActivity);
        mAuth = FirebaseAuth.getInstance();
        databaseReference= firebaseDatabase.getReference().child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstName=snapshot.child("firstName").getValue(String.class);
                lastName=snapshot.child("lastName").getValue(String.class);
                email = snapshot.child("email").getValue(String.class);
                address = snapshot.child("address").getValue(String.class);
                phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                userName = snapshot.child("username").getValue(String.class);
                gender = snapshot.child("gender").getValue(String.class);
                role = snapshot.child("role").getValue(String.class);
                password = snapshot.child("password").getValue(String.class);
                experience = snapshot.child("experience").getValue(String.class);
                branch = snapshot.child("branch").getValue(String.class);
                fname.setText(firstName);
                lname.setText(lastName);
                emailedittext.setText(email);
                addressedittext.setText(address);
                phonenumber.setText(phoneNumber);
                loadingSystem.endLoadingsystem();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            });
            btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ErMainActivity.class));finish();
            }
        });
        findViewById(R.id.updateInformationEditErProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingSystem.startLoadingSystem();
                ErModel erModel = new ErModel(firstName,lastName,userName,email,password,phoneNumber,address,gender,experience,branch,role);
                databaseReference.setValue(erModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            loadingSystem.endLoadingsystem();
                            btn.setVisibility(View.VISIBLE);
                        }else {
                            loadingSystem.endLoadingsystem();
                            FancyToast.makeText(getApplicationContext(),task.getException().getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });
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