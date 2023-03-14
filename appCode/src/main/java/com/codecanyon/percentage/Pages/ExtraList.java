package com.codecanyon.percentage.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codecanyon.percentage.Backend.App;
import com.codecanyon.percentage.Backend.CgpaSaved;
import com.codecanyon.percentage.Backend.DiscountSaved;
import com.codecanyon.percentage.Backend.MarkSaved;
import com.codecanyon.percentage.Backend.QudraticClass;
import com.codecanyon.percentage.Backend.SavedData;
import com.codecanyon.percentage.Backend.SgpaSaved;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.FavAdapter;
import com.codecanyon.percentage.Supporting.MoreAppAdapeter;
import com.codecanyon.percentage.Supporting.SavedAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExtraList extends AppCompatActivity implements SavedAdapter.AdapterCallback {
    RecyclerView recyclerView;
    TextView title;
    ImageView titleIcon;
    FirebaseFirestore moreAppDatabase = FirebaseFirestore.getInstance();
    FavAdapter favAdapter;
    MoreAppAdapeter moreAppAdapter;
    private ReviewManager reviewManager;
    SharedPreferences openedCalculation;
    int openedInInt=0;
    SavedAdapter savedAdapter;
    InterstitialAd mIntertialAds;

    String NUMBER_OF_TIME_OPENED="NUMBER OF TIME";
    List<SavedData> savedList=new ArrayList<SavedData>();

    public void showRateApp() {
        com.google.android.play.core.tasks.Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                // Getting the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                com.google.android.play.core.tasks.Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown.
                });
            }else{
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (openedInInt%2==0) {
            showRateApp();
        }else{
            if (mIntertialAds != null) {
                mIntertialAds.show(ExtraList.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }
        openedInInt++;
        openedCalculation.edit().putInt(NUMBER_OF_TIME_OPENED,openedInInt).apply();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_list);

        reviewManager = ReviewManagerFactory.create(this);
        openedCalculation=getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        openedInInt = openedCalculation.getInt(NUMBER_OF_TIME_OPENED,0);


        title=findViewById(R.id.titleText);
        titleIcon=findViewById(R.id.titleIcon);
        recyclerView=findViewById(R.id.list);
        if(!Constants.LIGHT_THEME) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                if(!Constants.PURCHASE_STATUS){
                    loadInterAds();
                }
            }
        });

        if(! Constants.PURCHASE_STATUS) {
            AdView madview=findViewById(R.id.adView);
            AdRequest adRequest=new AdRequest.Builder().build();
            madview.loadAd(adRequest);
        }

        Intent intent=getIntent();
        String function=intent.getStringExtra("function");

        if(function.equals(getString(R.string.Favourites))){
            favClicked();
        }else if(function.equals(getString(R.string.More_Apps))){
            moreAppClicked();
        }else if(function.equals(getString(R.string.Saved))){
            savedClicked();
        }

    }

    private void loadInterAds() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.inter_ads), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mIntertialAds = interstitialAd;
                        mIntertialAds.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mIntertialAds = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                        //onLoaded
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                       mIntertialAds = null;
                    }
                });
    }

    private void savedClicked() {
        titleIcon.setBackgroundResource(R.drawable.ic_download);
        title.setText(getString(R.string.Saved));
        showTheRecyclerView();
    }

    private void moreAppClicked() {
        titleIcon.setBackgroundResource(R.drawable.ic_outline_add_box_24);
        title.setText(getString(R.string.More_Apps));

        List<App> appList=new ArrayList<App>();

        moreAppDatabase.collection(getString(R.string.More_Apps))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                App newApp=new App(documentSnapshot.getId().toString(),documentSnapshot.get("playstoreLink").toString(),documentSnapshot.get("icon").toString(),documentSnapshot.get("shortDiscription").toString());
                                appList.add(newApp);
                            }
                            moreAppAdapter=new MoreAppAdapeter(ExtraList.this,appList);
                            GridLayoutManager gridLayoutManager2=new GridLayoutManager(ExtraList.this,1,GridLayoutManager.VERTICAL,false);

                            recyclerView.setLayoutManager(gridLayoutManager2);
                            recyclerView.setAdapter(moreAppAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else{
                            Log.e("TAG", "Error getting documents.", task.getException());

                            Toast.makeText(ExtraList.this,"Something went wrong !!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    private void favClicked() {
        titleIcon.setBackgroundResource(R.drawable.ic_outline_favorite_border_24);
        title.setText(getString(R.string.Favourites));
        String[] titlePer= Constants.titlesPer;
        String[] titleGpa= Constants.titlesGpa;
        String[] titleGen= Constants.titlesGen;
        String[] titleMath=Constants.titleMaths;

        int[] imagePer=Constants.imagesPer;
        int[] imageGpa=Constants.imagesGpa;
        int[] imageGen=Constants.imagesGen;
        int[] imageMath=Constants.imagesMath;

        String[] allTitles=new String[titleGen.length+titleGpa.length+titlePer.length+titleMath.length];
        int[] allImages=new int[titleGen.length+titleGpa.length+titlePer.length+titleMath.length];


        for(int i=0;i<titlePer.length;i++){
            allTitles[i]=titlePer[i];
            allImages[i]=imagePer[i];
        }
        int size=titlePer.length;
        for(int i=0;i<titleGpa.length;i++){
            allTitles[size+i]=titleGpa[i];
            allImages[size+i]=imageGpa[i];
        }
        size=titlePer.length+titleGpa.length ;
        for(int i=0;i<titleGen.length;i++){
            allTitles[size+i]=titleGen[i];
            allImages[size+i]=imageGen[i];
        }

        size+=titleGen.length ;
        for(int i=0;i<titleMath.length;i++){
            allTitles[size+i]=titleMath[i];
            allImages[size+i]=imageMath[i];
        }

        favAdapter=new FavAdapter(this,allTitles,allImages);
        GridLayoutManager gridLayoutManager2=new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager2);
        recyclerView.setAdapter(favAdapter);
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onMethodCallback() {
        showTheRecyclerView();
    }

    private void showTheRecyclerView() {
        savedList.clear();
        for (CgpaSaved cgpaSaved : Constants.CGPA_SAVED_LIST){
            SavedData savedData=new SavedData(cgpaSaved.getFileName(),"CGPA",cgpaSaved.getId());
            savedList.add(savedData);
        }
        for (MarkSaved markSaved : Constants.MARKS_SAVED_LIST){
            SavedData savedData=new SavedData(markSaved.getFileName(),"Marks",markSaved.getId());
            savedList.add(savedData);
        }
        for (SgpaSaved sgpaSaved : Constants.SGPA_SAVED_LIST){
            SavedData savedData=new SavedData(sgpaSaved.getFileName(),"SGPA",sgpaSaved.getId());
            savedList.add(savedData);
        }
        for (QudraticClass cgpaSaved : Constants.QUADRATIC_SAVED_LIST){
            SavedData savedData=new SavedData(cgpaSaved.getFileName(),"Quadratic",909);
            savedList.add(savedData);
        }
        for (DiscountSaved discountSaved : Constants.DISCOUNT_SAVED_LIST){
            SavedData savedData=new SavedData(discountSaved.getFileName(),"Discount",discountSaved.getId());
            savedList.add(savedData);
        }
        savedAdapter=new SavedAdapter(this,savedList,this);
        GridLayoutManager gridLayoutManager3=new GridLayoutManager(ExtraList.this,1,GridLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager3);
        recyclerView.setAdapter(savedAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

}