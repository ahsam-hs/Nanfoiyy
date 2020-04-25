package com.ahsam.nanfoiy;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class WelcomeLogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_logo);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(WelcomeLogoActivity.this, MainActivity.class);
                WelcomeLogoActivity.this.startActivity(mainIntent);
                WelcomeLogoActivity.this.finish();
            }
        }, 3000);



    }



}
