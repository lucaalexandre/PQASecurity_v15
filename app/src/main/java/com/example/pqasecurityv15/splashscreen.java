package com.example.pqasecurityv15;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class splashscreen extends AppCompatActivity {


    @Override
    protected void onCreate (Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.splashscreen);


        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        }, 3000);



    }
}
