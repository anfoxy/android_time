package com.example.timelab.ui.fragment;


import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timelab.MainActivity;
import com.example.timelab.R;
import com.example.timelab.ui.AlarmActivity;
import com.example.timelab.ui.model.AlarmModel;
import com.example.timelab.ui.adapter.AdapterAlarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AlarmsFragment extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private AdapterAlarm.OnStateClickListener stateClickListener;
    private Date date ;
    private ArrayList<AlarmModel> alarms = new ArrayList<>(MainActivity.myDBManager.set());
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alarms, container, false);

        Button t =  root.findViewById(R.id.alarm_button);

        recyclerView = root.findViewById(R.id.list1);
        t.setInputType(InputType.TYPE_NULL);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_time);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TimePicker time = (TimePicker) dialog.findViewById(R.id.t1);
                time.setIs24HourView(true);
                //кнопка добавление даты
                Button bt = (Button) dialog.findViewById(R.id.bSet);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        calendar.set(Calendar.MINUTE, time.getCurrentMinute());
                        calendar.set(Calendar.HOUR_OF_DAY, time.getCurrentHour());

                        date = calendar.getTime();

                        if (!alarms.contains(new AlarmModel(sdf.format(date).toString(), calendar.getTimeInMillis()))) {
                            MainActivity.myDBManager.insert_TABLE_ALARM(sdf.format(date).toString(), calendar.getTimeInMillis());
                            Long time = calendar.getTimeInMillis();
                            alarms.add(new AlarmModel(sdf.format(date), time));

                            Intent intent = new Intent(getActivity(), AlarmActivity.class);
                            intent.setAction("" + time);
                            PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, intent, 0);
                            AlarmManager manager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                            manager.setExact(AlarmManager.RTC_WAKEUP, time, pi);
                            AdapterAlarm adapter = new AdapterAlarm(getActivity(), alarms, stateClickListener);
                            recyclerView.setAdapter(adapter);
                            Toast.makeText(getContext(), "Будильник установлен на " + sdf.format(date), Toast.LENGTH_SHORT).show();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (!Settings.canDrawOverlays(getContext())) {
                                    Intent intent3 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                            Uri.parse("package:" + getContext().getPackageName()));
                                    startActivity(intent3);
                                }
                            }
                        } else {
                            Toast toast = Toast.makeText(getActivity(),
                                    "такой будильник уже существует", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        stateClickListener = new AdapterAlarm.OnStateClickListener() {
            @Override
            public void onStateClick(int position) {
                Toast.makeText(getContext(), alarms.get(position).getDate() , Toast.LENGTH_SHORT).show();
                AlarmManager manager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getActivity(),  AlarmActivity.class);
                intent.setAction(""+alarms.get(position).getTime());
                PendingIntent pendingIntent = PendingIntent.getActivity( getContext(), 0, intent, 0);
                manager.cancel(pendingIntent);
                MainActivity.myDBManager.delete_alarm(alarms.get(position).getDate());
                alarms.remove(position);
                AdapterAlarm adapter = new AdapterAlarm(getActivity(),alarms,stateClickListener);
                recyclerView.setAdapter(adapter);


            }
        };
        AdapterAlarm adapter = new AdapterAlarm(getActivity(), alarms,stateClickListener);
        recyclerView.setAdapter(adapter);

        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < alarms.size(); i++) {
            try {
                String string1 = alarms.get(i).getDate() + ":00";
                Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(time1);
                calendar1.add(Calendar.DATE, 1);

                Calendar dateAndTime = Calendar.getInstance();

                String someRandomTime = "" + dateAndTime.get(Calendar.HOUR_OF_DAY) + ":"
                        + dateAndTime.get(Calendar.MINUTE) + ":" + dateAndTime.get(Calendar.SECOND);
                Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(d);
                calendar3.add(Calendar.DATE, 1);

                Date x = calendar3.getTime();
                if (x.after(calendar1.getTime())) {
                    MainActivity.myDBManager.delete_alarm(alarms.get(i).getDate());
                    alarms.remove(alarms.indexOf(alarms.get(i)));
                    i--;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        AdapterAlarm adapter = new AdapterAlarm(getActivity(), alarms, stateClickListener);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {

    }
}