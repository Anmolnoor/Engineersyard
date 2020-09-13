package com.example.day28.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.day28.LoadingSystem;
import com.example.day28.MainActivity;
import com.example.day28.R;
import com.example.day28.database.profile.EditProfileActivity;
import com.example.day28.database.profile.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

public class UserMainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView computerEngineerUserActivity,mechanicalEngineerUserActivity,civilEngineerUserActivity,carpenterEngineerUserActivity,electricalEngineerUserActivity,architectureEngineerUserActivity,editProfileEngineerUserActivity,profileEngineerUserActivity,userProfileEngineerActivity;
    FirebaseAuth mAuth;
    TextView usernameEngineerUserActivity,emailEngineerUserActivity;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressBar progressbarEngineerUserActivity;
    String firstName,lastName,email,userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        final LoadingSystem loadingSystem = new LoadingSystem(UserMainActivity.this);
        loadingSystem.startLoadingSystem();
        profileEngineerUserActivity = findViewById(R.id.profileEngineerUserActivity);
        computerEngineerUserActivity = findViewById(R.id.computerEngineerUserActivity);
        mechanicalEngineerUserActivity = findViewById(R.id.mechanicalEngineerUserActivity);
        civilEngineerUserActivity = findViewById(R.id.civilEngineerUserActivity);
        carpenterEngineerUserActivity = findViewById(R.id.carpenterEngineerUserActivity);
        electricalEngineerUserActivity =findViewById(R.id.electricalEngineerUserActivity);
        editProfileEngineerUserActivity = findViewById(R.id.editProfileEngineerUserActivity);
        architectureEngineerUserActivity =findViewById(R.id.architectureEngineerUserActivity);
        userProfileEngineerActivity =findViewById(R.id.userProfileEngineerUserActivity);
        emailEngineerUserActivity = findViewById(R.id.emailEngineerUserActivity);
        usernameEngineerUserActivity =findViewById(R.id.usernameEngineerUserActivity);
        progressbarEngineerUserActivity = findViewById(R.id.progressbarEngineerUserActivity);
        mAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        profileEngineerUserActivity.setOnClickListener(this);
        computerEngineerUserActivity.setOnClickListener(this);
        mechanicalEngineerUserActivity.setOnClickListener(this);
        civilEngineerUserActivity.setOnClickListener(this);
        carpenterEngineerUserActivity.setOnClickListener(this);
        electricalEngineerUserActivity.setOnClickListener(this);
        editProfileEngineerUserActivity.setOnClickListener(this);
        architectureEngineerUserActivity.setOnClickListener(this);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.child(mAuth.getUid()).child("email").getValue(String.class);
                firstName = snapshot.child(mAuth.getUid()).child("firstName").getValue(String.class);
                lastName = snapshot.child(mAuth.getUid()).child("lastName").getValue(String.class);
                emailEngineerUserActivity.setText(email);
                userName = firstName+" "+lastName;
                usernameEngineerUserActivity.setText(userName);
                loadingSystem.endLoadingsystem();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FancyToast.makeText(getApplicationContext(),error.getMessage()+"",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profileEngineerUserActivity : startActivity(new Intent(getApplicationContext(), ProfileActivity.class));break;
            case R.id.computerEngineerUserActivity : startActivity(new Intent(getApplicationContext(), ErlistActivity.class).putExtra("type","Computer Science Er"));break;
            case R.id.electricalEngineerUserActivity : startActivity(new Intent(getApplicationContext(), ErlistActivity.class).putExtra("type","Electrical Er"));break;
            case R.id.civilEngineerUserActivity : startActivity(new Intent(getApplicationContext(), ErlistActivity.class).putExtra("type","Civil Er"));break;
            case R.id.carpenterEngineerUserActivity : startActivity(new Intent(getApplicationContext(), ErlistActivity.class).putExtra("type","Carpenter Er"));break;
            case R.id.architectureEngineerUserActivity : startActivity(new Intent(getApplicationContext(), ErlistActivity.class).putExtra("type","Architecture Er"));break;
            case R.id.mechanicalEngineerUserActivity : startActivity(new Intent(getApplicationContext(), ErlistActivity.class).putExtra("type","Mechanical Er"));break;
            case R.id.editProfileEngineerUserActivity : startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));break;
            default:
                FancyToast.makeText(getApplicationContext(),"Something Went Wrong",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();break;
        }
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