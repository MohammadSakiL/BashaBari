package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class _13SentMessage extends AppCompatActivity {
    private EditText tenantNo,message;
    private Button send;
    private TextView home;
    private DatabaseReference tenantReference;
    private DatabaseReference messageReference;
    String _Name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__13_sent_message);

        tenantReference = FirebaseDatabase.getInstance().getReference("Tenant Database");
        messageReference = FirebaseDatabase.getInstance().getReference("Message Database");


        tenantNo = findViewById(R.id.to13);
        message = findViewById(R.id.message13);
        send = findViewById(R.id.send13);
        home = findViewById(R.id.home13);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            _Name = extras.getString("name");
        }

        tenantNo.setText(_Name);



        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final String sendTo,sendMessage,date,owner_no;
                owner_no = readFromFile("111pho111.txt").trim();
                sendTo = tenantNo.getText().toString().trim();
                sendMessage = message.getText().toString();

                if(sendTo.isEmpty()){
                    tenantNo.setError("Enter a phone Number");
                }
                else {
                    date = getTodaysDate();
                    saveToDatabase(sendTo,owner_no,date,sendMessage);
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_13SentMessage.this,_5OwnerMenu.class);
                startActivity(intent);
            }
        });
    }

    private void saveToDatabase(final String sendTo,final String owner_no,final String date,final String sendMessage) {


        try {

            messageReference.child(sendTo).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messageInfo mInfo = new messageInfo(sendTo, owner_no, date, sendMessage);
                    messageReference.push().setValue(mInfo);
                    FancyToast.makeText(getApplicationContext(), "Message has been sent", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getTodaysDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy 'at' h:mm a");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        return dateString;
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





