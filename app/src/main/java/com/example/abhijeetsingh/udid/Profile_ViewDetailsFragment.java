package com.example.abhijeetsingh.udid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile_ViewDetailsFragment extends Fragment {

    TextView firstName,lastName,dateOfBirth,state,city,address,contact;

    Button viewDetails;

    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile__view_details, container, false);

        firstName=(TextView)v.findViewById(R.id.getfirstnameID);
        lastName=(TextView)v.findViewById(R.id.getlastnameID);
        dateOfBirth=(TextView)v.findViewById(R.id.getdobID);
        state=(TextView)v.findViewById(R.id.getstateID);
        city=(TextView)v.findViewById(R.id.getcityID);
        address=(TextView)v.findViewById(R.id.getaddressID);
        contact=(TextView)v.findViewById(R.id.getcontactID);


        viewDetails=(Button)v.findViewById(R.id.viewDetailsButtonID);

        firebaseAuth=FirebaseAuth.getInstance();



        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user=firebaseAuth.getCurrentUser();

                reference= FirebaseDatabase.getInstance().getReference().child(user.getUid());

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FirebaseUser user=firebaseAuth.getCurrentUser();

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
        });










        return v;
    }


}
