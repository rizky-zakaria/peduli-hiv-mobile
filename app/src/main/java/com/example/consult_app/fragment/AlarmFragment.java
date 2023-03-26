package com.example.consult_app.fragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.consult_app.R;
import com.example.consult_app.api.ApiInterfaces;
import com.example.consult_app.api.ApiServer;
import com.example.consult_app.model.ResponseAlarm;
import com.example.consult_app.utils.MyBroadcastReceiver;
import com.example.consult_app.utils.SharedPrefManager;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmFragment extends Fragment {
    private TextView timePicker;
    private Button btnTimer;
    private int jam, menit;
    String id;
    SharedPrefManager sharedPrefManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        sharedPrefManager = new SharedPrefManager(getActivity());
        id = sharedPrefManager.getSpId();
        timePicker = v.findViewById(R.id.waktu);
//        btnTimer = v.findViewById(R.id.btnTimer);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                getWaktuAlarm(id);
//            }
//        }, 1000);

//        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                jam = hourOfDay;
//                menit = minute;
//            }
//        });

//        btnTimer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Set Alarm " + jam + " : " + menit, Toast.LENGTH_SHORT).show();
//                setTimer();
//                notification();
//            }
//        });

        return v;
    }

    private  void getWaktuAlarm(String id){
        ApiInterfaces apiInterfaces = ApiServer.konekRetrofit().create(ApiInterfaces.class);
        Call<ResponseAlarm> call = apiInterfaces.getAlarm(id);
        call.enqueue(new Callback<ResponseAlarm>() {
            @Override
            public void onResponse(Call<ResponseAlarm> call, Response<ResponseAlarm> response) {
//                Log.d("TAG", "onResponse: "+response.body().getData().getJam());
                if (response.isSuccessful()){
                    timePicker.setText(response.body().getData().getJam()+":"+response.body().getData().getMenit());
                    jam = response.body().getData().getJam();
                    menit = response.body().getData().getMenit();
                    setTimer(jam, menit);
                    notification();
                }else {
                    Toast.makeText(getContext(), "Tidak Ada Alaram Pada Bulan Ini", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAlarm> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notification() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Minum Obat";
            String description = "Hey, Waktunya Minum Obat!!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel  = new NotificationChannel("Notify", name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = ContextCompat.getSystemService(getActivity(), NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setTimer(int jam, int menit) {
        AlarmManager alarmManager  = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        Date date = new Date();

        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_alarm.setTime(date);

//        Log.d("TAG", "setTimer: "+menit);
        cal_alarm.set(Calendar.HOUR_OF_DAY, jam);
        cal_alarm.set(Calendar.MINUTE, menit);
        cal_alarm.set(Calendar.SECOND, 0);

        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(getActivity(), MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, i, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),pendingIntent);
    }
}