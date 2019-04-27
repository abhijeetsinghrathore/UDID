package com.example.abhijeetsingh.udid;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class Profile_FillDetailsFragment extends Fragment {


    ImageView dob;
    EditText DOB;
    EditText firstname;
    EditText lastname;
    Button saveButton;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile__fill_details, container, false);
        dob=(ImageView)v.findViewById(R.id.calendarID);
        DOB=(EditText)v.findViewById(R.id.dobID) ;
        firstname=(EditText)v.findViewById(R.id.firstnameID);
        lastname=(EditText)v.findViewById(R.id.lastnameID);
        saveButton=(Button)v.findViewById(R.id.saveButtonID);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();



//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String firstnametext=firstname.getText().toString().trim();
//                String lastnametext=lastname.getText().toString().trim();
//                String dobtext=DOB.getText().toString().trim();
//
//                USER user=new USER(firstnametext,lastnametext,dobtext);
//
//                FirebaseUser currentuser= firebaseAuth.getCurrentUser();
//
//                databaseReference.child(currentuser.getUid()).setValue(user);
//                Toast.makeText(getActivity(),"information saved...",Toast.LENGTH_SHORT).show();
//
//
//                firstname.setFocusable(false);
//                firstname.setClickable(false);
//
//                lastname.setFocusable(false);
//                lastname.setClickable(false);
//
//                DOB.setFocusable(false);
//                DOB.setClickable(false);
//
//
//
//
//            }
//        });


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                DOB.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();

            }
        });



        return v;




    }

}
