package com.example.abhijeetsingh.udid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UserRegistration_UploadDocuments extends AppCompatActivity {


    Button nextButton;
    Button saveButton;

    Button choseProfile,choseMedicalCertificate,uploadProfile,uploadMedicalCertificate;


    StorageReference storageReference;
    private static final int PICK_PROFILE_IMAGE_REQUEST=234,PICK_CERTIFICATE_IMAGE_REQUEST=235;
    Uri PROFILEfilepath,CERTIFICATEfilepath;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    ImageView profileImage,medicalCertificateImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration__upload_documents);


        nextButton=(Button)findViewById(R.id.nextButtonID);
        saveButton=(Button)findViewById(R.id.saveButtonID);


        choseProfile=(Button)findViewById(R.id.choseProfileButtonID);
        choseMedicalCertificate=(Button)findViewById(R.id.certificatechoseButtonID);
        uploadProfile=(Button)findViewById(R.id.uploadProfileButtonID);
        uploadMedicalCertificate=(Button)findViewById(R.id.certificateuploadButtonID);
        storageReference= FirebaseStorage.getInstance().getReference();



        profileImage=(ImageView)findViewById(R.id.profile_imageID);
        medicalCertificateImage=(ImageView)findViewById(R.id.certificate_imageID);


        firebaseAuth=FirebaseAuth.getInstance();



        user=firebaseAuth.getCurrentUser();





        choseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"select and image"),PICK_PROFILE_IMAGE_REQUEST);

            }
        });


        choseMedicalCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"select and image"),PICK_CERTIFICATE_IMAGE_REQUEST);

            }
        });









        uploadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PROFILEfilepath != null) {

                    final ProgressDialog progressDialog=new ProgressDialog(UserRegistration_UploadDocuments.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    String  email=user.getEmail();

                    StorageReference profileRef = storageReference.child("images/"+email+"Profile.jpg");

                    profileRef.putFile(PROFILEfilepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    Task<Uri> downloadUrl = storageReference.getDownloadUrl();
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"File Uploaded",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"File Upload failed "+exception.getMessage(),Toast.LENGTH_SHORT).show();

                                    // ...
                                }


                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                                    progressDialog.setMessage(((int)progress)+"% uploaded...");
                                }
                            })

                    ;


                }

            }
        });


        uploadMedicalCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CERTIFICATEfilepath != null) {

                    final ProgressDialog progressDialog=new ProgressDialog(UserRegistration_UploadDocuments.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    String  email=user.getEmail();

                    StorageReference documentsRef = storageReference.child("Documents/"+email+"CERTI.jpg");

                    documentsRef.putFile(CERTIFICATEfilepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    Task<Uri> downloadUrl = storageReference.getDownloadUrl();
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"File Uploaded",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"File Upload failed "+exception.getMessage(),Toast.LENGTH_SHORT).show();

                                    // ...
                                }


                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                                    progressDialog.setMessage(((int)progress)+"% uploaded...");
                                }
                            })

                    ;


                }

            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                choseProfile.setClickable(false);
                choseMedicalCertificate.setClickable(false);
            }
        });






        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserRegistration_UploadDocuments.this,Profile.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_PROFILE_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            PROFILEfilepath=data.getData();

            try
            {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),PROFILEfilepath);

                Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,200,200,true);

                profileImage.setImageBitmap(scaledBitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        if(requestCode==PICK_CERTIFICATE_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            CERTIFICATEfilepath=data.getData();

            try
            {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),CERTIFICATEfilepath);

                Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,200,200,true);

                medicalCertificateImage.setImageBitmap(scaledBitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
