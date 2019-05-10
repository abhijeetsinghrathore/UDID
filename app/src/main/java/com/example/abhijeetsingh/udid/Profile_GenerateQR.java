package com.example.abhijeetsingh.udid;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;


public class Profile_GenerateQR extends Fragment {


    Button generateButton;
    Button saveButton;
    Button uploadButton;

    ImageView qrImage;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    StorageReference storageReference;

    EditText key;

    Bitmap b;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile__generate_qr, container, false);


        generateButton=(Button)v.findViewById(R.id.generatebuttonID);
        saveButton=(Button)v.findViewById(R.id.saveQRButtonID);
        uploadButton=(Button)v.findViewById(R.id.uploadQRButtonID);

        qrImage=(ImageView)v.findViewById(R.id.qrID);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        final String encodingtext=firebaseUser.getUid();

        key= (EditText)v.findViewById(R.id.keyEditText);
        reference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
        storageReference= FirebaseStorage.getInstance().getReference();



        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String keyInDatabase = dataSnapshot.child("Key").getValue().toString().trim();
                        String keyEntered=key.getText().toString().trim();

                        if ((Boolean) keyInDatabase.equals(keyEntered)) {
                            //code to generate QR code

                            MultiFormatWriter writer=new MultiFormatWriter();
                            try
                            {
                                BitMatrix bitMatrix=writer.encode(encodingtext, BarcodeFormat.QR_CODE,300,300);
                                BarcodeEncoder encoder=new BarcodeEncoder();
                                b=encoder.createBitmap(bitMatrix);
                                qrImage.setImageBitmap(b);
                            }
                            catch (WriterException e)
                            {
                                e.printStackTrace();

                            }

                        } else {
                            Toast.makeText(getActivity(), "Failed! Key is incorrect", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateButton.setClickable(false);

            }
        });



        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(b!=null)
                {
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    String  UID=user.getUid();

                    StorageReference QRRef = storageReference.child("QRcodes/"+UID+".jpg");

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask=QRRef.putBytes(data);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getActivity(),"Upload Successfull",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),"Upload failed "+exception.getMessage(),Toast.LENGTH_SHORT).show();

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



        return v;
    }

}