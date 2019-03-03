package com.example.ui_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 2.1 Layout Demo Activity
    private void openLayoutDemoActivity(View v, String layout) {
        Intent intent = new Intent(this, LayoutDemoActivity.class);
        intent.putExtra("layout", layout);
        startActivity(intent);
    }
    public void openLinearLayoutDemoActivity(View v) {
        openLayoutDemoActivity(v, "layoutdemo_linearlayout");
    }
    public void openConstraintLayoutDemoActivity(View v) {
        openLayoutDemoActivity(v, "layoutdemo_constraintlayout");
    }

    // 2.4 Views und Event Demo
    public void openViewsDemoActivity(View v) {
        Intent intent = new Intent(this, ViewsDemoActivity.class);
        startActivity(intent);
    }
}
