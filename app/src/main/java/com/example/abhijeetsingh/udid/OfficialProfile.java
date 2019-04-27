package com.example.abhijeetsingh.udid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class OfficialProfile extends AppCompatActivity {


    Button scanButton;
    TextView scannedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_profile);


        scanButton=(Button)findViewById(R.id.ScanButtonID);
        scannedText=(TextView)findViewById(R.id.ScannedTextID);

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
                scannedText.setText(result.getContents());

            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }
}
