package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class _15_Bkash extends AppCompatActivity {

    private ImageView back;
    private ImageView done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__15__bkash);

        back = findViewById(R.id.arrow_btn_15);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_15_Bkash.this,_14_PayBill.class);
                startActivity(intent);
            }
        });
    }
}