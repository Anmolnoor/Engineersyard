package com.example.day28.database.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.day28.LoadingSystem;
import com.example.day28.R;
import com.example.day28.database.firedatabase.ErModel;
import com.example.day28.engineer.ErMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ErRegisterActivity extends AppCompatActivity {
    NiceSpinner niceSpinner;
    ArrayList<String> list = new ArrayList<>();
    EditText firstName, lastName, userName, eMail, password, phoneNumber,address, repassword,experience;
    RadioButton male, feMale;
    Button select, register;
    FirebaseAuth mAuth;
    DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference();
    String firstNameTxt, lastNameTxt, userNameTxt, emailTxt, passwordTxt, phoneNumberTxt, addressTxt, genderTxt, experienceTxt, branchTxt,rePasswordTxt, role;
    boolean pass = true;
    ErModel erModel;
    final LoadingSystem loadingSystem = new LoadingSystem(ErRegisterActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_register);
        eMail =findViewById(R.id.emailErRegister);
        password=findViewById(R.id.passwordErRegister);
        firstName = findViewById(R.id.firstNameErRegister);
        lastName = findViewById(R.id.lastNameErRegister);
        userName = findViewById(R.id.userNameErRegister);
        repassword = findViewById(R.id.rePasswordErRegister);
        address = findViewById(R.id.addressErRegister);
        experience = findViewById(R.id.experienceErRegister);
        male = findViewById(R.id.maleErRegister);
        feMale = findViewById(R.id.feMaleErRegister);
        phoneNumber= findViewById(R.id.phoneNumberErRegister);
        register = findViewById(R.id.registerEr);
        niceSpinner = findViewById(R.id.spinnerRegister);
        getData();
        niceSpinner.attachDataSource(list);

        mAuth = FirebaseAuth.getInstance();
       // ArrayAdapter<String> myspinnerAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);
        //myspinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
      //  spinnerRegister.setAdapter(myspinnerAdapter);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getInfo();
                verifyUserInfo();
                if (pass){
                    loadingSystem.startLoadingSystem();
                    registerEr();
                }
            }
        });

    }

    private void registerEr() {
        mAuth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(ErRegisterActivity.this, ErMainActivity.class));
                    finish();
                    FancyToast.makeText(ErRegisterActivity.this,"Successful Register",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    saveInfo();
                } else {
                    loadingSystem.endLoadingsystem();
                    FancyToast.makeText(ErRegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }
        });
    }

    private void saveInfo() {
        userDatabaseReference.child(mAuth.getUid()).setValue(erModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadingSystem.endLoadingsystem();
                    startActivity(new Intent(ErRegisterActivity.this, ErMainActivity.class));
                    finish();
                }else {
                    loadingSystem.endLoadingsystem();
                    FancyToast.makeText(ErRegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }
        });
    }

    private void getData() {
        list.add("Select your branch");
        list.add("Computer Science Er");
        list.add("Architecture Er");
        list.add("Carpenter Er");
        list.add("Civil Er");
        list.add("Mechanical Er");
        list.add("Electrical Er");
    }
    private void verifyUserInfo() {
        if (firstNameTxt.isEmpty()) {
            firstName.setError("First name is Required");
            firstName.requestFocus();
            pass = false;
        } else if (lastNameTxt.isEmpty()) {
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
        } else if (rePasswordTxt.isEmpty()) {
            repassword.setError("Re-Enter Your Password Here");
            repassword.requestFocus();
        } else if (!rePasswordTxt.equals(passwordTxt)) {
            repassword.setError("Password Don't Match");
            repassword.requestFocus();
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
        }else if (experienceTxt.isEmpty()) {
            experience.setError("Address is Required");
            experience.requestFocus();
            pass = false;
        }else if (branchTxt.equals("Select your branch")) {
            FancyToast.makeText(ErRegisterActivity.this,"Select your Branch",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            pass = false;
        }
    }
    private void getInfo() {
        emailTxt = eMail.getText().toString();
        passwordTxt = password.getText().toString();
        rePasswordTxt = repassword.getText().toString();
        firstNameTxt = firstName.getText().toString();
        lastNameTxt = lastName.getText().toString();
        userNameTxt =userName.getText().toString();
        phoneNumberTxt = phoneNumber.getText().toString();
        addressTxt =address.getText().toString();
        role = "Engineer";
        if (male.isChecked()){
            genderTxt = "Male";

        }else genderTxt = "Female";
        experienceTxt = experience.getText().toString();
        branchTxt = niceSpinner.getSelectedItem().toString();

        erModel = new ErModel(firstNameTxt,lastNameTxt,userNameTxt,emailTxt,passwordTxt,phoneNumberTxt,addressTxt,genderTxt,experienceTxt,branchTxt,role);
    }

}




//
//
//<LinearLayout
//                android:layout_width="match_parent"
//                        android:layout_height="wrap_content"
//                        android:orientation="horizontal"
//                        >
//<TextView
//                    android:id="@+id/selectImageErRegister"
//                            android:layout_width="wrap_content"
//                            android:layout_height="match_parent"
//                            android:text="Select Your profile picture"
//                            android:textSize="20sp"
//                            android:textColor="#fff"
//                            android:layout_margin="10dp"/>
//<Button
//                    android:id="@+id/selectButtonErRegister"
//                            android:layout_width="match_parent"
//                            android:layout_height="wrap_content"
//                            android:textColor="#111"
//                            android:text="select"
//                            android:textSize="21sp"
//                            android:backgroundTint="@color/highlight"/>
//</LinearLayout>
//
//<LinearLayout
//               android:layout_width="match_parent"
//                       android:layout_height="wrap_content"
//                       android:orientation="horizontal"
//                       ><TextView
//               android:layout_width="match_parent"
//                       android:layout_height="wrap_content"
//                       android:layout_weight="1"
//                       android:paddingStart="8dp"
//                       android:text="Select Branch :"
//                       android:textColor="#fff"
//                       android:textSize="24sp"
//                       tools:ignore="RtlSymmetry" />
//<Spinner
//                   android:id="@+id/spinnerRegister"
//                           android:layout_width="match_parent"
//                           android:layout_height="30dp"
//                           android:layout_weight="1"
//                           android:textAlignment="center"
//                           android:backgroundTint="#f40303" />
//</LinearLayout>
