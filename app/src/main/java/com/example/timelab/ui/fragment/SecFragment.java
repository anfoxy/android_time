package com.example.timelab.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timelab.R;
import com.example.timelab.ui.model.Date;
import com.example.timelab.ui.adapter.AdapterSec;

import java.util.ArrayList;
import java.util.List;


public class SecFragment extends Fragment implements View.OnClickListener{
    private AdapterSec.OnStateClickListener stateClickListener;
    private Handler h;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sec, container, false);


        Date d = new Date();
        EditText text = root.findViewById(R.id.L4TimeText);
        String t = Integer.toString(d.hour) + ":" +
                Integer.toString(d.min) + ":" + Integer.toString(d.sec);
        text.setText(t);
        Handler.Callback hc = new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                d.addSecond();
                String time = Integer.toString(d.hour) + ":" +
                        Integer.toString(d.min) + ":" + Integer.toString(d.sec);
                text.setText(time);
                h.sendEmptyMessageDelayed(0, 1000);
                return true;
            }
        };
        h = new Handler(hc);


        Button timer = root.findViewById(R.id.L4Bttn);
        timer.setOnClickListener(view -> {
            if (h.hasMessages(0))
                h.removeMessages(0);
            else
                h.sendEmptyMessageDelayed(0, 1000);
        });

        Button reset = root.findViewById(R.id.Reset);
        reset.setOnClickListener(view -> {
           d.setTime(0,0,0);
            String time = Integer.toString(d.hour) + ":" +
                    Integer.toString(d.min) + ":" + Integer.toString(d.sec);
            text.setText(time);
        });
        List<String> list = new ArrayList<>();

        final RecyclerView recyclerView = root.findViewById(R.id.list1);
        Button l2 = root.findViewById(R.id.L4FixBttn);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String time = Integer.toString(d.hour) + ":" +
                        Integer.toString(d.min) + ":" + Integer.toString(d.sec);
                list.add(time);
                AdapterSec adapter = new AdapterSec(getActivity(), list,stateClickListener);
                recyclerView.setAdapter(adapter);
            }
        });

        stateClickListener = new AdapterSec.OnStateClickListener() {
            @Override
            public void onStateClick(int position) {

                list.remove(position);
                AdapterSec adapter = new AdapterSec(getActivity(),list,stateClickListener);
                recyclerView.setAdapter(adapter);


            }
        };
        AdapterSec adapter = new AdapterSec(getActivity(), list,stateClickListener);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onClick(View view) {

    }



}




