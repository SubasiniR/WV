package com.suba.weathervogue;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String VOGUE = "VOGUE";

    private String mJsonData;
    private Forecast mForecast;

    private TextView mLocationTextView;
    private Drawable mIcon;
    private TextView mCurrentTemperature;
    private TextView mFeelsLikeValue;
    private TextView mMaxMinTemperatureValue;
    private TextView mSummaryLabel;
    private TextView mHumidityValue;
    private TextView mUVIndexValue;
    private TextView mWindValue;
    private TextView mPrecipValue;
    private TextView mLastUpdatedTextView;
    private ImageView mIconImageView;
    private Button mVogueButton;

    private String mWeatherUrl;

    private String mLocation ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        makeActionOverflowMenuShown();

        mLocationTextView = (TextView) findViewById(R.id.locationTextView);
        mCurrentTemperature = (TextView) findViewById(R.id.currentTemperature);
        mFeelsLikeValue = (TextView) findViewById(R.id.feelsLikeValue);
        mMaxMinTemperatureValue = (TextView) findViewById(R.id.maxMinTemperatureValue);
        mSummaryLabel = (TextView) findViewById(R.id.summaryLabel);
        mHumidityValue = (TextView) findViewById(R.id.humidityValue);
        mUVIndexValue = (TextView) findViewById(R.id.uVIndexValue);
        mWindValue = (TextView) findViewById(R.id.windValue);
        mPrecipValue = (TextView) findViewById(R.id.precipValue);
        mLastUpdatedTextView = (TextView) findViewById(R.id.lastUpdatedTextView);
        mIconImageView = (ImageView) findViewById(R.id.iconImageView);
        mVogueButton = (Button) findViewById(R.id.vogueButton);


        mLocation = getLocation();
        getForecast();


        mVogueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,VogueActivity.class);
                intent.putExtra(VOGUE, mForecast.getVogue());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,LocationActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void getForecast() {

        String apiKey = "22180c7d3caa6464";

        mWeatherUrl = "http://api.wunderground.com/api/" + apiKey +
                "/geolocation/conditions/hourly/forecast10day/q/" + mLocation + ".json";

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(mWeatherUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertUserAboutError();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        mJsonData = response.body().string();
                        if (response.isSuccessful()) {
//                            Log.i(TAG, jsonData);
                            mForecast = getForecastDetails(mJsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException | JSONException e) {
                        Log.e(TAG, "Exception caught. ", e);
                    }
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.error_title))
                    .setMessage(getString(R.string.network_error_message))
                    .setPositiveButton(getString(R.string.error_ok_button), null);
            builder.create().show();
        }
    }

    private Forecast getForecastDetails(String jsonData) throws JSONException{
        Forecast forecast = new Forecast();

        forecast.setToday(getTodayDetails(jsonData));
        forecast.setVogue(getVogueDetails(jsonData));

        return forecast;
    }

    private Vogue[] getVogueDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject dailyForecast = forecast.getJSONObject("forecast");
        JSONObject simpleForecast = dailyForecast.getJSONObject("simpleforecast");
        JSONArray forecastDayArray = simpleForecast.getJSONArray("forecastday");

        Vogue[] vogues = new Vogue[forecastDayArray.length()];

        for(int i = 0 ; i<forecastDayArray.length() ; i++){
            JSONObject forecastDay = forecastDayArray.getJSONObject(i);
            JSONObject date = forecastDay.getJSONObject("date");
            JSONObject maxTemperature = forecastDay.getJSONObject("high");
            JSONObject minTemperature = forecastDay.getJSONObject("low");

            Vogue vogue = new Vogue();

            vogue.setIconStr(forecastDay.getString("icon"));
            vogue.setMonth(date.getInt("month"));
            vogue.setDayNum(date.getInt("day"));
            vogue.setMaxHumidity(forecastDay.getInt("maxhumidity"));
            vogue.setMaxTemperature(maxTemperature.getInt("fahrenheit"));
            vogue.setMinTemperature(minTemperature.getInt("fahrenheit"));

            vogues[i] = vogue;
        }
        return vogues;
    }

    private Today getTodayDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject currently = forecast.getJSONObject("current_observation");
        JSONObject location = currently.getJSONObject("display_location");
        JSONObject daily = forecast.getJSONObject("forecast");
        JSONObject dailyForecast = daily.getJSONObject("simpleforecast");
        JSONArray dailyArray = dailyForecast.getJSONArray("forecastday");

        final Today today = new Today();

        for (int i = 0; i < 1;i++) {
            JSONObject todayObject = dailyArray.getJSONObject(i);
            JSONObject maxTemperature = todayObject.getJSONObject("high");
            JSONObject minTemperature = todayObject.getJSONObject("low");

            today.setMaxTemperature(maxTemperature.getDouble("fahrenheit"));
            today.setMinTemperature(minTemperature.getDouble("fahrenheit"));
        }

        today.setLocation(location.getString("full"));
        today.setTemperature(currently.getDouble("temp_f"));
        today.setFeelsLike(currently.getDouble("feelslike_f"));
        today.setSummary(currently.getString("weather"));
        today.setHumidity(currently.getString("relative_humidity"));
        today.setUVIndex(currently.getString("UV"));
        today.setWindDirection(currently.getString("wind_dir"));
        today.setWindSpeed(currently.getDouble("wind_mph"));
        today.setPrecipitation(currently.getString("precip_today_in"));
        today.setLastUpdated(currently.getString("observation_time"));

        today.setIconUrl(currently.getString("icon_url"));
        String iconUrl = today.getIconUrl();
        mIcon = Forecast.getIconImageView(iconUrl);

        return today;
    }


    private void updateDisplay() {
        Today today = mForecast.getToday();

        mLocationTextView.setText(today.getLocation());
        mIconImageView.setImageDrawable(mIcon);
        mCurrentTemperature.setText(today.getTemperature()+"°F");
        mFeelsLikeValue.setText(today.getFeelsLike()+"°F");
        mMaxMinTemperatureValue.setText(today.getMaxTemperature() + "/" + today.getMinTemperature()+"°F");
        mSummaryLabel.setText(today.getSummary());
        mHumidityValue.setText(today.getHumidity());
        mUVIndexValue.setText(today.getUVIndex()+"");
        mWindValue.setText(today.getWindSpeed()+"mph "+today.getWindDirection());
        mPrecipValue.setText(today.getPrecipitation()+" in");
        mLastUpdatedTextView.setText(today.getLastUpdated());

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error_title))
                .setMessage(getString(R.string.error_message))
                .setPositiveButton(getString(R.string.error_ok_button), null);
        builder.create().show();
    }

    public String getLocation() {

        Intent intent = getIntent();
        String loc = intent.getStringExtra(LocationActivity.LOCATION);
        if(loc == null){
            mLocation = "autoip";
        } else {
            mLocation = loc;
            Log.i(TAG,mLocation);
        }
        return mLocation;
    }
    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }
}
