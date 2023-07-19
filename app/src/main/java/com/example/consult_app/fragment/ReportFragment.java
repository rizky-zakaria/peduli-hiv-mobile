package com.example.consult_app.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.consult_app.R;

public class ReportFragment extends Fragment {

    CardView cardPerjalanan, cardKondisi, cardKonsumsi, cardAlarm;
    Fragment fragment;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report, container, false);

        cardKondisi = v.findViewById(R.id.cardKondisi);
        cardAlarm = v.findViewById(R.id.cardAlarm);
        cardKonsumsi = v.findViewById(R.id.cardKonsumsi);

        cardKonsumsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new MedkitFragment())
                        .commit();
            }
        });

        cardAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new AlarmFragment())
                        .commit();
            }
        });

        cardKondisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new KondisiFragment())
                        .commit();
            }
        });

        return v;
    }
}