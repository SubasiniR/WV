package com.suba.weathervogue;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Sakthivale on 10/30/2015.
 */
public class Forecast {

    private static final String TAG = Forecast.class.getSimpleName();

    private Today mToday;

    private Vogue[] mVogue;

    public Today getToday() {
        return mToday;
    }

    public void setToday(Today today) {
        mToday = today;
    }


    public Vogue[] getVogue() {
        return mVogue;
    }

    public void setVogue(Vogue[] vogue) {
        mVogue = vogue;
    }


    public static Drawable getIconImageView(String url) {

        InputStream is = null;
        try {
            is = (InputStream) new URL(url).getContent();
        } catch (IOException e) {
            Log.e(TAG, "Exception: ", e);
        }
        return  Drawable.createFromStream(is, "src name");
    }

    public static String getVogueStr(String iconStr, int maxHumidity, int maxTemperature, int minTemperature){

        String vogueStr = "";

        if(iconStr.equals("chancerain")| iconStr.equals("chancetstorms")
                | iconStr.equals("rain") | iconStr.equals("tstorms")){

            vogueStr = "Don't forget to take your hoodie/coat and umbrella";

        } else if(iconStr.equals("snow") | iconStr.equals("sleet")
                | iconStr.equals("flurries") | iconStr.equals("chancesleet")
                | iconStr.equals("chanceflurries") | iconStr.equals("chancesnow")
                | maxTemperature <= 30){

            vogueStr = "Dress up with many layers of clothes, gloves, ear muffs and boots.";

        } else  if(maxTemperature >= 80 & maxHumidity >= 60){

            vogueStr = "Wear loose/cotton, white clothes. Don't forget your sunglasses, hat and sunscreen lotion.";

        } else if(maxTemperature < 80 & minTemperature >= 60  & maxHumidity < 60 & maxTemperature > 60){

            vogueStr = "Awesome weather! Enjoy your day with your favorite shorts and tanktops/t-shirts.";

        } else if((maxTemperature <= 60 & maxTemperature > 40)){

            vogueStr = "Chill! You'll need a sweatshirt.";

        } else if(maxTemperature <= 40){

            vogueStr = "Icy cold! Stay warm with 2 layers of jacket/sweatshirt.";

        } else {
            vogueStr = "Normal! Any casuals. ";
        }

        return vogueStr;
    }

}
