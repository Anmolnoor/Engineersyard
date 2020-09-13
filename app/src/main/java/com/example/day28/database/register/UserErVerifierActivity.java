package com.example.day28.database.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.day28.R;

public class UserErVerifierActivity extends AppCompatActivity {
Button sellButton, buyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_er_verifier);
        sellButton = findViewById(R.id.sellButton);
        buyButton = findViewById(R.id.buyButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserErVerifierActivity.this,UserRegisterActivity.class));
                finish();
            }
        });
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserErVerifierActivity.this,ErRegisterActivity.class));
            }
        });
    }
}