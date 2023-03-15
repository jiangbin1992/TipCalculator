package com.codecanyon.percentage.Pages;

import static com.best.now.myad.utils.Constant.AD_INTERSTITIAL_ID;
import static com.best.now.myad.utils.PublicHelperKt.loadAd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codecanyon.percentage.Backend.CgpaSaved;
import com.codecanyon.percentage.Backend.DiscountSaved;
import com.codecanyon.percentage.Backend.MarkSaved;
import com.codecanyon.percentage.Backend.QudraticClass;
import com.codecanyon.percentage.Backend.SgpaSaved;
import com.codecanyon.percentage.Calculation.Cgpa;
import com.codecanyon.percentage.Calculation.CgpaToPercentage;
import com.codecanyon.percentage.Calculation.ChangeinPercent;
import com.codecanyon.percentage.Calculation.Discount;
import com.codecanyon.percentage.Calculation.DoublingTime;
import com.codecanyon.percentage.Calculation.General;
import com.codecanyon.percentage.Calculation.Inflation;
import com.codecanyon.percentage.Calculation.Margin;
import com.codecanyon.percentage.Calculation.MarkUp;
import com.codecanyon.percentage.Calculation.Marks;
import com.codecanyon.percentage.Calculation.Quadratic;
import com.codecanyon.percentage.Calculation.Ratio;
import com.codecanyon.percentage.Calculation.RootCalculator;
import com.codecanyon.percentage.Calculation.SalesTax;
import com.codecanyon.percentage.Calculation.ScientificCalculator;
import com.codecanyon.percentage.Calculation.Sgpa;
import com.codecanyon.percentage.Calculation.Tip;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculationActivity extends AppCompatActivity {
    TextView title;
    ImageView icon;
    FrameLayout frameLayout;
    private ReviewManager reviewManager;
    SharedPreferences openedCalculation;
    int openedInInt=0;
    InterstitialAd mInterstitialAd;
    String NUMBER_OF_TIME_OPENED="NUMBER OF TIME";

    public void showRateApp() {
        Task <ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                // Getting the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
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
        if (openedInInt%4==0) {
            showRateApp();
        }else{
            if (mInterstitialAd != null) {
                mInterstitialAd.show(this);
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
        openedCalculation=getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        openedInInt = openedCalculation.getInt(NUMBER_OF_TIME_OPENED,0);

        setContentView(R.layout.activity_calculation);
        title=findViewById(R.id.titleText);
        icon=findViewById(R.id.titleIcon);
        frameLayout=findViewById(R.id.frameLayout);
        reviewManager = ReviewManagerFactory.create(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                if(!Constants.PURCHASE_STATUS) {
                    loadInterAds();
                }
            }
        });

        if(! Constants.PURCHASE_STATUS) {
//            Lin madview=findViewById(R.id.adView);
//            AdRequest adRequest=new AdRequest.Builder().build();
//            madview.loadAd(adRequest);
            LinearLayout advBanner = findViewById(R.id.adView);
            loadAd(advBanner);

        }
        if(!Constants.LIGHT_THEME) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Intent intent=getIntent();
        String function=intent.getStringExtra("function");

        boolean openingSaved=intent.getBooleanExtra("openingSaved",false);
        int positionFile=0;
        if(openingSaved){
            positionFile=intent.getIntExtra("filePosition",0);
        }
        title.setText(function);
        if(function.equals(Constants.titlesPer[0])){
            icon.setBackgroundResource(Constants.imagesPer[0]);
            if(openingSaved) {
                MarkSaved markSaved= Constants.MARKS_SAVED_LIST.get(positionFile);
                List<String> obtainedList=stringToList(markSaved.getMarksObtained());
                List<String> totalList=stringToList(markSaved.getTotalMarks());
                List<String> subjectList=stringToList(markSaved.getSubjectName());
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Marks(openingSaved,obtainedList,totalList,subjectList)).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Marks(openingSaved,null,null,null)).commit();
            }
        }else if(function.equals(Constants.titlesPer[1])){
            icon.setBackgroundResource(Constants.imagesPer[1]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new General()).commit();
        }else if(function.equals(Constants.titlesPer[2])){
            icon.setBackgroundResource(Constants.imagesPer[2]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new ChangeinPercent()).commit();
        }else if(function.equals(Constants.titlesPer[3])){
            icon.setBackgroundResource(Constants.imagesPer[3]);
            if(openingSaved) {
                DiscountSaved discount=Constants.DISCOUNT_SAVED_LIST.get(positionFile);
                List<String> itemList=stringToList(discount.getItemName());
                List<String> actualList=stringToList(discount.getActualValue());
                List<String> discountList=stringToList(discount.getTotalValue());
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Discount(openingSaved,itemList,actualList,discountList,discount.getAddOverAllDiscount(),discount.getOverAllDiscount(),discount.getPercentageBoolean())).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Discount(openingSaved,null,null,null,false,null,false)).commit();
            }
        }else if(function.equals(Constants.titlesGpa[0])){
            icon.setBackgroundResource(Constants.imagesGpa[0]);
            if(openingSaved){
                CgpaSaved cgpaSaved=Constants.CGPA_SAVED_LIST.get(positionFile);
                List<String>semesterName=stringToList(cgpaSaved.getSemesterName());
                List<String>cgpaList=stringToList(cgpaSaved.getSgpa());

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Cgpa(openingSaved,semesterName,cgpaList,cgpaSaved.getCgpaOutOf())).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Cgpa(openingSaved,null,null,false)).commit();
            }
        }else if(function.equals(Constants.titlesGpa[1])){
            icon.setBackgroundResource(Constants.imagesGpa[1]);
            if(openingSaved){
                SgpaSaved sgpaSaved=Constants.SGPA_SAVED_LIST.get(positionFile);
                List<String> subjectList=stringToList(sgpaSaved.getSubjectName());
                List<String> creditList=stringToList(sgpaSaved.getCredit());
                List<String> obtainedList=stringToList(sgpaSaved.getMarksObtained());
                List<String> totalList=stringToList(sgpaSaved.getTotalMarks());
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Sgpa(openingSaved,subjectList,creditList,obtainedList,totalList,sgpaSaved.getCalculationRule(),sgpaSaved.getSgpaRule())).commit();
            }else{

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Sgpa(openingSaved,null,null,null,null,false,false)).commit();
            }
        }else if(function.equals(Constants.titlesGpa[2])){
            icon.setBackgroundResource(Constants.imagesGpa[2]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new CgpaToPercentage()).commit();
        }else if(function.equals(Constants.titlesGen[0])){
            icon.setBackgroundResource(Constants.imagesGen[0]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new SalesTax()).commit();
        }else if(function.equals(Constants.titlesGen[1])){
            icon.setBackgroundResource(Constants.imagesGen[1]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Tip()).commit();
        }else if(function.equals(Constants.titlesGen[2])){
            icon.setBackgroundResource(Constants.imagesGen[2]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new MarkUp()).commit();
        }else if(function.equals(Constants.titlesGen[3])){
            icon.setBackgroundResource(Constants.imagesGen[3]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Margin()).commit();
        }else if(function.equals(Constants.titlesGen[4])){
            icon.setBackgroundResource(Constants.imagesGen[4]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new DoublingTime()).commit();
        }else if(function.equals(Constants.titlesGen[5])){
            icon.setBackgroundResource(Constants.imagesGen[5]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Inflation()).commit();
        } else if (function.equals(Constants.titleMaths[0])) {
            icon.setBackgroundResource(Constants.imagesMath[0]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ScientificCalculator()).commit();
        } else if (function.equals(Constants.titleMaths[1])) {
            icon.setBackgroundResource(Constants.imagesMath[1]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Ratio()).commit();
        } else if (function.equals(Constants.titleMaths[2])) {
            icon.setBackgroundResource(Constants.imagesMath[2]);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new RootCalculator()).commit();
        } else if (function.equals(Constants.titleMaths[3])) {
            icon.setBackgroundResource(Constants.imagesMath[3]);
            if (openingSaved) {
                QudraticClass qudraticClass = Constants.QUADRATIC_SAVED_LIST.get(positionFile);
                String a = qudraticClass.getA();
                String b = qudraticClass.getB();
                String c = qudraticClass.getC();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Quadratic(a, b, c)).commit();
            } else
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Quadratic()).commit();
        }


    }

    private List<String> stringToList(String string) {
        List<String> myList = new ArrayList<String>(Arrays.asList(string.split(Constants.DIVIDER)));
        return myList;
    }


    private void loadInterAds() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,AD_INTERSTITIAL_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
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
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                        //onLoaded
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
    }

}