package com.codecanyon.percentage.Supporting;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.codecanyon.percentage.Backend.cgpaList;
import com.codecanyon.percentage.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CgpaAdapter extends RecyclerView.Adapter<CgpaAdapter.ViewHolder> {
    Context context;
    List<String> semesterList;
    private CgpaAdapter.AdapterCallback mAdapterCallback;
    cgpaList cList;
    boolean outOfToggle;

    LayoutInflater inflater;
    public CgpaAdapter(Context context, List<String>semesterList, boolean outOfToggle,CgpaAdapter.AdapterCallback callback){
        this.context=context;
        this.semesterList=semesterList;
        this.mAdapterCallback = callback;
        cList=new cgpaList(context);
        this.outOfToggle=outOfToggle;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CgpaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.cgpa_adapter,parent,false);
        return new CgpaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CgpaAdapter.ViewHolder holder, int position) {

        if (outOfToggle){
            holder.sgpa.setHint("Out Of 10");
        }else {
            holder.sgpa.setHint("Out Of 5");
        }
        holder.semesterName.setText(semesterList.get(position));
        holder.sgpa.setText(String.valueOf(cgpaList.SGPA.get(position)));

        holder.semesterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cgpaList.SEMESTER_NAME.set(position,holder.semesterName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                cgpaList.SEMESTER_NAME.set(position,holder.semesterName.getText().toString());
            }
        });
        holder.sgpa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.sgpa.getText().toString().isEmpty()){
                    cgpaList.SGPA.set(position,0.0);
                }else {
                    cgpaList.SGPA.set(position,Double.parseDouble(holder.sgpa.getText().toString()));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.sgpa.getText().toString().isEmpty()){
                    cgpaList.SGPA.set(position,0.0);
                }else {
                    cgpaList.SGPA.set(position,Double.parseDouble(holder.sgpa.getText().toString()));

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
        return semesterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EditText semesterName,sgpa;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            semesterName=itemView.findViewById(R.id.semester1);
            sgpa=itemView.findViewById(R.id.spga);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
