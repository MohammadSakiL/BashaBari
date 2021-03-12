package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _0Welcome extends AppCompatActivity {

    private ImageView imgNextArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._0welcome);

        imgNextArrow = findViewById(R.id.imgNextArrow1);
        imgNextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_0Welcome.this,_1Welcome.class);
                startActivity(intent);
            }
        });

    }



}