package com.example.assignment1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String mPhoneNumber = "";                //String to save the typed phone number
    private Button[] mButtons = new Button[12];             //All the digit buttons
    private final int[] mButtonIds= {
            R.id.button11, R.id.button12, R.id.button13,
            R.id.button21, R.id.button22, R.id.button23,
            R.id.button31, R.id.button32, R.id.button33,
            R.id.button41, R.id.button42, R.id.button43,
    };

    /*
     * Function to start phone call with entered number
     */
    public void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+mPhoneNumber));
        startActivity(intent);
    }

    /*
     * If needed to request persmission to access the call action, if permission was granted, make the call
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            makeCall();
        }
    }

    /*
     * Function called when app is launched. Assigns all onClickListeners for the buttons
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mNumberTextView = findViewById(R.id.number);
        Button mBack = findViewById(R.id.back);
        mBack.setOnClickListener(view -> {
            if(mPhoneNumber.length() > 0){
                mPhoneNumber = mPhoneNumber.substring(0, mPhoneNumber.length() - 1);
                mNumberTextView.setText(mPhoneNumber);
            }
        });

        Button mCall = findViewById(R.id.buttonCall);
        mCall.setOnClickListener(view -> {
            if(Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
            } else {
                makeCall();
            }
        });


        for (int i = 0; i < 12; i++) {
            final int v = i;
            mButtons[i] = findViewById(mButtonIds[i]);
            mButtons[i].setOnClickListener(view -> {
                mPhoneNumber += mButtons[v].getText();
                mNumberTextView.setText(mPhoneNumber);
            });
        }
    }
}
