package com.codecanyon.percentage.Supporting;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codecanyon.percentage.Backend.Fav;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    LayoutInflater inflater;
    String []titles;
    int[] images;
    SharedPreferences sharedPreferences,sharedPreferencesImage;
    Fav fav;

    public FavAdapter(Context context, String[] titles, int[] images){
        inflater=LayoutInflater.from(context);
        this.titles=titles;
        this.images=images;

        sharedPreferencesImage=context.getSharedPreferences(Constants.PACKAGE_NAME,Context.MODE_PRIVATE);
        sharedPreferences=context.getSharedPreferences(Constants.PACKAGE_NAME,Context.MODE_PRIVATE);
        fav=new Fav(context);
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.fav_element,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        holder.calText.setText(titles[position]);
        holder.calIcon.setBackgroundResource(images[position]);
        if(Fav.favList.contains(titles[position])){
            holder.favIcon.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
        }else{
            holder.favIcon.setBackgroundResource(R.drawable.ic_outline_favorite_border_24);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Fav.favList.contains(titles[position])){
                    fav.removeFromFavList(titles[position],images[position]);
                    holder.favIcon.setBackgroundResource(R.drawable.ic_outline_favorite_border_24);

                }else{
                    if(fav.addToFavList(titles[position],images[position])){
                        holder.favIcon.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    }

                }
                Gson gson = new Gson();
                String jsonText = gson.toJson(Fav.favList);
                sharedPreferences.edit().putString(Constants.FAV_TEXT, jsonText).apply();

                Gson gson1 = new Gson();
                String jsonText1 = gson1.toJson(Fav.favImageList);
                sharedPreferences.edit().putString(Constants.FAV_IMAGE, jsonText1).apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView calText;
        ImageView calIcon,favIcon;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            calIcon=itemView.findViewById(R.id.calIcon);
            calText=itemView.findViewById(R.id.calText);
            favIcon=itemView.findViewById(R.id.fav);
            layout=itemView.findViewById(R.id.layout);

        }
    }
}
