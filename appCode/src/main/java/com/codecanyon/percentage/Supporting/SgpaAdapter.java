package com.codecanyon.percentage.Supporting;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.codecanyon.percentage.Backend.SpgaList;
import com.codecanyon.percentage.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SgpaAdapter extends RecyclerView.Adapter<SgpaAdapter.ViewHolder> {
    Context context;
    List<String> subjectNameList;
    private SgpaAdapter.AdapterCallback mAdapterCallback;
    SpgaList cList;


    LayoutInflater inflater;
    public SgpaAdapter(Context context, List<String>subjectNameList,SgpaAdapter.AdapterCallback callback){
        this.context=context;
        this.subjectNameList=subjectNameList;
        this.mAdapterCallback = callback;
        cList=new SpgaList(context);
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SgpaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.spga_adapter,parent,false);
        return new SgpaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SgpaAdapter.ViewHolder holder, int position) {

        holder.subjectName.setText(subjectNameList.get(position));
        holder.credit.setText(String.valueOf(SpgaList.CREDIT.get(position)));
        holder.obtainedMarks.setText(String.valueOf(SpgaList.OBTAINED_MARKS.get(position)));
        holder.totalMarks.setText(String.valueOf(SpgaList.TOTAL_MARKS.get(position)));

        holder.subjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SpgaList.SUBJECT_NAME.set(position,holder.subjectName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                SpgaList.SUBJECT_NAME.set(position,holder.subjectName.getText().toString());

            }
        });
        holder.credit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.credit.getText().toString().isEmpty()){
                    SpgaList.CREDIT.set(position,0);
                }else {
                    SpgaList.CREDIT.set(position,Integer.parseInt(holder.credit.getText().toString()));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.credit.getText().toString().isEmpty()){
                    SpgaList.CREDIT.set(position,0);
                }else {
                    SpgaList.CREDIT.set(position,Integer.parseInt(holder.credit.getText().toString()));

                }

            }
        });


        holder.obtainedMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.obtainedMarks.getText().toString().isEmpty()){
                    SpgaList.OBTAINED_MARKS.set(position,0.0);
                }else {
                    SpgaList.OBTAINED_MARKS.set(position,Double.parseDouble(holder.obtainedMarks.getText().toString()));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.obtainedMarks.getText().toString().isEmpty()){
                    SpgaList.OBTAINED_MARKS.set(position,0.0);
                }else {
                    SpgaList.OBTAINED_MARKS.set(position,Double.parseDouble(holder.obtainedMarks.getText().toString()));

                }


            }
        });


        holder.totalMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.totalMarks.getText().toString().isEmpty()){
                    SpgaList.TOTAL_MARKS.set(position,0.0);
                }else {
                    SpgaList.TOTAL_MARKS.set(position,Double.parseDouble(holder.totalMarks.getText().toString()));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.totalMarks.getText().toString().isEmpty()){
                    SpgaList.TOTAL_MARKS.set(position,0.0);
                }else {
                    SpgaList.TOTAL_MARKS.set(position,Double.parseDouble(holder.totalMarks.getText().toString()));

                }

            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cList.removeValue(position);
                mAdapterCallback.onMethodCallback();
            }
        });
    }

    public interface AdapterCallback {
        void onMethodCallback();
    }

    @Override
    public int getItemCount() {
        return subjectNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EditText subjectName,credit,obtainedMarks,totalMarks;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.subjectName);
            obtainedMarks=itemView.findViewById(R.id.obtainedMarks);
            totalMarks=itemView.findViewById(R.id.totalMarks);
            credit=itemView.findViewById(R.id.credit);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
