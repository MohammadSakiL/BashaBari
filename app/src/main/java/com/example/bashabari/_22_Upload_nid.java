package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class _22_Upload_nid extends AppCompatActivity {

    private CardView selectNid;
    private Button uploadNid;
    private ImageView nidImageView;

    final int REQ = 1;
    private Bitmap bitmap;
    private String downloadUrl;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__22__upload_nid);

        selectNid = findViewById(R.id.selectGalleryImage22);
        uploadNid = findViewById(R.id.btnUploadNid22);
        nidImageView = findViewById(R.id.galleryImageView22);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Nid Image");
        storageReference = FirebaseStorage.getInstance().getReference().child("NID");

        pd = new ProgressDialog(this);
        pd.setTitle("Uploading Nid image");
        pd.setMessage("Please wait....");

        selectNid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadNid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap == null) {
                    Toast.makeText(_22_Upload_nid.this,"Select an image",Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage();
                }
            }
        });

    }

    private void uploadImage() {
        pd.show();
        //Compress image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage = baos.toByteArray();

        final StorageReference filePath;
        filePath = storageReference.child(finalImage+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData(readFromFile("111pho111.txt").trim());
                                }
                            });

                        }
                    });
                }
            }
        });


    }

    private void uploadData(String phone_number) {

        databaseReference.child(phone_number).setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FancyToast.makeText(_22_Upload_nid.this, "Successfully upload", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                pd.dismiss();
                Intent intent = new Intent(_22_Upload_nid.this,_6_User_menu.class);
                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FancyToast.makeText(_22_Upload_nid.this, "Upload Falied", Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();

            }
        });
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK){
            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {

            }

            nidImageView.setImageBitmap(bitmap);

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