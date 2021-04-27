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
    private ImageView nidImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__26__view_nid);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Nid Image");
        nidImage = findViewById(R.id.nidImage26);

        retriveImage(readFromFile("111pho111.txt").trim());
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
                        FancyToast.makeText(_26_View_nid.this, "Spmething went wrong", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

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