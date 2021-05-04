package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class _26_View_nid extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ImageView nidImage,back_arrow;
    private String PHONENUMBER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__26__view_nid);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Nid Image");
        nidImage = findViewById(R.id.nidImage26);
        back_arrow = findViewById(R.id.back_arrow_btn_26);

        PHONENUMBER = getIntent().getStringExtra("phoneNumber");

        retriveImage(PHONENUMBER);
    }

    private void retriveImage(final String phone_no) {
        databaseReference.child(phone_no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if(snapshot.exists()) {
                        try {
                            String link = snapshot.getValue(String.class);
                            Picasso.get().load(link).into(nidImage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        FancyToast.makeText(_26_View_nid.this, "Something went wrong", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}