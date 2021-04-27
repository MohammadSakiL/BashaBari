package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;


public class _20_Bill_owner extends AppCompatActivity {

    private Spinner spinner_month;
    private EditText homeRent,waterBill,gasBill,otherBill,totalBill;
    private Button backHome,sendBill;

    private DatabaseReference databaseReference,dbRef;

    private String month,_NAME,_PHONENUMBER;

    private String hRent,wBill,gBill,oBill,tBill;

    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__20__bill_owner);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bill Database");


        spinner_month = findViewById(R.id.spnMonth);
        homeRent = findViewById(R.id.edtHomeRent20);
        waterBill = findViewById(R.id.edtWaterBill20);
        gasBill = findViewById(R.id.edtGasBill20);
        otherBill = findViewById(R.id.edtOtherBill20);
        totalBill = findViewById(R.id.edtTotalBill20);
        backHome = findViewById(R.id.btnHome20);
        sendBill = findViewById(R.id.btnSendBill20);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            _NAME = extras.getString("name");
        }


        if(extras != null){
            _PHONENUMBER = extras.getString("phone_number");
        }





        String[] items = new String[]{"Select Month","January","February","March","April","May","June","July",
        "august","September","October","November","December"};
        spinner_month.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));


        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = spinner_month.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_20_Bill_owner.this,_5OwnerMenu.class);
                startActivity(intent);
            }
        });

        sendBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hRent = homeRent.getText().toString();
                wBill = waterBill.getText().toString();
                gBill = gasBill.getText().toString();
                oBill = otherBill.getText().toString();



                if(month.equals("Select Month")){
                    Toast.makeText(_20_Bill_owner.this,"Select a month",Toast.LENGTH_SHORT).show();
                }else if(hRent.isEmpty()){
                    homeRent.setError("Enter Home Rent");
                    homeRent.requestFocus();
                }else if(wBill.isEmpty()){
                    waterBill.setError("Enter water bill");
                    waterBill.requestFocus();
                }else if(gBill.isEmpty()){
                    gasBill.setError("Enter gas bill");
                    gasBill.requestFocus();
                }else if(oBill.isEmpty()){
                    otherBill.setError("Enter others bill");
                    otherBill.requestFocus();
                }
                else {
                    total = Integer.parseInt(hRent) + Integer.parseInt(wBill) + Integer.parseInt(gBill) + Integer.parseInt(oBill);

                    tBill = String.valueOf(total);
                    totalBill.setText(tBill);

                    uploadBill(month,hRent,wBill,gBill,oBill,tBill,_PHONENUMBER);
                }


            }
        });


    }

    private void uploadBill(final String month,final String hRent,final String wBill,final String gBill,final String oBill,final String tBill,final String phoneNumber) {

        BillInfo billInfo = new BillInfo(month,hRent,wBill,gBill,oBill,tBill,phoneNumber);

        databaseReference.child(month).setValue(billInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FancyToast.makeText(getApplicationContext(), "Bill send to "+_NAME, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FancyToast.makeText(getApplicationContext(), "Something Went Wrong", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();

            }
        });

    }


}