package com.example.abhijeetsingh.udid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Profile_ViewDocumentsFragment extends Fragment {


    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    ImageView profileImage,certificateImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile__view_documents, container, false);



        firebaseAuth= FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        user=firebaseAuth.getCurrentUser();

        String email=user.getEmail();


        profileImage=(ImageView)v.findViewById(R.id.ProfileImageID);
        certificateImage=(ImageView)v.findViewById(R.id.CertificateImageID);




        StorageReference profileRef = storageReference.child("images/"+email+"Profile.jpg");
        StorageReference documentsRef = storageReference.child("Documents/"+email+"CERTI.jpg");


        Glide.with(v.getContext()).load(profileRef).into(profileImage);
        Glide.with(Profile_ViewDocumentsFragment.this).load(documentsRef).into(certificateImage);









        return  v;

    }

}
