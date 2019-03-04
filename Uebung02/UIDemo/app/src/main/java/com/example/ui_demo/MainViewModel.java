package com.example.ui_demo;

import android.arch.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private int counter = 0;
    public int incrementCounter() { return ++counter; }
    public int getCounter() { return counter; }
}
