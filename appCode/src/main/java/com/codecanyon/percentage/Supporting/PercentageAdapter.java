package com.codecanyon.percentage.Supporting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codecanyon.percentage.Backend.percentageTsgList;
import com.codecanyon.percentage.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PercentageAdapter extends RecyclerView.Adapter<PercentageAdapter.ViewHolder> {
    percentageTsgList pList;
    LayoutInflater inflater;
    List<Double>percent;
    private OnItemClickListener onItemClickListener;

    public PercentageAdapter(Context context, List<Double> percent, OnItemClickListener onItemClickListener){
        inflater=LayoutInflater.from(context);
        this.percent=percent;
        this.onItemClickListener = onItemClickListener;
        pList=new percentageTsgList(context);
    }

    @NonNull
    @Override
    public PercentageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.percentageadapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PercentageAdapter.ViewHolder holder, int position) {
        Decimals decimals=new Decimals();
        holder.percentageText.setText(decimals.roundOfToTwo(percent.get(position))+" %");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (percent.size()>position) {
                    pList.removeValue(percent.get(position));
                    onItemClickListener.update();
                }
            }
        });
        holder.percentageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (percent.size()>position) {
                     onItemClickListener.onItemClicked(percent.get(position));
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClicked( Double object);
        void update( );
    }

    @Override
    public int getItemCount() {
        return percent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout use;
        TextView percentageText;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            use=itemView.findViewById(R.id.use);
            percentageText=itemView.findViewById(R.id.percentValue);
            delete=itemView.findViewById(R.id.delete);

        }
    }
}
