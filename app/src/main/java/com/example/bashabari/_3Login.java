package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class _3Login extends AppCompatActivity {

    private TextView txtRegister3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._3login);

        txtRegister3 = findViewById(R.id.txtRegister3);
        txtRegister3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_3Login.this,_4Register.class);
                startActivity(intent);
            }
        });
    }
}