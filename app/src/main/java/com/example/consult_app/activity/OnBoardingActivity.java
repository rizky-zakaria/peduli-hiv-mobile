package com.example.consult_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.consult_app.R;
import com.xcode.onboarding.OnBoarder;
import com.xcode.onboarding.OnBoardingPage;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        List<OnBoardingPage> pages = new ArrayList<>();
        pages.add(new OnBoardingPage(R.drawable.apps,"Consult-app","Aplikasi Untuk Penangan HIV"));
        pages.add(new OnBoardingPage(R.drawable.login,"Please Login!","Masuk Dan Nikmati Kenyaman Dalam Berkonsultasi"));
        pages.add(new OnBoardingPage(R.drawable.solusi,"Solution","Dapatkan Solusi Terbaik Dari Ahlinya"));
        OnBoarder.startOnBoarding(this, pages,null);
    }
}