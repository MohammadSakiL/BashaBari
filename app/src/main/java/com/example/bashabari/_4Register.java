package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;


public class _4Register extends AppCompatActivity {
    private EditText edtName4,edtAdress4,edtNid4,edtPhoneNumber4,edtPassword4;
    private TextView txtBackToLogin4;
    private Button btnSignup4;

    private DatabaseReference ownerReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__4_register);

        ownerReference = FirebaseDatabase.getInstance().getReference("Owner Database");

        edtName4 = findViewById(R.id.edtName4);
        edtAdress4 = findViewById(R.id.edtAdress4);
        edtNid4 = findViewById(R.id.edtNid4);
        edtPhoneNumber4 = findViewById(R.id.edtPhoneNumber4);
        edtPassword4 = findViewById(R.id.edtPassword4);
        txtBackToLogin4 = findViewById(R.id.txtBackToLogin4);
        btnSignup4 = findViewById(R.id.btnSignup4);

        txtBackToLogin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_4Register.this,_3Login.class);
                startActivity(intent);
            }
        });


        btnSignup4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = edtName4.getText().toString().trim();
                final String address = edtAdress4.getText().toString().trim();
                final String nid_no = edtNid4.getText().toString().trim();
                final String phone_no = edtPhoneNumber4.getText().toString().trim();
                final String password = edtPassword4.getText().toString().trim();

                if(!name.isEmpty() && !address.isEmpty() && !nid_no.isEmpty() && !phone_no.isEmpty() && !password.isEmpty())
                {
                    if(password.length() < 3)
                        edtPassword4.setError("Input Minimum 6 Character Password");
                    else if(phone_no.length() < 3)
                        edtPhoneNumber4.setError("Invalid Phone Number");
                    else savaToDataBase(name,address,nid_no,phone_no,password);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Empty Input Field",Toast.LENGTH_SHORT).show();

                    if(name.isEmpty())
                        edtName4.setError("Input your name.");
                    else if(address.isEmpty())
                        edtAdress4.setError("Input your address");
                    else if(nid_no.isEmpty())
                        edtNid4.setError("Input your National ID Number");
                    else if(password.isEmpty())
                        edtPhoneNumber4.setError("Input your phone number");
                    else if(phone_no.isEmpty())
                        edtPassword4.setError("Input a passwordField");
                }
            }
        });


    }

    private void savaToDataBase(final String name,final String address,final String nid_no,final String phone_no,final String password) {
        try {
            ownerReference.child(phone_no).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        ownerInfo userInfo = snapshot.getValue(ownerInfo.class);
                        if(phone_no.equals(userInfo.getPhone_no())){
                            edtPhoneNumber4.setError("Phone number already exist");
                        }

                    }catch (Exception e){
                        ownerInfo userInfo = new ownerInfo(address,name,nid_no,password,phone_no);
                        ownerReference.child(phone_no).setValue(userInfo);


                        FancyToast.makeText(_4Register.this,"Successfully Registered",Toast.LENGTH_LONG,FancyToast.INFO,true).show();

                        Intent intent = new Intent(_4Register.this,_3Login.class);
                        startActivity(intent);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}