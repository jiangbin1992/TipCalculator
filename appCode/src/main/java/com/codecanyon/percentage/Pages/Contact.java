package com.codecanyon.percentage.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;

import java.util.List;

public class Contact extends AppCompatActivity {
    EditText name, email, message;
    ImageView instagramLink,linkdinLink,facebookLink,youtubeLink,twitterLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        message=findViewById(R.id.message);

        instagramLink=findViewById(R.id.instagram);
        linkdinLink=findViewById(R.id.linkdin);
        youtubeLink=findViewById(R.id.youtube);
        twitterLink=findViewById(R.id.twitter);
        facebookLink=findViewById(R.id.facebook);

        if(Constants.INSTAGRAM_LINK.isEmpty()){
            instagramLink.setVisibility(View.GONE);
        }
        if(Constants.LINKDIN_LINK.isEmpty()){
            linkdinLink.setVisibility(View.GONE);
        }
        if(Constants.FACEBOOK_LINK.isEmpty()){
            facebookLink.setVisibility(View.GONE);
        }
        if(Constants.YOUTUBE_LINK.isEmpty()){
            youtubeLink.setVisibility(View.GONE);
        }
        if(Constants.TWITTER_LINK.isEmpty()){
            twitterLink.setVisibility(View.GONE);
        }

        if(!Constants.LIGHT_THEME) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


    }

    public void sendMessage(View view) {

        if(!name.getText().toString().isEmpty()||!email.getText().toString().isEmpty()||!message.getText().toString().isEmpty()){
            Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
            selectorIntent.setData(Uri.parse("mailto:"));

            final Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.EMAIL_ADDRESS});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From "+getString(R.string.app_name)+" To the developer of the app "+ getString(R.string.app_name));
            emailIntent.putExtra(Intent.EXTRA_TEXT, "This is " + name.getText()+" with my email address "+email.getText().toString()+" "+message.getText().toString());
            emailIntent.setSelector( selectorIntent );

            startActivity(Intent.createChooser(emailIntent, "Send email..."));
            finish();
        }


    }

    public void social(View view) {
        String tag=(view.getTag().toString());
        Intent intent=new Intent(Intent.ACTION_VIEW);
        switch (tag){
            case "0":
                //Youtube
                Intent intent1 = new Intent(
                        Intent.ACTION_VIEW ,
                        Uri.parse(Constants.YOUTUBE_LINK));
                intent1.setComponent(new ComponentName("com.google.android.youtube","com.google.android.youtube.PlayerActivity"));

                PackageManager manager = getPackageManager();
                List<ResolveInfo> infos = manager.queryIntentActivities(intent1, 0);
                if (infos.size() > 0) {
                    startActivity(intent1);
                }else{
                    intent.setData(Uri.parse(Constants.YOUTUBE_LINK));
                    startActivity(intent);
                }
                break;
            case "1":
                //LinkdIn
                Uri uri1 = Uri.parse(Constants.LINKDIN_LINK);
                Intent likeIng1 = new Intent(Intent.ACTION_VIEW, uri1);

                likeIng1.setPackage("com.linkedin.android");

                try {
                    startActivity(likeIng1);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            (uri1)));
                }
                break;
            case "2":
                //instagram
                Uri uri2 = Uri.parse(Constants.INSTAGRAM_LINK);
                Intent likeIng2 = new Intent(Intent.ACTION_VIEW, uri2);

                likeIng2.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng2);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            (uri2)));
                }
                break;
            case "3":
                //facebook
                Uri uri = Uri.parse(Constants.FACEBOOK_LINK);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.facebook.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            (uri)));
                }
                break;
            case "4":
                //twitter
                Uri uri4 = Uri.parse(Constants.TWITTER_LINK);
                Intent likeIng4 = new Intent(Intent.ACTION_VIEW, uri4);

                likeIng4.setPackage("com.twitter.android");

                try {
                    startActivity(likeIng4);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            (uri4)));
                }
                break;

        }
    }
}