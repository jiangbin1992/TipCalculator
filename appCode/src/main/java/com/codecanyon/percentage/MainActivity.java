package com.codecanyon.percentage;

import static com.best.now.myad.utils.Constant.AD_INTERSTITIAL_ID;
import static com.best.now.myad.utils.Constant.URL_PRIVACY_POLICY;
import static com.best.now.myad.utils.PublicHelperKt.loadAd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.best.now.myad.WebActivity;
import com.best.now.myad.utils.PublicHelperKt;
import com.codecanyon.percentage.Backend.CgpaSaved;
import com.codecanyon.percentage.Backend.DiscountSaved;
import com.codecanyon.percentage.Backend.Fav;
import com.codecanyon.percentage.Backend.MarkSaved;
import com.codecanyon.percentage.Backend.QudraticClass;
import com.codecanyon.percentage.Backend.SgpaSaved;
import com.codecanyon.percentage.Pages.CalculationActivity;
import com.codecanyon.percentage.Pages.Contact;
import com.codecanyon.percentage.Pages.ExtraList;
import com.codecanyon.percentage.Pages.InApp;
import com.codecanyon.percentage.Pages.Privacy;
import com.codecanyon.percentage.Supporting.CalculationAdapter;
import com.codecanyon.percentage.Supporting.RecyclerItemClickListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.CountryCurrencyPicker;
import com.scrounger.countrycurrencypicker.library.Currency;
import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;
import com.scrounger.countrycurrencypicker.library.PickerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    RelativeLayout menu, cart, fav, download;
    TextView favText;
    List<String> titlesFav;
    RelativeLayout updateLayout;
    TextView updateButton;
    List<Integer> imagesFav;
    RecyclerView favouriteRecycle, percentageRecycle, gpaRecycle, generalRecycle, mathRecycler;
    CalculationAdapter percentageAdapter, gpaAdapter, generalAdapter, favouriteAdapter, mathAdapter;
    GridLayoutManager gridLayoutManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    boolean backPressedTime = false;
    SharedPreferences sharedPreferences, sharedPreferencesImage, currencyPreference, themePreference;
    String versionString = "", usingAppVersion;
    private InterstitialAd mInterstitialAd;

    BillingProcessor bp;
    TransactionDetails subscriptionTransactionDetails;
    SharedPreferences purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu);
        cart = findViewById(R.id.chart);
        fav = findViewById(R.id.fav);
        download = findViewById(R.id.download);
        favText = findViewById(R.id.favourite);
        mathRecycler = findViewById(R.id.mathRecycle);
        favouriteRecycle = findViewById(R.id.favouriteRecycle);
        percentageRecycle = findViewById(R.id.percentageRecycle);
        gpaRecycle = findViewById(R.id.gpaRecycle);
        generalRecycle = findViewById(R.id.generalRecycle);
        updateLayout = findViewById(R.id.update);
        updateButton = findViewById(R.id.updateButton);

        bp = new BillingProcessor(this, Constants.GOOGLE_PLAY_LICENSE, this);
        bp.initialize();
        purchase = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);

        LinearLayout advBanner = findViewById(R.id.advBanner);
        loadAd(advBanner);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

//        if (!Constants.PURCHASE_STATUS) {
//            loadInterAds();
//            AdLoader adLoader = new AdLoader.Builder(this, AD_INTERSTITIAL_ID)
//                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
//                        @Override
//                        public void onNativeAdLoaded(NativeAd nativeAd) {
//                            //Show the ad.
//                            FrameLayout frameLayout =
//                                    findViewById(R.id.nativeAds);
//                            // Assumes that your ad layout is in a file call native_ad_layout.xml
//                            // in the res/layout folder
//                            NativeAdView adView = (NativeAdView) getLayoutInflater()
//                                    .inflate(R.layout.native_ads, null);
//                            // This method sets the text, images and the native ad, etc into the ad
//                            // view.
//                           // populateNativeAdView(nativeAd, adView);
//                        }
//                    })
//                    .withAdListener(new AdListener() {
//                        @Override
//                        public void onAdFailedToLoad(LoadAdError adError) {
//                            // Handle the failure by logging, altering the UI, and so on.
//                        }
//                    })
//                    .withNativeAdOptions(new NativeAdOptions.Builder()
//                            // Methods in the NativeAdOptions.Builder class can be
//                            // used here to specify individual options settings.
//                            .build())
//                    .build();
//            adLoader.loadAds(new AdRequest.Builder().build(), 3);
//        } else {
//            cart.setVisibility(View.GONE);
//        }
        usingAppVersion = getString(R.string.version);
        firebaseDatabase = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        mDatabase = firebaseDatabase.getReference("version");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                versionString = dataSnapshot.getValue().toString();
                if (versionString.equals(usingAppVersion)) {
                    updateLayout.setVisibility(View.GONE);
                } else {
                    updateLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(getString(R.string.appLink)));
                            startActivity(intent);
                            return null;
                        }
                    });
                }

            }
        });
        retrevingData();

        addToRecylcerView();

        favText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            favClicked();
                            return null;
                        }
                    });
                }
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
//                            meenuNav();
                            WebActivity.Companion.startActivity(MainActivity.this, "Privacy Policy", URL_PRIVACY_POLICY);
                            return null;
                        }
                    });

                }

            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent intent = new Intent(MainActivity.this, ExtraList.class);
                            intent.putExtra("function", getString(R.string.Saved));
                            startActivity(intent);
                            return null;
                        }
                    });

                }

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            buyPremium();
                            return null;
                        }
                    });

                }

            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            favClicked();
                            return null;
                        }
                    });

                }

            }
        });


    }

    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        try {
            // Locate the view that will hold the headline, set its text, and call the
            // NativeAdView's setHeadlineView method to register it.
            FrameLayout container = findViewById(R.id.nativeAds);
            container.setVisibility(View.VISIBLE);
            TextView headlineView = adView.findViewById(R.id.heading);
            headlineView.setText(nativeAd.getHeadline());
            adView.setHeadlineView(headlineView);


            TextView body = adView.findViewById(R.id.body);
            if (!nativeAd.getBody().equals(null)) {
                body.setText(nativeAd.getBody());
                adView.setBodyView(body);
            }

            RatingBar starRating = adView.findViewById(R.id.start_rating);
            if (nativeAd.getStarRating() != null) {
                starRating.setRating(nativeAd.getStarRating().floatValue());
                adView.setStarRatingView(starRating);
            }

            TextView advitisor = adView.findViewById(R.id.advertisername);
            if (nativeAd.getAdvertiser() != null) {
                advitisor.setText(nativeAd.getAdvertiser());
                adView.setAdvertiserView(advitisor);
            }

            ImageView icon = adView.findViewById(R.id.adicon);
            if (nativeAd.getIcon() != null) {
                icon.setImageDrawable(nativeAd.getIcon().getDrawable());
                adView.setIconView(icon);
            }

            Button button = adView.findViewById(R.id.calltoaction);
            if (nativeAd.getCallToAction() != null) {
                button.setText(nativeAd.getCallToAction());
                adView.setCallToActionView(button);
            }
            // Repeat the above process for the other assets in the NativeAd
            // using additional view objects (Buttons, ImageViews, etc).


            // If the app is using a MediaView, it should be
            // instantiated and passed to setMediaView. This view is a little different
            // in that the asset is populated automatically, so there's one less step.
            MediaView mediaView = (MediaView) adView.findViewById(R.id.media_view);
            adView.setMediaView(mediaView);

            // Call the NativeAdView's setNativeAd method to register the
            // NativeAdObject.
            adView.setNativeAd(nativeAd);

            // Ensure that the parent view doesn't already contain an ad view.
            container.removeAllViews();

            // Place the AdView into the parent.
            container.addView(adView);
        } catch (Exception e) {

        }
    }

    private void retrevingData() {
        sharedPreferencesImage = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if (!sharedPreferences.getString(Constants.FAV_TEXT, "null").equals("null")) {
            String jsonText = sharedPreferences.getString(Constants.FAV_TEXT, null);
            String[] text = new String[6];
            int[] image = new int[6];
            text = gson.fromJson(jsonText, String[].class);
            Gson gson1 = new Gson();
            String jsonText1 = sharedPreferencesImage.getString(Constants.FAV_IMAGE, null);
            image = gson1.fromJson(jsonText1, int[].class);
            Fav fav = new Fav(this);
            List<String> listText = Arrays.asList(text);

            for (int i = 0; i < listText.size(); i++) {
                fav.addToFavList(text[i], image[i]);
            }
        }

        retrivingMarks();
        retrevingCgpa();
        retrevingSgpa();
        retrivingDiscount();
        retrevingQuadratic();
    }

    private void retrevingQuadratic() {

        Constants.QUADRATIC_SAVED_LIST.clear();
        SQLiteDatabase myDatabaseForQuadratic = this.openOrCreateDatabase("Quadratic", MODE_PRIVATE, null);
        myDatabaseForQuadratic.execSQL("CREATE TABLE IF NOT EXISTS quadraticTable(a TEXT,b TEXT,c TEXT,fileName TEXT,id INTEGER PRIMARY KEY)");
        Cursor cursor = myDatabaseForQuadratic.rawQuery("SELECT * FROM quadraticTable", null);
        int aIndex = cursor.getColumnIndex("a");
        int bIndex = cursor.getColumnIndex("b");
        int cIndex = cursor.getColumnIndex("c");
        int fileNameIndex = cursor.getColumnIndex("fileName");
        int idIndex = cursor.getColumnIndex("id");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            QudraticClass saved = new QudraticClass(cursor.getString(fileNameIndex), cursor.getString(aIndex), cursor.getString(bIndex), cursor.getString(cIndex));
            Constants.QUADRATIC_SAVED_LIST.add(saved);
            cursor.moveToNext();
        }
    }

    private void retrivingMarks() {

        Constants.MARKS_SAVED_LIST.clear();
        SQLiteDatabase myDatabaseForMarks = this.openOrCreateDatabase("Marks", MODE_PRIVATE, null);
        myDatabaseForMarks.execSQL("CREATE TABLE IF NOT EXISTS marksTable(subjectArray TEXT,markObtainedArray TEXT,totalMarksArray TEXT,fileName TEXT,id INTEGER PRIMARY KEY)");
        Cursor cursor = myDatabaseForMarks.rawQuery("SELECT * FROM marksTable", null);
        int subjectIndex = cursor.getColumnIndex("subjectArray");
        int marksObtainedIndex = cursor.getColumnIndex("markObtainedArray");
        int totalMarksIndex = cursor.getColumnIndex("totalMarksArray");
        int fileIndex = cursor.getColumnIndex("fileName");
        int idIndex = cursor.getColumnIndex("id");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = Integer.parseInt(cursor.getString(idIndex));
            MarkSaved markSaved = new MarkSaved(cursor.getString(fileIndex), cursor.getString(marksObtainedIndex), cursor.getString(totalMarksIndex), cursor.getString(subjectIndex), id);
            Constants.MARKS_SAVED_LIST.add(markSaved);
            cursor.moveToNext();
        }
    }

    private void retrivingDiscount() {

        Constants.DISCOUNT_SAVED_LIST.clear();
        SQLiteDatabase myDatabaseForDiscount = this.openOrCreateDatabase("Discount", MODE_PRIVATE, null);
        myDatabaseForDiscount.execSQL("CREATE TABLE IF NOT EXISTS discountTable(itemNameArray TEXT,actualValueArray TEXT,discountValueArray TEXT,addOverAllDiscountBoolean INTEGER,overAllDiscount TEXT,percentageBoolean INTEGER,fileName TEXT,id INTEGER PRIMARY KEY)");
        Cursor cursor = myDatabaseForDiscount.rawQuery("SELECT * FROM discountTable", null);
        int itemIndex = cursor.getColumnIndex("itemNameArray");
        int actualIndex = cursor.getColumnIndex("actualValueArray");
        int discountIndex = cursor.getColumnIndex("discountValueArray");
        int addIndex = cursor.getColumnIndex("addOverAllDiscountBoolean");
        int overAllIndex = cursor.getColumnIndex("overAllDiscount");
        int percentIndex = cursor.getColumnIndex("percentageBoolean");
        int fileNameIndex = cursor.getColumnIndex("fileName");
        int idIndex = cursor.getColumnIndex("id");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Boolean percentage, overAll;
            if (Integer.parseInt(cursor.getString(percentIndex)) == 0) {
                percentage = false;
            } else {
                percentage = true;
            }
            if (Integer.parseInt(cursor.getString(addIndex)) == 0) {
                overAll = false;
            } else {
                overAll = true;
            }
            int id = Integer.parseInt(cursor.getString(idIndex));
            DiscountSaved saved = new DiscountSaved(cursor.getString(fileNameIndex), cursor.getString(actualIndex), cursor.getString(discountIndex), cursor.getString(itemIndex), cursor.getString(overAllIndex), percentage, overAll, id);
            Constants.DISCOUNT_SAVED_LIST.add(saved);
            cursor.moveToNext();
        }
    }

    private void retrevingSgpa() {
        Constants.SGPA_SAVED_LIST.clear();
        SQLiteDatabase myDatabaseForSgpa = this.openOrCreateDatabase("SGPA", MODE_PRIVATE, null);
        myDatabaseForSgpa.execSQL("CREATE TABLE IF NOT EXISTS sgpaTable(subjectArray TEXT,obtainedMarksArray TEXT,totalMarksArray TEXT,credit TEXT,calculationRule INTEGER,sgpaOutOf INTEGER,fileName TEXT,id INTEGER PRIMARY KEY)");
        Cursor cursor = myDatabaseForSgpa.rawQuery("SELECT * FROM sgpaTable", null);
        int subjectIndex = cursor.getColumnIndex("subjectArray");
        int obtainedIndex = cursor.getColumnIndex("obtainedMarksArray");
        int totalIndex = cursor.getColumnIndex("totalMarksArray");
        int creditIndex = cursor.getColumnIndex("credit");
        int calculationRuleIndex = cursor.getColumnIndex("calculationRule");
        int sgpaOutOfIndex = cursor.getColumnIndex("sgpaOutOf");
        int fileNameIndex = cursor.getColumnIndex("fileName");
        int idIndex = cursor.getColumnIndex("id");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Boolean sgpaToggle, standards;
            if (Integer.parseInt(cursor.getString(calculationRuleIndex)) == 0) {
                standards = false;
            } else {
                standards = true;
            }
            if (Integer.parseInt(cursor.getString(sgpaOutOfIndex)) == 0) {
                sgpaToggle = false;
            } else {
                sgpaToggle = true;
            }
            int id = Integer.parseInt(cursor.getString(idIndex));
            SgpaSaved saved = new SgpaSaved(cursor.getString(fileNameIndex), cursor.getString(obtainedIndex), cursor.getString(totalIndex), cursor.getString(creditIndex), cursor.getString(subjectIndex), standards, sgpaToggle, id);
            Constants.SGPA_SAVED_LIST.add(saved);
            cursor.moveToNext();
        }
    }

    private void retrevingCgpa() {
        Constants.CGPA_SAVED_LIST.clear();
        SQLiteDatabase myDatabaseForCgpa = this.openOrCreateDatabase("CGPA", MODE_PRIVATE, null);
        myDatabaseForCgpa.execSQL("CREATE TABLE IF NOT EXISTS cgpaTable(semesterNameArray TEXT,sgpa TEXT,fileName TEXT,cgpaOutOf INTEGER,id INTEGER PRIMARY KEY)");
        Cursor cursor = myDatabaseForCgpa.rawQuery("SELECT * FROM cgpaTable", null);
        int semesterIndex = cursor.getColumnIndex("semesterNameArray");
        int sgpaIndex = cursor.getColumnIndex("sgpa");
        int fileNameIndex = cursor.getColumnIndex("fileName");
        int cgpaOutOfIndex = cursor.getColumnIndex("cgpaOutOf");
        int idIndex = cursor.getColumnIndex("id");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Boolean cgpaToggle;

            if (Integer.parseInt(cursor.getString(cgpaOutOfIndex)) == 0) {
                cgpaToggle = false;
            } else {
                cgpaToggle = true;
            }
            int id = Integer.parseInt(cursor.getString(idIndex));
            CgpaSaved saved = new CgpaSaved(cursor.getString(fileNameIndex), cursor.getString(semesterIndex), cursor.getString(sgpaIndex), cgpaToggle, id);
            Constants.CGPA_SAVED_LIST.add(saved);
            cursor.moveToNext();
        }
    }

    private void addToRecylcerView() {

        //percentage
        percentageAdapter = new CalculationAdapter(this, Constants.titlesPer, Constants.imagesPer);
        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        percentageRecycle.setLayoutManager(gridLayoutManager);
        percentageRecycle.setAdapter(percentageAdapter);

        percentageRecycle.addOnItemTouchListener(new RecyclerItemClickListener(this, percentageRecycle, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // do whatever
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent intent = new Intent(MainActivity.this, CalculationActivity.class);
                            intent.putExtra("function", Constants.titlesPer[position]);
                            intent.putExtra("openingSaved", false);
                            startActivity(intent);
                            return null;
                        }
                    });

                }

            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));

        ///Maths Calculator


        mathAdapter = new CalculationAdapter(this, Constants.titleMaths, Constants.imagesMath);
        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mathRecycler.setLayoutManager(gridLayoutManager4);
        mathRecycler.setAdapter(mathAdapter);
        mathRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, mathRecycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // do whatever
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent intent = new Intent(MainActivity.this, CalculationActivity.class);
                            intent.putExtra("function", Constants.titleMaths[position]);
                            intent.putExtra("openingSaved", false);
                            startActivity(intent);
                            return null;
                        }
                    });

                }

            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));


        //GPA

        gpaAdapter = new CalculationAdapter(this, Constants.titlesGpa, Constants.imagesGpa);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        gpaRecycle.setLayoutManager(gridLayoutManager1);
        gpaRecycle.setAdapter(gpaAdapter);

        gpaRecycle.addOnItemTouchListener(new RecyclerItemClickListener(this, gpaRecycle, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // do whatever
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent intent = new Intent(MainActivity.this, CalculationActivity.class);
                            intent.putExtra("function", Constants.titlesGpa[position]);
                            intent.putExtra("openingSaved", false);
                            startActivity(intent);
                            return null;
                        }
                    });

                }

            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));

        //General


        generalAdapter = new CalculationAdapter(this, Constants.titlesGen, Constants.imagesGen);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        generalRecycle.setLayoutManager(gridLayoutManager2);
        generalRecycle.setAdapter(generalAdapter);
        generalRecycle.addOnItemTouchListener(new RecyclerItemClickListener(this, generalRecycle, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // do whatever
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent intent = new Intent(MainActivity.this, CalculationActivity.class);
                            intent.putExtra("function", Constants.titlesGen[position]);
                            intent.putExtra("openingSaved", false);
                            startActivity(intent);
                            return null;
                        }
                    });

                }

            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));

        // favourite
        titlesFav = new ArrayList<>();
        imagesFav = new ArrayList<>();

        Fav fav = new Fav(this);

        if (Fav.favList.isEmpty() || Fav.favList == null) {
            favText.setVisibility(View.GONE);
            favouriteRecycle.setVisibility(View.GONE);
        } else {
            favText.setVisibility(View.VISIBLE);
            favouriteRecycle.setVisibility(View.VISIBLE);
            favouriteAdapter = new CalculationAdapter(this, fav.inArray(), fav.inArrayImsge());
            GridLayoutManager gridLayoutManager3 = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

            favouriteRecycle.setLayoutManager(gridLayoutManager3);
            favouriteRecycle.setAdapter(favouriteAdapter);
        }
        favouriteRecycle.addOnItemTouchListener(new RecyclerItemClickListener(this, favouriteRecycle, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // do whatever
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent intent = new Intent(MainActivity.this, CalculationActivity.class);
                            intent.putExtra("function", fav.inArray()[position]);
                            startActivity(intent);
                            return null;
                        }
                    });

                }

            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));
    }

    private void meenuNav() {
        final Dialog menu = new Dialog(MainActivity.this);
        menu.setContentView(R.layout.menu);
        menu.setCancelable(true);
        menu.setCanceledOnTouchOutside(true);
        menu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        menu.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        menu.getWindow().setGravity(Gravity.BOTTOM);
        menu.show();
        //Decleration
        LinearLayout removeAds = menu.findViewById(R.id.removeads);
        LinearLayout currency = menu.findViewById(R.id.currency);
        LinearLayout rate = menu.findViewById(R.id.rateUs);
        LinearLayout favourite = menu.findViewById(R.id.favourite);
        LinearLayout saved = menu.findViewById(R.id.saved);
        LinearLayout privacy = menu.findViewById(R.id.privacy);
        LinearLayout contactUs = menu.findViewById(R.id.contactUs);
        LinearLayout theme = menu.findViewById(R.id.theme);
        LinearLayout moreApps = menu.findViewById(R.id.moreApps);
        LinearLayout share = menu.findViewById(R.id.share);
        TextView currencyIcon = menu.findViewById(R.id.iconOfCurrency);

        if (getString(R.string.version).equals("v.0.1")) {
            saved.setVisibility(View.GONE);
            removeAds.setVisibility(View.GONE);
        }
        if (Constants.PURCHASE_STATUS) {
            removeAds.setVisibility(View.GONE);
        }

        currencyIcon.setText(Constants.CURRENCY_VALUE);
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            menu.dismiss();
                            favClicked();
                            return null;
                        }
                    });

                }

            }
        });
        moreApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            menu.dismiss();
                            Intent intent = new Intent(MainActivity.this, ExtraList.class);
                            intent.putExtra("function", getString(R.string.More_Apps));
                            startActivity(intent);
                            return null;
                        }
                    });

                }

            }
        });
        removeAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            buyPremium();
                            menu.dismiss();
                            return null;
                        }
                    });

                }

            }
        });
        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            menu.dismiss();
                            changeCurrency();
                            return null;
                        }
                    });

                }

            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(getString(R.string.appLink)));
                            startActivity(intent);
                            menu.dismiss();
                            return null;
                        }
                    });

                }

            }
        });

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            Intent intent = new Intent(MainActivity.this, ExtraList.class);
                            intent.putExtra("function", getString(R.string.Saved));
                            startActivity(intent);
                            menu.dismiss();
                            return null;
                        }
                    });

                }

            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            menu.dismiss();
                            WebActivity.Companion.startActivity(MainActivity.this, "Privacy Policy", URL_PRIVACY_POLICY);
                            return null;
                        }
                    });

                }

            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            menu.dismiss();
                            startActivity(new Intent(MainActivity.this, Contact.class));
                            return null;
                        }
                    });

                }

            }
        });
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicHelperKt.isRewarded(MainActivity.this)) {
                    PublicHelperKt.showInterstitialAd(MainActivity.this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            menu.dismiss();
                            changeTheme();
                            return null;
                        }
                    });

                }

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.appLink));
                intent.putExtra(Intent.EXTRA_SUBJECT, "EMI A Financial Calculator");
                startActivity(Intent.createChooser(intent, "Share Using"));

                menu.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime) {
            super.onBackPressed();
        } else {
            backPressedTime = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeTheme() {
        themePreference = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);

        final Dialog theme = new Dialog(MainActivity.this);
        theme.setContentView(R.layout.theme_dialog);
        theme.setCancelable(true);
        theme.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        theme.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        theme.getWindow().setGravity(Gravity.CENTER);
        theme.show();

        SwitchCompat dark = theme.findViewById(R.id.dark);
        Constants.LIGHT_THEME = themePreference.getBoolean(Constants.THEME, true);
        if (!Constants.LIGHT_THEME) {
            dark.setChecked(true);
        }
        dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    themePreference.edit().putBoolean(Constants.THEME, false).apply();
                    Constants.LIGHT_THEME = false;
                    theme.dismiss();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    themePreference.edit().putBoolean(Constants.THEME, true).apply();
                    Constants.LIGHT_THEME = true;
                    theme.dismiss();
                }
            }
        });
    }

    private void loadInterAds() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, AD_INTERSTITIAL_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(MainActivity.this);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
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

    private void changeCurrency() {
        currencyPreference = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);

        CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(PickerType.COUNTRYandCURRENCY, new CountryCurrencyPickerListener() {

            @Override
            public void onSelectCountry(Country country) {
                currencyPreference.edit().putString(Constants.CURRENCY, country.getCurrency().getSymbol()).apply();
                Toast.makeText(MainActivity.this, country.getCurrency().getSymbol(), Toast.LENGTH_SHORT).show();
                Constants.CURRENCY_VALUE = country.getCurrency().getSymbol();
            }

            @Override
            public void onSelectCurrency(Currency currency) {
                currencyPreference.edit().putString(Constants.CURRENCY, currency.getSymbol()).apply();
                Toast.makeText(MainActivity.this, currency.getSymbol(), Toast.LENGTH_SHORT).show();
                Constants.CURRENCY_VALUE = currency.getSymbol();

            }
        });

        pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);


    }

    private void buyPremium() {
        startActivity(new Intent(MainActivity.this, InApp.class));
    }

    private void favClicked() {
        Intent intent = new Intent(MainActivity.this, ExtraList.class);
        intent.putExtra("function", getString(R.string.Favourites));
        startActivity(intent);
    }


    public boolean hassubsription() {
        if (subscriptionTransactionDetails != null) {
            return subscriptionTransactionDetails.purchaseInfo != null;
        } else {
            return false;
        }
    }

    @Override
    public void onBillingInitialized() {

        subscriptionTransactionDetails = bp.getPurchaseTransactionDetails(Constants.PRODUCT_ID);

        if (hassubsription()) {
            purchase.edit().putBoolean("PURCHASE", true).apply();
            Toast.makeText(MainActivity.this, "You have purchased, Just restart the app.", Toast.LENGTH_LONG).show();

            purchase.edit().putBoolean("PURCHASE", true).apply();
        }
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }
}