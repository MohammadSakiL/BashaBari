package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class _27_Tolet extends AppCompatActivity {

    private Spinner spinner;
    private CardView image;
    private EditText rent,flat_no,contact_number,location;

    private Button post,home;

    private StorageReference storageReference;
    private DatabaseReference databaseReference,dbRef;

    final int REQ = 1;
    private Bitmap bitmap;
    private String downloadUrl = "",type;

    private String _price,_location,_number,_flatNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__27__tolet);

        image = findViewById(R.id.selectHomeImage27);
        spinner = findViewById(R.id.spntype27);
        rent = findViewById(R.id.edtRent27);
        flat_no = findViewById(R.id.edtFlatNo27);
        contact_number = findViewById(R.id.edtPhoneNumber27);
        location = findViewById(R.id.edtLocation27);
        post = findViewById(R.id.btnPost27);
        home = findViewById(R.id.btnHome27);

        storageReference = FirebaseStorage.getInstance().getReference().child("Home Picture");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Rent Database");


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        String[] items = new String[]{"Select Category","Sell","Rent"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_27_Tolet.this,_5OwnerMenu.class);
                startActivity(intent);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _price = rent.getText().toString();
                _location = location.getText().toString();
                _number = contact_number.getText().toString();
                _flatNo = flat_no.getText().toString();

                if(_price.isEmpty()){
                    rent.setError("Price is empty");
                    rent.requestFocus();
                }else if(_location.isEmpty()){
                    rent.setError("Location is empty");
                    rent.requestFocus();
                }else if(_flatNo.isEmpty()) {
                    rent.setError("Flat no is empty");
                    rent.requestFocus();
                } else if(_number.isEmpty()) {
                    rent.setError("Contact number is empty");
                    rent.requestFocus();
                }else if(type.equals("Select Category")){
                    FancyToast.makeText(_27_Tolet.this,"Please select a teacher category",FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();

                }else if(bitmap == null){
                    uploadData();
                }else {
                    uploadImage();
                }

            }
        });

    }




    private void uploadImage() {
        //Compress image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImage = baos.toByteArray();

        final StorageReference filePath;
        filePath = storageReference.child(finalImage + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData();
                                }
                            });

                        }
                    });
                }
            }
        });


    }

    private void uploadData() {

        dbRef = databaseReference.child(type);
        final String uniqueKey = dbRef.push().getKey();

        ToletInfo toletInfo = new ToletInfo(downloadUrl,_price,type,_flatNo,_location,_number,uniqueKey);

        dbRef.child(uniqueKey).setValue(toletInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                FancyToast.makeText(_27_Tolet.this,"Data upload successful",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                Intent intent = new Intent(_27_Tolet.this,_5OwnerMenu.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FancyToast.makeText(_27_Tolet.this,"Data upload successful",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

            }
        });

    }



    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {

            }


        }
    }

}