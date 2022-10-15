package com.example.timelab.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.timelab.R;
import com.example.timelab.ui.model.AlarmModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterAlarm extends RecyclerView.Adapter<AdapterAlarm.ViewHolder>{

    private final LayoutInflater inflater;

    private ArrayList<AlarmModel> mass;
    private Context context;

    private final OnStateClickListener onClickListener;

public interface OnStateClickListener{
    void onStateClick(int position);
}

    public AdapterAlarm(Context context, ArrayList<AlarmModel> states, OnStateClickListener onClickListener) {
        this.mass = states;
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlarmModel str = mass.get(position);

        holder.nameView.setText("Будильник:" + str.getDate());


        holder.button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                onClickListener.onStateClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mass.size();
    }
public static class ViewHolder extends RecyclerView.ViewHolder {
    final TextView nameView;
    final Button button;
    ViewHolder(View view){
        super(view);
        nameView = (TextView) view.findViewById(R.id.text);
        button = (Button) view.findViewById(R.id.alarm_button);
    }
}
}