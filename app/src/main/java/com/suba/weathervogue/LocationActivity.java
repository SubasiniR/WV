package com.suba.weathervogue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LocationActivity extends AppCompatActivity implements TextWatcher {

    private static final String TAG = LocationActivity.class.getSimpleName();

    public static final String LOCATION = "LOCATION";

    private AutoCompleteTextView mLocationACTextView;
    private String mQuery = "";
    private String mJsonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mLocationACTextView = (AutoCompleteTextView) findViewById(R.id.locationACTextView);
        getLocation(mQuery);

        mLocationACTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Location location = (Location) adapterView.getItemAtPosition(position);
                String locationName = location.getLocationName();

                mLocationACTextView.setText(locationName);

                Intent intent = new Intent(LocationActivity.this,MainActivity.class);
                intent.putExtra(LOCATION, locationName);
                startActivity(intent);

            }
        });

    }

    private void getLocation(String query) {

        String locationUrl = "http://autocomplete.wunderground.com/aq?query=" + query;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(locationUrl).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LocationActivity.this, "Error in Calling API", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //get the suggestions and print
                if (response.isSuccessful()) {
                    mJsonData = response.body().string();
                    Log.i(TAG, mJsonData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setLocationAdapter();
                        }
                    });

                }
            }
        });

    }


    private Location[] getLocationDetails(String jsonData) throws JSONException{
        JSONObject autoCompleteLocation = new JSONObject(jsonData);
        JSONArray result = autoCompleteLocation.getJSONArray("RESULTS");

        Location[] locations = new Location[result.length()];

        for (int i = 0; i < result.length(); i++) {
            JSONObject jsonLocation = result.getJSONObject(i);
            Location location = new Location();

            location.setLocationName(jsonLocation.getString("name"));

            locations[i] = location;
        }
        return locations;
    }


    public void setLocationAdapter() {
        try {
            LocationAdapter adapter = new LocationAdapter(LocationActivity.this,
                    R.layout.activity_location, getLocationDetails(mJsonData));
            mLocationACTextView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.e(TAG, "Exception: ", e);
        }
        mLocationACTextView.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        mQuery = editable.toString();
        getLocation(mQuery);
    }
}
