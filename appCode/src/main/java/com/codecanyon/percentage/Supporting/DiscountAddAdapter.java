package com.codecanyon.percentage.Supporting;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codecanyon.percentage.Backend.DiscountList;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiscountAddAdapter extends RecyclerView.Adapter<DiscountAddAdapter.ViewHolder> {
    Context context;
    List<String> itemNameList;
    private DiscountAddAdapter.AdapterCallback mAdapterCallback;
    DiscountList discountList;
    boolean removeDiscount;

    LayoutInflater inflater;
    public DiscountAddAdapter(Context context, List<String>itemNameList,boolean removeDiscount, DiscountAddAdapter.AdapterCallback callback){
        this.context=context;
        this.removeDiscount=removeDiscount;
        this.itemNameList=itemNameList;
        this.mAdapterCallback = callback;
        discountList=new DiscountList(context);
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DiscountAddAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.discount_add_element,parent,false);
        return new DiscountAddAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountAddAdapter.ViewHolder holder, int position) {


        holder.itemName.setText(itemNameList.get(position));
        holder.actualValue.setText(String.valueOf(DiscountList.actualValueList.get(position)));
        if(!removeDiscount){
            holder.discountValue.setText(String.valueOf(DiscountList.discountValueList.get(position)));
        }

        holder.qty.setText(String.valueOf(DiscountList.quantityList.get(position)));

        holder.itemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DiscountList.itemNameList.set(position,holder.itemName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                DiscountList.itemNameList.set(position,holder.itemName.getText().toString());
            }
        });
        holder.actualValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.actualValue.getText().toString().isEmpty()){
                    DiscountList.actualValueList.set(position,0.0);
                }else {
                    DiscountList.actualValueList.set(position,Double.parseDouble(holder.actualValue.getText().toString()));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.actualValue.getText().toString().isEmpty()) {
                    DiscountList.actualValueList.set(position, 0.0);
                } else {
                    DiscountList.actualValueList.set(position, Double.parseDouble(holder.
                            actualValue.getText().toString()));
                }
            }
        });
        holder.discountValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.discountValue.getText().toString().isEmpty()){
                    DiscountList.discountValueList.set(position,0.0);
                }else {
                    DiscountList.discountValueList.set(position,Double.parseDouble(holder.discountValue.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.discountValue.getText().toString().isEmpty()){
                    DiscountList.discountValueList.set(position,0.0);
                }else {
                    DiscountList.discountValueList.set(position,Double.parseDouble(holder.discountValue.getText().toString()));
                }
            }
        });
        holder.qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.qty.getText().toString().isEmpty()){
                    DiscountList.quantityList.set(position,0);
                }else {
                    DiscountList.quantityList.set(position,Integer.parseInt(holder.qty.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.qty.getText().toString().isEmpty()){
                    DiscountList.quantityList.set(position,0);
                }else {
                    DiscountList.quantityList.set(position,Integer.parseInt(holder.qty.getText().toString()));
                }
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountList.removeValue(position);
                mAdapterCallback.onMethodCallback();
            }
        });
    }

    public interface AdapterCallback {
        void onMethodCallback();
    }

    @Override
    public int getItemCount() {
        return itemNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EditText itemName,actualValue,discountValue,qty;
        TextView currency1,currency2;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.itemName);
            currency1=itemView.findViewById(R.id.currency1);
            currency2=itemView.findViewById(R.id.currency2);
            actualValue=itemView.findViewById(R.id.autualValue);
            discountValue=itemView.findViewById(R.id.discountValue);
            delete=itemView.findViewById(R.id.delete);
            qty= itemView.findViewById(R.id.qty);
            currency2.setText(Constants.CURRENCY_VALUE);
            currency1.setText(Constants.CURRENCY_VALUE);
            if (removeDiscount){
                discountValue.setText("");
                discountValue.setEnabled(false);
            }
        }
    }
}
