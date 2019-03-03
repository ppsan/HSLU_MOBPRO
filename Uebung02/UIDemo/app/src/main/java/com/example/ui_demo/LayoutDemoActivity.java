package com.example.ui_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LayoutDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2.1 get layout from setExtra
        String layout = getIntent().getStringExtra("layout");
        if ("layoutdemo_linearlayout".equals(layout)) {
            setContentView(R.layout.layoutdemo_linearlayout);
        } else if ("layoutdemo_constraintlayout".equals(layout)) {
            setContentView(R.layout.layoutdemo_constraintlayout);
        }
    }
}
