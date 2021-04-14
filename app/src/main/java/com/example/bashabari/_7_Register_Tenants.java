package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class _7_Register_Tenants extends AppCompatActivity {


    private DatabaseReference tenantReference;
    private DatabaseReference ownerReference;

    private EditText edtName,edtFlat,edtNid,edtPhoneNumber,edtPassword;
    private Button btnAddTenants;
    private TextView txtownerHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__7__register__tenants);

        tenantReference = FirebaseDatabase.getInstance().getReference("Tenant Database");
        ownerReference = FirebaseDatabase.getInstance().getReference("Owner Database");

        edtName = findViewById(R.id.edtName7);
        edtFlat = findViewById(R.id.edtFlat7);
        edtNid = findViewById(R.id.edtNid7);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber7);
        edtPassword = findViewById(R.id.edtPassword7);

        txtownerHome = findViewById(R.id.txtownerHome7);

        btnAddTenants = findViewById(R.id.btnAddTenants7);

        txtownerHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_7_Register_Tenants.this,_5OwnerMenu.class);
                startActivity(intent);
            }
        });

        btnAddTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edtName.getText().toString().trim();
                final String address = edtFlat.getText().toString().trim();
                final String nid_no = edtNid.getText().toString().trim();
                final String phone_no = edtPhoneNumber.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();

                if( !name.isEmpty() && !address.isEmpty() && !nid_no.isEmpty() && !phone_no.isEmpty() && !password.isEmpty() ) {

                    if(password.length()<6)
                        edtPassword.setError("Input minimum 6 character password");
                    else if(phone_no.length() != 11)
                        edtPhoneNumber.setError("Invalid phone number");
                    else
                        saveToDatabase(name, address, nid_no, phone_no, password);
                }else{
                    Toast.makeText(getApplicationContext(),"Empty Input Field",Toast.LENGTH_SHORT).show();

                    if(name.isEmpty())
                        edtName.setError("Input your name.");
                    else if(address.isEmpty())
                        edtFlat.setError("Input your address");
                    else if(nid_no.isEmpty())
                        edtNid.setError("Input your National ID Number");
                    else if(phone_no.isEmpty())
                        edtPhoneNumber.setError("Input your address");
                    else if(password.isEmpty())
                        edtPassword.setError("Input a password");
                }

            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.page_layout_7);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_7_Register_Tenants.this, _7_Register_Tenants.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

    }

    private void saveToDatabase(final String name,final String address,final String nid_no, final String phone_no,final String password) {
        final String owner_no;
        owner_no = readFromFile("111pho111.txt").trim();

        try {
            tenantReference.child(phone_no).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        tenantInfo userInfo =snapshot.getValue(tenantInfo.class);
                        if (phone_no.equals(userInfo.getPhone_no())) {
                            edtPhoneNumber.setError("Phone number already exists");
                        }

                    }catch (Exception e){
                        tenantInfo usrinfo = new tenantInfo(address, name, nid_no, owner_no, password, phone_no);
                        String key = phone_no;
                        tenantReference.child(key).setValue(usrinfo);
                        FancyToast.makeText(_7_Register_Tenants.this,"Registration Done",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                        Intent intent1 = new Intent(_7_Register_Tenants.this, _5OwnerMenu.class);
                        startActivity(intent1);
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

    private String readFromFile(String File_Name){

        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 =openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while( (text = br.readLine()) != null ){
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis0 != null) {
                try {
                    fis0.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return st;

    }
}