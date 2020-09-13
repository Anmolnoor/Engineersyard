package com.example.day28.database.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day28.LoadingSystem;
import com.example.day28.R;
import com.example.day28.database.register.UserErVerifierActivity;
import com.example.day28.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText emailLogin, passwordLogin;
    Button loginButton;
    TextView createNewAccount;
    FirebaseAuth mAuth;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final LoadingSystem loadingSystem = new LoadingSystem(LoginActivity.this);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        createNewAccount = findViewById(R.id.createNewAccounnt);
        loginButton = findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=emailLogin.getText().toString();
                password=passwordLogin.getText().toString();
                if (email.isEmpty()) {
                    emailLogin.setError("Email Is Required to Login");
                    emailLogin.requestFocus();
                } else if (password.isEmpty()) {
                    passwordLogin.setError("Password is Required to Login");
                    passwordLogin.requestFocus();
                } else {
                    loadingSystem.startLoadingSystem();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loadingSystem.endLoadingsystem();
                                startActivity(new Intent(LoginActivity.this, LoginVerifierActivity.class));
                                finish();
                                FancyToast.makeText(LoginActivity.this, "Login SuccessFull", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            } else {
                                loadingSystem.endLoadingsystem();
                                FancyToast.makeText(LoginActivity.this, task.getException().getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                }
            }
        });
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, UserErVerifierActivity.class));
                finish();
            }
        });
    }
}