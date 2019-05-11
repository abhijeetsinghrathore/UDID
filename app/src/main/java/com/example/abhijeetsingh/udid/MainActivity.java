package com.example.abhijeetsingh.udid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button user;
    Button official;


    private static int SPLASH_SCREEN_TIMEOUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();


        user=(Button)findViewById(R.id.USERButtonID);
        official=(Button)findViewById(R.id.OFFICIALButtonID);


        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,UserRegistration.class);
                startActivity(i);
            }
        });


        official.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,OfficialSignIn.class);
                startActivity(i);

            }
        });
    }
}
