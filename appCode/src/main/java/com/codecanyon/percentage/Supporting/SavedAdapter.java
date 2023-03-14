package com.codecanyon.percentage.Supporting;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codecanyon.percentage.Backend.SavedData;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.Pages.CalculationActivity;
import com.codecanyon.percentage.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    Context context;
    List<SavedData> savedData=new ArrayList<>();
    private AdapterCallback mAdapterCallback;

    LayoutInflater inflater;
    public SavedAdapter(Context context, List<SavedData>savedData, AdapterCallback callback){
        this.context=context;
        this.savedData=savedData;
        this.mAdapterCallback = callback;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.savr_lsit_adapter,parent,false);
        return new SavedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position) {
        SavedData saved= savedData.get(position);
        holder.fileName.setText(saved.getFileName()+"");

        holder.category.setText(saved.getCategory()+"");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavedData toOpen= savedData.get(position);
                if(toOpen.getCategory().equals("CGPA")){
                    Intent intent=new Intent(context, CalculationActivity.class);
                    intent.putExtra("openingSaved",true);
                    intent.putExtra("function",toOpen.getCategory());
                    intent.putExtra("filePosition",position);
                    context.startActivity(intent);
                }else if(toOpen.getCategory().equals("Marks")){
                    Intent intent=new Intent(context, CalculationActivity.class);
                    intent.putExtra("openingSaved",true);
                    intent.putExtra("function",Constants.titlesPer[0]);
                    int i=0;

                    i=Constants.CGPA_SAVED_LIST.size();

                    intent.putExtra("filePosition",position-i);
                    context.startActivity(intent);
                }else if(toOpen.getCategory().equals("SGPA")){
                    Intent intent=new Intent(context, CalculationActivity.class);
                    intent.putExtra("openingSaved",true);
                    intent.putExtra("function",toOpen.getCategory());
                    int i=0;
                    i=Constants.CGPA_SAVED_LIST.size();
                    i+=Constants.MARKS_SAVED_LIST.size();

                    intent.putExtra("filePosition",position-i);
                    context.startActivity(intent);
                }else if(toOpen.getCategory().equals("Discount")){
                    Intent intent=new Intent(context, CalculationActivity.class);
                    intent.putExtra("function",Constants.titlesPer[4]);
                    intent.putExtra("openingSaved",true);
                    int i=0;
                    i=Constants.CGPA_SAVED_LIST.size();
                    i+=Constants.MARKS_SAVED_LIST.size();
                    i+=Constants.SGPA_SAVED_LIST.size();

                    intent.putExtra("filePosition",position-i);
                    context.startActivity(intent);
                }else if(toOpen.getCategory().equals("Quadratic")){
                    Intent intent=new Intent(context, CalculationActivity.class);
                    intent.putExtra("function","Quadratic");
                    intent.putExtra("openingSaved",true);
                    int i=0;
                    i=Constants.CGPA_SAVED_LIST.size();
                    i+=Constants.MARKS_SAVED_LIST.size();
                    i+=Constants.SGPA_SAVED_LIST.size();
                    i+=Constants.DISCOUNT_SAVED_LIST.size();
                    intent.putExtra("filePosition",position-i);
                    context.startActivity(intent);
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavedData toDelete= savedData.get(position);
                if(toDelete.getCategory().equals("CGPA")){
                    SQLiteDatabase myDatabase= context.openOrCreateDatabase("CGPA",MODE_PRIVATE,null);
                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS cgpaTable(semesterNameArray TEXT,sgpa TEXT,fileName TEXT,cgpaOutOf INTEGER,id INTEGER PRIMARY KEY)");
                    myDatabase.execSQL("DELETE FROM cgpaTable WHERE fileName='"+toDelete.getFileName()+"'");
                    Constants.CGPA_SAVED_LIST.remove(position);
                }else if(toDelete.getCategory().equals("Marks")){
                    SQLiteDatabase myDatabase= context.openOrCreateDatabase("Marks",MODE_PRIVATE,null);
                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS marksTable(subjectArray TEXT,markObtainedArray TEXT,totalMarksArray TEXT,fileName TEXT,id INTEGER PRIMARY KEY)");
                    myDatabase.execSQL("DELETE FROM marksTable WHERE fileName='"+toDelete.getFileName()+"'");
                    int i=0;
                    i=Constants.CGPA_SAVED_LIST.size();

                    Constants.MARKS_SAVED_LIST.remove(position-i);
                }else if(toDelete.getCategory().equals("SGPA")){
                    SQLiteDatabase myDatabase= context.openOrCreateDatabase("SGPA",MODE_PRIVATE,null);
                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS sgpaTable(subjectArray TEXT,obtainedMarksArray TEXT,totalMarksArray TEXT,credit TEXT,calculationRule INTEGER,sgpaOutOf INTEGER,fileName TEXT,id INTEGER PRIMARY KEY)");
                    myDatabase.execSQL("DELETE FROM sgpaTable WHERE fileName='"+toDelete.getFileName()+"'");
                    int i=0;
                    i=Constants.CGPA_SAVED_LIST.size();
                    i+=Constants.MARKS_SAVED_LIST.size();

                    Constants.SGPA_SAVED_LIST.remove(position-i);
                }else if(toDelete.getCategory().equals("Discount")){
                    SQLiteDatabase myDatabase= context.openOrCreateDatabase("Discount",MODE_PRIVATE,null);
                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS discountTable(itemNameArray TEXT,actualValueArray TEXT,discountValueArray TEXT,addOverAllDiscountBoolean INTEGER,overAllDiscount TEXT,percentageBoolean INTEGER,fileName TEXT,id INTEGER PRIMARY KEY)");
                    myDatabase.execSQL("DELETE FROM discountTable WHERE fileName='"+toDelete.getFileName()+"'");
                    int i=0;
                    i=Constants.CGPA_SAVED_LIST.size();
                    i+=Constants.MARKS_SAVED_LIST.size();
                    i+=Constants.SGPA_SAVED_LIST.size();

                    Constants.DISCOUNT_SAVED_LIST.remove(position-i);
                }
                mAdapterCallback.onMethodCallback();
            }
        });
    }

    public interface AdapterCallback {
        void onMethodCallback();
    }

    @Override
    public int getItemCount() {
        return savedData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView fileName,category;
        ImageView delete;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.layout);
            fileName=itemView.findViewById(R.id.title);
            category=itemView.findViewById(R.id.category);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
