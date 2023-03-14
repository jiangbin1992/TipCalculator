package com.codecanyon.percentage;

import com.codecanyon.percentage.Backend.CgpaSaved;
import com.codecanyon.percentage.Backend.DiscountSaved;
import com.codecanyon.percentage.Backend.MarkSaved;
import com.codecanyon.percentage.Backend.QudraticClass;
import com.codecanyon.percentage.Backend.SgpaSaved;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    ///Editing///
    public static String PACKAGE_NAME="com.calculator.percentage";

    public static String FAV_TEXT = "FavText";

    public static String PERCENT_TAG = "PercentTag";
    public static String FAV_IMAGE="FavImage";

    public static String APP_UNIQUE_ID_STRING="AppUniqueId";
    public static String APP_UNIQUE_ID_VALUE="";
    public static String CURRENCY="currency";
    public static String THEME="Theme";
    public static Boolean LIGHT_THEME=true;
    public static String CURRENCY_VALUE="$";

    public static String [] titlesPer={"Marks %", "General %","Change in %","Discount %"};
    public static int [] imagesPer={R.drawable.ic_percent_icon,R.drawable.ic_percent_icon,R.drawable.ic_percent_icon,R.drawable.ic_percent_icon};

    public static String [] titlesGpa={"CGPA", "SGPA","GPA to %"};
    public static int [] imagesGpa={R.drawable.ic_cgpa,R.drawable.ic_sgpa,R.drawable.ic_gpa};

    public static String [] titlesGen={"Tax", "Tip","MarkUp","Margin","Doubling Time","Inflation"};
    public static int [] imagesGen={R.drawable.ic_tax,R.drawable.ic_tip,R.drawable.ic_percent_icon,R.drawable.ic_percent_icon,R.drawable.ic_percent_icon,R.drawable.ic_percent_icon};


    public static String [] titleMaths={"Scientific Calculator", "Ratio","Root","Quadratic"};
    public static int [] imagesMath={R.drawable.ic_calculator,R.drawable.ic_calculator,R.drawable.ic_calculator,
            R.drawable.ic_calculator,R.drawable.ic_calculator};



    public static String EMAIL_ADDRESS="feedback.arpa@gmail.com";
    public static String YOUTUBE_LINK="https://www.youtube.com/channel/UCsejxapLU_Ia1NFpwNXto1Q";
    public static String INSTAGRAM_LINK="https://www.instagram.com/_arpa_studio/";
    public static String LINKDIN_LINK="https://www.linkedin.com/in/aneesh-anchan/";
    public static String FACEBOOK_LINK="https://m.facebook.com/arpa1212/";
    public static String TWITTER_LINK="https://twitter.com/ArpaLtd/";

    public static String FIREBASE_LINK="https://percentagecalculator-3b556-default-rtdb.firebaseio.com";

    public static List<MarkSaved> MARKS_SAVED_LIST=new ArrayList<MarkSaved>();
    public static List<DiscountSaved> DISCOUNT_SAVED_LIST=new ArrayList<DiscountSaved>();
    public static List<SgpaSaved> SGPA_SAVED_LIST=new ArrayList<SgpaSaved>();
    public static List<CgpaSaved> CGPA_SAVED_LIST=new ArrayList<CgpaSaved>();
    public static List<QudraticClass> QUADRATIC_SAVED_LIST=new ArrayList<QudraticClass>();


    ///Save////
    //Marks//
    public static String SAVE="save";
    public static String DIVIDER="₹₹";
    public static String MARKS_NAME="Marks";
    public static String SUBJECT_ARRAY="subjectArray";
    public static String MARKS_OBTAINED_ARRAY="marksObtained";
    public static String TOTAL_MARKS_ARRAY="";

    public static String TOPIC="topic";

    //PURCHASE//
    public static String PRODUCT_ID="no_ads";
    public static String GOOGLE_PLAY_LICENSE="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk3eBsPyYMSNwzaE0jWATqRlmYjc3vPbkE1XrWDxEukstAyikBtSIOPLchdowzLiKyXtTWVP+0aUWIlIoF10dGaAb14Lc7osVN7OBCaePLkDhueYC+lGLAIqEEspUnlBKpqd6KEDjUGwktFYV+qnTlUKSvtxpFZfXRKS+Pghm5hXxUX2XMXhs3NEqo23275mXQLO2xGFdZYcuFliTN1TMWqg37ExY9GlRilLaJ2pXn0oPMZ4tAMOxOYDu37N2C/Z8KG0bi3ZN0KNoG0rp+Wgu3BIXD5k02pp/VWO2mmAlth/e8RVK6Hp4EaM3NTMUvF+np4KVsz0kp1dWLuPpBeK0XwIDAQAB";
    public static boolean PURCHASE_STATUS=false;

}
