package com.codecanyon.percentage.Supporting;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.codecanyon.percentage.Backend.MarksList;
import com.codecanyon.percentage.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarksAddAddapter extends RecyclerView.Adapter<MarksAddAddapter.ViewHolder> {
    Context context;
    List<String> subjectNameList=new ArrayList<>();
    private AdapterCallback mAdapterCallback;
    MarksList marksList;

    LayoutInflater inflater;
    public MarksAddAddapter(Context context, List<String>subjectNameList,AdapterCallback callback){
        this.context=context;
        this.subjectNameList=subjectNameList;
        this.mAdapterCallback = callback;
        marksList=new MarksList(context);
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MarksAddAddapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.marks_add_element,parent,false);
        return new MarksAddAddapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarksAddAddapter.ViewHolder holder, int position) {
        holder.subjectName.setText(subjectNameList.get(position));

        holder.totalMarks.setText(String.valueOf(MarksList.totalMarksList.get(position)));
        holder.marksObtained.setText(String.valueOf(MarksList.marksObtainedList.get(position)));

        holder.subjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MarksList.subjectList.set(position,holder.subjectName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                MarksList.subjectList.set(position,holder.subjectName.getText().toString());
            }
        });
        holder.marksObtained.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.marksObtained.getText().toString().isEmpty()){
                    MarksList.marksObtainedList.set(position,0.0);
                }else {
                    MarksList.marksObtainedList.set(position,Double.parseDouble(holder.marksObtained.getText().toString()));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.marksObtained.getText().toString().isEmpty()) {
                    MarksList.marksObtainedList.set(position, 0.0);
                } else {
                    MarksList.marksObtainedList.set(position, Double.parseDouble(holder.marksObtained.getText().toString()));
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
                    MarksList.totalMarksList.set(position,0);
                }else {
                    MarksList.totalMarksList.set(position,Integer.parseInt(holder.totalMarks.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.totalMarks.getText().toString().isEmpty()){
                    MarksList.totalMarksList.set(position,0);
                }else {
                    MarksList.totalMarksList.set(position,Integer.parseInt(holder.totalMarks.getText().toString()));
                }
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marksList.removeValue(position);
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
        EditText subjectName,marksObtained,totalMarks;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.subjectName);
            marksObtained=itemView.findViewById(R.id.markObtained);
            totalMarks=itemView.findViewById(R.id.totalMark);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
