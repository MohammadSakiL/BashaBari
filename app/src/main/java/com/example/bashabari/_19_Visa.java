package com.example.bashabari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.braintreepayments.cardform.view.CardForm;

public class _19_Visa extends AppCompatActivity {

    private CardForm cardForm;
    private Button pay;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__19__visa);

        CardForm cardForm = findViewById(R.id.card_form);
        Button pay = findViewById(R.id.btnPay);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(_19_Visa.this);

        back = findViewById(R.id.arrow_btn_19);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_19_Visa.this, _14_PayBill.class);
                startActivity(intent);
            }
        });




        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_19_Visa.this, _14_PayBill.class);
                startActivity(intent);
            }
        });



    }

}

