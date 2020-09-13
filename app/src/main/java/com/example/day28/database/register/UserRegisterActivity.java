package com.example.day28.database.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.day28.LoadingSystem;
import com.example.day28.R;
import com.example.day28.database.firedatabase.UserModel;
import com.example.day28.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;
import java.util.regex.Pattern;

public class UserRegisterActivity extends AppCompatActivity {
    EditText firstName, lastName, userName, eMail, password, phoneNumber,address,repassworrd;
    RadioButton male, feMale;
    Button  register;
    FirebaseAuth mAuth;
    LoadingSystem loadingSystem = new LoadingSystem(UserRegisterActivity.this);
    DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference();
    String emailTxt,passwordTxt,fNameTxt,lNameTxt,userNameTxt,phoneNumberTxt,addressTxt,genderTxt,passwd,role;
    boolean pass = true,save=true;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        mAuth = FirebaseAuth.getInstance();
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        userName = findViewById(R.id.userName);
        eMail = findViewById(R.id.eMailUserRegister);
        password = findViewById(R.id.passwordUserRegister);
        repassworrd = findViewById(R.id.rePasswordUserRegister);
        phoneNumber = findViewById(R.id.phonenumberUserRegister);
        address = findViewById(R.id.addressUserRegister);
        male = findViewById(R.id.maleUserRegister);
        feMale = findViewById(R.id.feMaleUserRegister);
        register = findViewById(R.id.registerUser);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getinfo();
                verifyUserinfo();
                if (pass){
                    loadingSystem.startLoadingSystem();
                    registerUser();
                }
            }
        });
    }

    private void saveUserInfo() {
        userDatabaseReference.child(mAuth.getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    loadingSystem.endLoadingsystem();
                    startActivity(new Intent(UserRegisterActivity.this, UserMainActivity.class));
                    finish();
                }else {
                    loadingSystem.endLoadingsystem();
                    FancyToast.makeText(UserRegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }
        });
    }

    private void verifyUserinfo() {
        if (fNameTxt.isEmpty()) {
            firstName.setError("First name is Required");
            firstName.requestFocus();
            pass = false;
        } else if (lNameTxt.isEmpty()) {
            lastName.setError("Last name is Required");
            lastName.requestFocus();
            pass = false;

        }else if (userNameTxt.isEmpty()) {
            userName.setError("User Name is required");
            userName.requestFocus();
            pass = false;

        }else if (emailTxt.isEmpty()) {
            eMail.setError("E-Mail is Required");
            eMail.requestFocus();
            pass = false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
            eMail.setError("A Valid E-Mail is Required");
            eMail.requestFocus();
            pass = false;
        }else if (passwordTxt.isEmpty()) {
            password.setError("Password is Required");
            password.requestFocus();
            pass = false;
        }else if (passwordTxt.length()<8){
            password.setError("Password Must Contain 8 Character");
            password.requestFocus();
            pass = false;
        } else if (passwd.isEmpty()) {
            repassworrd.setError("Re-Enter Your Password Here");
            repassworrd.requestFocus();
            pass = false;
        } else if (!passwd.equals(passwordTxt)) {
            repassworrd.setError("Password Don't Match");
            repassworrd.requestFocus();
            pass = false;
        } else if (phoneNumberTxt.isEmpty()) {
            phoneNumber.setError("Phone Number is required");
            phoneNumber.requestFocus();
            pass = false;
        } else if (phoneNumberTxt.length() < 10) {
            phoneNumber.setError("Enter a valid Number");
            phoneNumber.requestFocus();
            pass = false;
        }else if (addressTxt.isEmpty()) {
            address.setError("Address is Required");
            address.requestFocus();
            pass = false;
        }
    }

    private void getinfo() {
        emailTxt = eMail.getText().toString();
        passwordTxt = password.getText().toString();
        passwd = repassworrd.getText().toString();
        fNameTxt = firstName.getText().toString();
        lNameTxt = lastName.getText().toString();
        userNameTxt =userName.getText().toString();
        phoneNumberTxt = phoneNumber.getText().toString();
        addressTxt =address.getText().toString();
        role = "User";
        if (male.isChecked()){
            genderTxt = "Male";

        }else genderTxt = "Female";

        userModel = new UserModel(fNameTxt,lNameTxt,userNameTxt,emailTxt,passwordTxt,phoneNumberTxt,addressTxt,genderTxt,role);
    }

    private void registerUser() {

        mAuth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(UserRegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FancyToast.makeText(UserRegisterActivity.this,"Registration successful",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    saveUserInfo();
                }else {
                    loadingSystem.endLoadingsystem();
                    FancyToast.makeText(UserRegisterActivity.this,task.getException().getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }

            }
        });
    }


}