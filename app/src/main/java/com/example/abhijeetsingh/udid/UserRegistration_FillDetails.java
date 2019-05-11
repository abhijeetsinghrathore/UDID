package com.example.abhijeetsingh.udid;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class UserRegistration_FillDetails extends AppCompatActivity {


    Button nextButton;
    Button saveButton;

    EditText firstname,lastname,dateofbirth,state,city,address,phone;

    String firstnametext,lastnametext,dateofbirthtext,statetext,citytext,addresstext,phonetext,token;


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;


    ProgressDialog progressDialog;


    ImageView DOBimage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration__fill_details);

        this.getSupportActionBar().hide();


        nextButton=(Button)findViewById(R.id.nextButtonID);
        saveButton=(Button)findViewById(R.id.saveButtonID);
        firstname=(EditText)findViewById(R.id.firstnameID);
        lastname=(EditText)findViewById(R.id.lastnameID);
        dateofbirth=(EditText)findViewById(R.id.dobID);
        state=(EditText)findViewById(R.id.StateID);
        city=(EditText)findViewById(R.id.cityID);
        address=(EditText)findViewById(R.id.addressID);
        phone=(EditText)findViewById(R.id.phoneID);

        DOBimage=(ImageView)findViewById(R.id.calendarID);


        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();


        progressDialog=new ProgressDialog(this);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserRegistration_FillDetails.this,UserRegistration_UploadDocuments.class);
                startActivity(i);
                finish();
            }
        });

        DOBimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserRegistration_FillDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateofbirth.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });




        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstnametext=firstname.getText().toString().trim();
                lastnametext=lastname.getText().toString().trim();
                dateofbirthtext=dateofbirth.getText().toString().trim();
                statetext=state.getText().toString().trim();
                citytext=city.getText().toString().trim();
                addresstext=address.getText().toString().trim();
                phonetext=phone.getText().toString().trim();

                token=(SharedPrefManager.getInstance(UserRegistration_FillDetails.this).getToken());

                USER user=new USER(firstnametext,lastnametext,dateofbirthtext,statetext,citytext,addresstext,phonetext,token);

                FirebaseUser currentUser=firebaseAuth.getCurrentUser();
                databaseReference.child(currentUser.getUid()).setValue(user);
                Toast.makeText(UserRegistration_FillDetails.this,"information saved...",Toast.LENGTH_SHORT).show();


                firstname.setFocusable(false);
                firstname.setClickable(false);

                lastname.setFocusable(false);
                lastname.setClickable(false);

                dateofbirth.setFocusable(false);
                dateofbirth.setClickable(false);

                state.setFocusable(false);
                state.setClickable(false);

                city.setFocusable(false);
                city.setClickable(false);

                address.setFocusable(false);
                address.setClickable(false);

                phone.setFocusable(false);
                phone.setClickable(false);




            }
        });


    }
}
