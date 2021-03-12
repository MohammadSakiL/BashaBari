package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _1Welcome extends AppCompatActivity {

    private ImageView imgNextArrow2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._1welcome);

        imgNextArrow2 = findViewById(R.id.imgNextArrow2);
        imgNextArrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_1Welcome.this,_3Login.class);
                startActivity(intent);
            }
        });

    }


}