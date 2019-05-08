package com.example.abhijeetsingh.udid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OfficialProfile extends AppCompatActivity {


    Button scanButton;
    String UID;

    DatabaseReference reference;

    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    android.widget.ImageView profileImage,certificateImage;


    TextView firstName,lastName,dateOfBirth,state,city,address,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_profile);


        storageReference=FirebaseStorage.getInstance().getReference();


        scanButton=(Button)findViewById(R.id.ScanButtonID);

        firstName=(TextView)findViewById(R.id.getfirstnameID);
        lastName=(TextView)findViewById(R.id.getlastnameID);
        dateOfBirth=(TextView)findViewById(R.id.getdobID);
        state=(TextView)findViewById(R.id.getstateID);
        city=(TextView)findViewById(R.id.getcityID);
        address=(TextView)findViewById(R.id.getaddressID);
        contact=(TextView)findViewById(R.id.getcontactID);


        profileImage=(android.widget.ImageView)findViewById(R.id.ProfileImageID);
        certificateImage=(android.widget.ImageView)findViewById(R.id.CertificateImageID);


        final Activity activity=this;


        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result!=null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(this,"You cancelled the scanning",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                UID=result.getContents();
                reference= FirebaseDatabase.getInstance().getReference().child(UID);

                StorageReference profileRef = storageReference.child("images/"+UID+"Profile.jpg");
                StorageReference documentsRef = storageReference.child("Documents/"+UID+"CERTI.jpg");

                 GlideApp.with(OfficialProfile.this).load(profileRef).into(profileImage);
                 GlideApp.with(OfficialProfile.this).load(documentsRef).into(certificateImage);


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String firstNameText=dataSnapshot.child("mfirstname").getValue().toString();
                        String lastNameText=dataSnapshot.child("mlastname").getValue().toString();
                        String dateOfBirthText=dataSnapshot.child("mdob").getValue().toString();
                        String stateText=dataSnapshot.child("mState").getValue().toString();
                        String cityText=dataSnapshot.child("mCity").getValue().toString();
                        String addressText=dataSnapshot.child("maddress").getValue().toString();
                        String contactText=dataSnapshot.child("mPhone").getValue().toString();


                        firstName.setText(firstNameText);
                        lastName.setText(lastNameText);
                        dateOfBirth.setText(dateOfBirthText);
                        state.setText(stateText);
                        city.setText(cityText);
                        address.setText(addressText);
                        contact.setText(contactText);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }
}
