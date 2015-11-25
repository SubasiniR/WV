package com.suba.weathervogue;

/**
 * Created by Sakthivale on 10/28/2015.
 */
public class Today {

    private double mTemperature;
    private double mFeelsLike;
    private double mMaxTemperature;
    private double mMinTemperature;
    private String mSummary;
    private String mHumidity;
    private String mUVIndex;
    private String mWindDirection;
    private double mWindSpeed;
    private String mPrecipitation;
    private String mLastUpdated;
    private String mIconUrl;
    private String mLocation;


    private String mErrorType;
    private String mErrorDescription;

    public Today() {
    }


    public String getErrorType() {
        return mErrorType;
    }

    public void setErrorType(String errorType) {
        mErrorType = errorType;
    }

    public String getErrorDescription() {
        return mErrorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        mErrorDescription = errorDescription;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        mLastUpdated = lastUpdated;
    }

    public String getPrecipitation() {
        return mPrecipitation;
    }

    public void setPrecipitation(String precipitation) {
        mPrecipitation = precipitation;
    }

    public int getTemperature() {
        return (int)Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public int getFeelsLike() {
        return (int)Math.round(mFeelsLike);
    }

    public void setFeelsLike(double feelsLike) {
        mFeelsLike = feelsLike;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity;
    }

    public String getUVIndex() {
        return mUVIndex;
    }

    public void setUVIndex(String UVIndex) {
        mUVIndex = UVIndex;
    }

    public String getWindDirection() {
        return mWindDirection;
    }

    public void setWindDirection(String windDirection) {
        mWindDirection = windDirection;
    }

    public int getWindSpeed() {
        return (int)Math.round(mWindSpeed);
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }


    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }

    public int getMaxTemperature() {
        return (int)Math.round(mMaxTemperature);
    }

    public void setMaxTemperature(double maxTemperature) {
        mMaxTemperature = maxTemperature;
    }

    public int getMinTemperature() {
        return (int)Math.round(mMinTemperature);
    }

    public void setMinTemperature(double minTemperature) {
        mMinTemperature = minTemperature;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }
}
