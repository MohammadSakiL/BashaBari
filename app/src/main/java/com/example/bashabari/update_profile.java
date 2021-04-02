package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
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

public class update_profile extends AppCompatActivity {

    private DatabaseReference ownerReference;
    private TextInputEditText update_name, update_phone, update_NID, update_password;
    String _USERNAME, _PHOHNENUMBER, _NID, _PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        ownerReference = FirebaseDatabase.getInstance().getReference("Owner Database");

        update_name = findViewById(R.id.update_name);
        update_phone = findViewById(R.id.update_phone);
        update_NID = findViewById(R.id.update_NID);
        update_password = findViewById(R.id.update_password);

        retriveData(readFromFile("111pho111.txt").trim());
    }

    private void retriveData(final String phone_no) {
        ownerReference.child(phone_no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    ownerInfo userInfo = snapshot.getValue(ownerInfo.class);

                    _USERNAME = userInfo.getName();
                    _PHOHNENUMBER = userInfo.getPhone_no();
                    _NID = userInfo.getNid_no();
                    _PASSWORD = userInfo.getPassword();

                    update_name.setText(_USERNAME);
                    update_phone.setText(_PHOHNENUMBER);
                    update_NID.setText(_NID);
                    update_password.setText(_PASSWORD);


                } catch (Exception e) {
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void update(View view) {
        if (isNameChanged() || isPhoneNumberChanged() || isNidChnaged() || isPasswordChanged()) {
            FancyToast.makeText(update_profile.this, "Data has been updated", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

        } else {
            FancyToast.makeText(update_profile.this, "Already updated", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

        }
    }

    private boolean isPasswordChanged() {
        if (!_PASSWORD.equals(update_password.getText().toString())) {
            ownerReference.child(_PHOHNENUMBER).child("password").setValue(update_password.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isNidChnaged() {
        if (!_NID.equals(update_NID.getText().toString())) {
            ownerReference.child(_PHOHNENUMBER).child("nid_no").setValue(update_NID.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean isNameChanged() {
        if (!_USERNAME.equals(update_name.getText().toString())) {
            ownerReference.child(_PHOHNENUMBER).child("name").setValue(update_name.getText().toString());
            return true;
        } else {
            return false;
        }

    }

    public boolean isPhoneNumberChanged() {
        if (!_PHOHNENUMBER.equals(update_phone.getText().toString())) {
            ownerReference.child(_PHOHNENUMBER).child("phone_no").setValue(update_phone.getText().toString());
            return true;
        } else {
            return false;
        }
    }


    private String readFromFile(String File_Name) {
        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 = openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis0 != null) {
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