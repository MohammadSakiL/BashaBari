package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

public class _3Login extends AppCompatActivity {

    private EditText edtPhoneNumber3,edtPassword3;
    private CheckBox loginAsOwner3;
    private Button btnLogin3;
    private TextView txtRegister3;
    private DatabaseReference ownerReference;
    private DatabaseReference tenantReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._3login);
        edtPhoneNumber3 = findViewById(R.id.edtPhoneNumber3);
        edtPassword3 = findViewById(R.id.edtPassword3);
        txtRegister3 = findViewById(R.id.txtRegister3);
        loginAsOwner3 = findViewById(R.id.loginAsOwner3);
        btnLogin3 = findViewById(R.id.btnLogin3);

        ownerReference = FirebaseDatabase.getInstance().getReference("Owner Database");
        tenantReference = FirebaseDatabase.getInstance().getReference("Tenant Database");

         if(!isConnected(_3Login.this))
            buildDialog(_3Login.this).show();

        txtRegister3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginAsOwner3.isChecked()){
                    Intent intent = new Intent(_3Login.this,_4Register.class);
                    startActivity(intent);
                }
                else {
                    FancyToast.makeText(_3Login.this,"You can only register as owner",Toast.LENGTH_LONG,FancyToast.INFO,true).show();
                }
            }
        });



        btnLogin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getPhone_no,getPassword;
                getPhone_no = edtPhoneNumber3.getText().toString().trim();
                getPassword = edtPassword3.getText().toString().trim();

                if(loginAsOwner3.isChecked()){
                    if(getPhone_no.isEmpty())
                        edtPhoneNumber3.setError("Input your phone number");
                    else if(getPassword.isEmpty())
                        edtPassword3.setError("Input your password");
                    else{
                        ownerLogin(getPhone_no,getPassword);

                    }

                }
                else {
                    FancyToast.makeText(_3Login.this,"You can only register as owner",Toast.LENGTH_LONG,FancyToast.INFO,true).show();
                }
            }
        });



    }


    private void ownerLogin(final String getPhone_no, final String getPassword) {
        try {
            ownerReference.child(getPhone_no).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        ownerInfo userInfo = snapshot.getValue(ownerInfo.class);
                        if(getPhone_no.equals(userInfo.getPhone_no()) && getPassword.equals(userInfo.getPassword())){
                            FancyToast.makeText(_3Login.this,"Login Successful",Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            Intent intent = new Intent(_3Login.this, _5OwnerMenu.class);
                            startActivity(intent);
                        }
                        else {
                            FancyToast.makeText(_3Login.this,"Invalid Phone Number or Password",Toast.LENGTH_LONG,FancyToast.ERROR,true).show();

                        }
                    }catch (Exception e){
                        FancyToast.makeText(_3Login.this,"Invalid Phone Number or Password",Toast.LENGTH_LONG,FancyToast.ERROR,true).show();


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
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else
                return false;
        } else
            return false;
    }


    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this.");

        return builder;
    }


}