package com.example.timelab.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.timelab.R;

import java.util.List;

public class AdapterSec extends RecyclerView.Adapter<AdapterSec.ViewHolder>{

    private final LayoutInflater inflater;

    private final List<String> mass;

    private Context context;

    private final OnStateClickListener onClickListener;

public interface OnStateClickListener{
    void onStateClick(int position);
}

    public AdapterSec(Context context, List<String> states, OnStateClickListener onClickListener) {
        this.mass = states;
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_sec, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String str = mass.get(position);

        holder.nameView.setText(str);


        holder.nameView.setOnClickListener(new View.OnClickListener(){
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

    ViewHolder(View view){
        super(view);
        nameView = (TextView) view.findViewById(R.id.text);

    }
}
}