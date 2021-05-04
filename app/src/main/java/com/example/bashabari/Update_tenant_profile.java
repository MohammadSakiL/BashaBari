package com.example.bashabari;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Update_tenant_profile extends AppCompatActivity {

    private DatabaseReference tenantReference,databaseReference;
    private StorageReference storageReference;
    private TextInputEditText update_tenant_name, update_tenant_phone, update_tenant_NID, update_tenant_password;
    private ImageView update_tenant_image;
    private Button update_tenant_Nid_image,upload_image;

    String _USERNAME, _PHOHNENUMBER, _NID, _PASSWORD;

    final int REQ = 1;
    private Bitmap bitmap;
    private String downloadUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tenant_profile);

        tenantReference = FirebaseDatabase.getInstance().getReference("Tenant Database");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tenant Profile Image");
        storageReference = FirebaseStorage.getInstance().getReference().child("Gallery");

        update_tenant_name = findViewById(R.id.update_tenant_name);
        update_tenant_phone = findViewById(R.id.update_tenant_phone);
        update_tenant_NID = findViewById(R.id.update_tenant_NID);
        update_tenant_password = findViewById(R.id.update_tenant_password);
        update_tenant_image = findViewById(R.id.update_tenant_image);
        update_tenant_Nid_image = findViewById(R.id.update_tenant_Nid_image);
        upload_image = findViewById(R.id.btn_tenant_image);

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap == null)
                {
                    FancyToast.makeText(Update_tenant_profile.this, "Please select an image", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                }else {
                    uploadImage();
                }
            }
        });

        update_tenant_Nid_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update_tenant_profile.this,_22_Upload_nid.class);
                startActivity(intent);
            }
        });

        update_tenant_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        retriveImage(readFromFile("111pho111.txt").trim());

        retriveData(readFromFile("111pho111.txt").trim());



    }

    private void uploadImage() {
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
        databaseReference = databaseReference.child(_PHOHNENUMBER);

        databaseReference.setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FancyToast.makeText(Update_tenant_profile.this, "Image Upload Succesfully", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                Intent intent = new Intent(Update_tenant_profile.this,_6_User_menu.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FancyToast.makeText(Update_tenant_profile.this, "Image Upload Falied", Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();


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

            update_tenant_image.setImageBitmap(bitmap);

        }
    }


    private void retriveImage(final String phone_no) {
        databaseReference.child(phone_no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    try {
                        String link = snapshot.getValue(String.class);
                        Picasso.get().load(link).into(update_tenant_image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    FancyToast.makeText(Update_tenant_profile.this, "Upload Profile image", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void retriveData(final String phone_no) {
        tenantReference.child(phone_no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    tenantInfo userInfo = snapshot.getValue(tenantInfo.class);

                    _USERNAME = userInfo.getName();
                    _PHOHNENUMBER = userInfo.getPhone_no();
                    _NID = userInfo.getNid_no();
                    _PASSWORD = userInfo.getPassword();

                    update_tenant_name.setText(_USERNAME);
                    update_tenant_phone.setText(_PHOHNENUMBER);
                    update_tenant_NID.setText(_NID);
                    update_tenant_password.setText(_PASSWORD);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateTenant(View view) {




        if (isNameChanged() || isPhoneNumberChanged() || isNidChnaged() || isPasswordChanged()) {
            FancyToast.makeText(Update_tenant_profile.this, "Data has been updated", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

        } else {
            FancyToast.makeText(Update_tenant_profile.this, "Already updated", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

        }
    }

    private boolean isPasswordChanged() {
        if (!_PASSWORD.equals(update_tenant_password.getText().toString())) {
            tenantReference.child(_PHOHNENUMBER).child("password").setValue(update_tenant_password.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isNidChnaged() {
        if (!_NID.equals(update_tenant_NID.getText().toString())) {
            tenantReference.child(_PHOHNENUMBER).child("nid_no").setValue(update_tenant_NID.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean isNameChanged() {
        if (!_USERNAME.equals(update_tenant_name.getText().toString())) {
            tenantReference.child(_PHOHNENUMBER).child("name").setValue(update_tenant_name.getText().toString());
            return true;
        } else {
            return false;
        }

    }

    public boolean isPhoneNumberChanged() {
        if (!_PHOHNENUMBER.equals(update_tenant_phone.getText().toString())) {
            tenantReference.child(_PHOHNENUMBER).child("phone_no").setValue(update_tenant_phone.getText().toString());
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