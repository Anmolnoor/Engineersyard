package com.example.day28.database.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
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
import com.example.day28.database.firedatabase.UserModel;
import com.example.day28.engineer.ErMainActivity;
import com.example.day28.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    EditText firstName , lastName , email , address, phonnumber;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Button btn;
    String fname, lname, userName, emailtxt, phoneNumber, addresstxt, gender,role,password;
    LoadingSystem loadingSystem = new LoadingSystem(EditProfileActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        loadingSystem.startLoadingSystem();
        firstName = findViewById(R.id.firstNameEditProfileActivity);
        lastName = findViewById(R.id.lastNameEditProfileActivity);
        email = findViewById(R.id.emailEditProfileActivity);
        address = findViewById(R.id.addressEditProfileActivity);
        phonnumber = findViewById(R.id.phoneNumberEditProfileActivity);
        btn =findViewById(R.id.goBackEditProfile);
        btn.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        databaseReference= firebaseDatabase.getReference().child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fname=snapshot.child("firstName").getValue(String.class);
                lname=snapshot.child("lastName").getValue(String.class);
                emailtxt = snapshot.child("email").getValue(String.class);
                addresstxt = snapshot.child("address").getValue(String.class);
                phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                userName = snapshot.child("username").getValue(String.class);
                gender = snapshot.child("gender").getValue(String.class);
                role = snapshot.child("role").getValue(String.class);
                password = snapshot.child("password").getValue(String.class);
                firstName.setText(fname);
                lastName.setText(lname);
                email.setText(emailtxt);
                address.setText(addresstxt);
                phonnumber.setText(phoneNumber);
                loadingSystem.endLoadingsystem();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingSystem.endLoadingsystem();
                FancyToast.makeText(getApplicationContext(),error.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }
        });

        findViewById(R.id.updateInformationEditProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingSystem.startLoadingSystem();
                emailtxt = email.getText().toString();
                fname=firstName.getText().toString();
                lname=lastName.getText().toString();
                addresstxt = address.getText().toString();
                phoneNumber = phonnumber.getText().toString();
                UserModel  userModel = new UserModel(fname,lname,userName,emailtxt,password,phoneNumber,addresstxt,gender,role);
                databaseReference.setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            FancyToast.makeText(EditProfileActivity.this, Objects.requireNonNull(task.getException()).getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });
                btn.setVisibility(View.VISIBLE);
                loadingSystem.endLoadingsystem();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserMainActivity.class));
                finish();
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