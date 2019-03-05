package com.example.ui_demo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    String modules[];
    MainViewModel viewModel;
    TextView counterLabel;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2.5 Auswahl ermöglichen: Spinner
        modules = getResources().getStringArray(R.array.modules);

        Spinner spinner = (Spinner) findViewById(R.id.moduleSpinner);
        spinner.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String module = modules[position];
                        // 2.5 Do a Toast
                        Toast toast = Toast.makeText(getApplicationContext(),
                                module,
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        if (! "".equals(module)) toast.show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // NOP
                    }
                }
        );
        // 2.6 Zustands-Zwischenspeicherung
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        counterLabel = findViewById(R.id.main_counterLabel);
        updateCounterLabel();

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

    // 2.6 Counter
    public void updateCounterLabel () {
        counterLabel.setText("Counter: " + viewModel.getCounter() % 11);
    }

    // called on button click (see main.xml)
    public void increaseInternalCounter(View button) {
        viewModel.incrementCounter();
        updateCounterLabel();
    }
}
