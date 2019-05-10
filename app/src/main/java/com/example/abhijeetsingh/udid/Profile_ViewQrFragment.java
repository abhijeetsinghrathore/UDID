package com.example.abhijeetsingh.udid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Profile_ViewQrFragment extends Fragment {

    ImageView generatedQR;

    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile__view_qr, container, false);


        firebaseAuth= FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        user=firebaseAuth.getCurrentUser();

        String UID=user.getUid();


        generatedQR=(ImageView)v.findViewById(R.id.generatedQR);


        StorageReference QRRef = storageReference.child("QRcodes/"+UID+".jpg");



        GlideApp.with(v.getContext()).load(QRRef).into(generatedQR);



        return v;
    }


}
