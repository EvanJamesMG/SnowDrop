package com.example.evanjames.animationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private MyAnimView snowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         snowView = (MyAnimView) findViewById(R.id.mysnow);

    }

}
