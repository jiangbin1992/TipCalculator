package com.codecanyon.percentage.Supporting;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecanyon.percentage.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalculationAdapter extends RecyclerView.Adapter<CalculationAdapter.ViewHolder> {

    LayoutInflater inflater;
    String []titles;
    int[] images;

    public CalculationAdapter(Context context, String[] titles,int[] images){
        inflater=LayoutInflater.from(context);
        this.titles=titles;
        this.images=images;

    }

    @NonNull
    @Override
    public CalculationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.calculation_element,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalculationAdapter.ViewHolder holder, int position) {
        holder.calText.setText(titles[position]);
        holder.calIcon.setBackgroundResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView calText;
        ImageView calIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            calIcon=itemView.findViewById(R.id.calIcon);
            calText=itemView.findViewById(R.id.calText);
        }
    }
}
