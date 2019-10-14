package com.example.listviewexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button arrayAdapterButton;
    Button customAdapterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayAdapterButton = findViewById(R.id.arrayadapter);
        customAdapterButton = findViewById(R.id.customAdapterButton);

        arrayAdapterButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.d("AdapterButtonListener", "clicked");
                Intent intent = new Intent(MainActivity.this,ArrayAdapterActivity.class);
                startActivity(intent);
            }

        });


        customAdapterButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.d("customAdapterButton", "clicked");
                Intent intent = new Intent(MainActivity.this,CustomAdapterActivity.class);
                startActivity(intent);
            }

        });

    }
}
