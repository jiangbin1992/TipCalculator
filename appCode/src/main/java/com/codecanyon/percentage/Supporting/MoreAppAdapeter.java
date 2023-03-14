package com.codecanyon.percentage.Supporting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codecanyon.percentage.Backend.App;
import com.codecanyon.percentage.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoreAppAdapeter extends RecyclerView.Adapter<MoreAppAdapeter.ViewHolder> {


    LayoutInflater inflater;
    List<App> moreApps=new ArrayList<App>();
    Context context;

    public MoreAppAdapeter(Context context, List<App> moreApps){
        inflater=LayoutInflater.from(context);
        this.moreApps=moreApps;
        this.context=context;

    }

    @NonNull
    @Override
    public MoreAppAdapeter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.more_apps_element,parent,false);
        return new MoreAppAdapeter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreAppAdapeter.ViewHolder holder, int position) {
        App app=moreApps.get(position);
        holder.appName.setText(app.getAppName());
        holder.shortDiscription.setText(app.getShortDiscription());
        String image = app.getIcon();
        try {
            Picasso.get().load(image).into(holder.icon);
        } catch (Exception e) {

        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App newApp=moreApps.get(position);

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(newApp.getPlaystoreLink()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moreApps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView icon;
        TextView appName,shortDiscription;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appName=itemView.findViewById(R.id.appName);
            icon=itemView.findViewById(R.id.appIcon);
            shortDiscription=itemView.findViewById(R.id.shortDiscription);
            layout=itemView.findViewById(R.id.layout);

        }
    }
}
