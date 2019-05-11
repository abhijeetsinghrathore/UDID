package com.example.abhijeetsingh.udid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class OfficialSignIn extends AppCompatActivity {


    EditText officialemail;
    EditText officialpassword;
    Button officialsignInButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_sign_in);

        this.getSupportActionBar().hide();

        officialemail=(EditText)findViewById(R.id.officialemail_loginin);
        officialpassword=(EditText)findViewById(R.id.officialpass_loginin);
        officialsignInButton=(Button)findViewById(R.id.officialsigninbutton);
        firebaseAuth=FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            Intent i=new Intent(OfficialSignIn.this,OfficialProfile.class);
            startActivity(i);

        }


        officialsignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailtext=officialemail.getText().toString().trim();
                String passwordtext=officialpassword.getText().toString().trim();



                if(TextUtils.isEmpty(emailtext))
                {
                    Toast.makeText(OfficialSignIn.this,"enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(passwordtext) )
                {
                    Toast.makeText(OfficialSignIn.this,"enter password",Toast.LENGTH_SHORT).show();
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(OfficialSignIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                            Intent i=new Intent(OfficialSignIn.this,OfficialProfile.class);
                            startActivity(i);
                        }

                    }
                });

            }
        });

    }
}
