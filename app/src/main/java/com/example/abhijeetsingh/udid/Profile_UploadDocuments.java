package com.example.abhijeetsingh.udid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.app.Activity.RESULT_OK;


public class Profile_UploadDocuments extends Fragment {


    Button chose;
    Button upload;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST=234;
    Uri filepath;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    ImageView profileimage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile__upload_documents, container, false);


        chose=(Button)v.findViewById(R.id.choseButtonID);
        upload=(Button)v.findViewById(R.id.uploadButtonID);
        profileimage=(ImageView)v.findViewById(R.id.profile_imageID);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        final String email=user.getEmail();

        storageReference= FirebaseStorage.getInstance().getReference();



        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"select and image"),PICK_IMAGE_REQUEST);

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (filepath != null) {

                    final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    StorageReference riversRef = storageReference.child("images/"+email+".jpg");

                    riversRef.putFile(filepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    Task<Uri> downloadUrl = storageReference.getDownloadUrl();
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),"File Uploaded",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),"File Upload failed "+exception.getMessage(),Toast.LENGTH_SHORT).show();

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




        return  v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            filepath=data.getData();

            try
            {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);

                Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,200,200,true);

                profileimage.setImageBitmap(scaledBitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
