package com.example.day28.engineer;

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
import android.widget.Switch;
import android.widget.TextView;

import com.example.day28.LoadingSystem;
import com.example.day28.MainActivity;
import com.example.day28.R;
import com.example.day28.database.profile.EditErProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.zip.Inflater;

public class ErMainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView userNameErMainActivity, emailErMainActivity, branchNameErMainActivity,experienceErMainActivity,addressErMainActivity,phoneNumberErMainActivity;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference ;
    String username,fname,lname,email,phone,add,exp,branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_main);
        final LoadingSystem loadingSystem = new LoadingSystem(ErMainActivity.this);
        loadingSystem.startLoadingSystem();
        mAuth = mAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child(mAuth.getUid());
        imageView = findViewById(R.id.profileImageErMainActivity);
        userNameErMainActivity = findViewById(R.id.userNameErMainActivity);
        emailErMainActivity = findViewById( R.id.emailErMainActivity);
        experienceErMainActivity = findViewById(R.id.experienceErMAinActivity);
        addressErMainActivity = findViewById(R.id.addressErMainActivity);
        branchNameErMainActivity = findViewById(R.id.branchErMainActivity);
        phoneNumberErMainActivity = findViewById(R.id.phoneNumberErMainActivity);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fname = snapshot.child("firstName").getValue(String.class);
                lname = snapshot.child("lastName").getValue(String.class);
                email = snapshot.child("email").getValue(String.class);
                branch = snapshot.child("branch").getValue(String.class);
                exp = snapshot.child("experience").getValue(String.class);
                phone = snapshot.child("phoneNumber").getValue(String.class);
                add = snapshot.child("address").getValue(String.class);
                username = fname + " " + lname;
                emailErMainActivity.setText(email);
                userNameErMainActivity.setText(username);
                experienceErMainActivity.setText(exp+" Years Experience");
                addressErMainActivity.setText(add);
                phoneNumberErMainActivity.setText(phone);
                branchNameErMainActivity.setText(branch);
                loadingSystem.endLoadingsystem();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FancyToast.makeText(getApplicationContext(),error.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }
        });
        findViewById(R.id.editProfileErMainActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditErProfileActivity.class));
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