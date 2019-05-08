package com.example.abhijeetsingh.udid;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class Profile_GenerateQR extends Fragment {


    Button generateButton;
    Button saveButton;
    Button uploadButton;

    ImageView qrImage;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile__generate_qr, container, false);


       generateButton=(Button)v.findViewById(R.id.generatebuttonID);
       qrImage=(ImageView)v.findViewById(R.id.qrID);
       firebaseAuth=FirebaseAuth.getInstance();
       firebaseUser=firebaseAuth.getCurrentUser();

       final String encodingtext=firebaseUser.getUid();



       generateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               MultiFormatWriter writer=new MultiFormatWriter();
               try
               {
                   BitMatrix bitMatrix=writer.encode(encodingtext, BarcodeFormat.QR_CODE,300,300);
                   BarcodeEncoder encoder=new BarcodeEncoder();
                   Bitmap b=encoder.createBitmap(bitMatrix);
                   qrImage.setImageBitmap(b);
               }
               catch (WriterException e)
               {
                   e.printStackTrace();

               }





           }
       });






        return v;
    }

}
