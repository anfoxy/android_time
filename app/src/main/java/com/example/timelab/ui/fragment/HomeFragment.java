package com.example.timelab.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.timelab.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


public class HomeFragment extends Fragment implements View.OnClickListener{
    private Handler h;
    TextView h1,h2,m1,m2,s1,s2;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        h1 = root.findViewById(R.id.h1);
        h2 = root.findViewById(R.id.h2);

        m1 = root.findViewById(R.id.m1);
        m2 = root.findViewById(R.id.m2);

        s1 = root.findViewById(R.id.s1);
        s2 = root.findViewById(R.id.s2);

        Handler.Callback hc = new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                Calendar dateAndTime= Calendar.getInstance();

                h1.setText(""+dateAndTime.get(Calendar.HOUR_OF_DAY)/10);
                m1.setText(""+dateAndTime.get(Calendar.MINUTE)/10);
                s1.setText(""+dateAndTime.get(Calendar.SECOND)/10);

                h2.setText(""+dateAndTime.get(Calendar.HOUR_OF_DAY)%10);
                m2.setText(""+dateAndTime.get(Calendar.MINUTE)%10);
                s2.setText(""+ (dateAndTime.get(Calendar.SECOND))%10);

                h.sendEmptyMessageDelayed(0, 500);
                return true;
            }
        };
        h = new Handler(hc);
        h.sendEmptyMessageDelayed(0, 500);
        return root;
    }
    @Override
    public void onClick(View v) {

    }
}