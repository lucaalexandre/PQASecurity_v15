package com.example.pqasecurityv15;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void digital(View view){
        Intent intent = new Intent(this, FingerPrintActivity.class);
        startActivity(intent);
    }
    public void  oximetria(View view){
        Intent intent2 = new Intent(this, OximetriaActivity.class);
        startActivity(intent2);

    }



}
