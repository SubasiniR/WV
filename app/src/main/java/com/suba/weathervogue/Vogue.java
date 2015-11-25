package com.suba.weathervogue;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sakthivale on 11/16/2015.
 */
public class Vogue implements Parcelable{

    private String mIconStr;
    private int mDayNum;
    private int mMonth;
    private int mMaxHumidity;
    private int mMaxTemperature;
    private int mMinTemperature;

    public Vogue() { }

    public int getMinTemperature() {
        return mMinTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        mMinTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return mMaxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        mMaxTemperature = maxTemperature;
    }

    public String getIconStr() {
        return mIconStr;
    }

    public void setIconStr(String iconStr) {
        mIconStr = iconStr;
    }

    public int getMaxHumidity() {
        return mMaxHumidity;
    }

    public void setMaxHumidity(int maxHumidity) {
        mMaxHumidity = maxHumidity;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getDayNum() {
        return mDayNum;
    }

    public void setDayNum(int dayNum) {
        mDayNum = dayNum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {

        dest.writeString(mIconStr);
        dest.writeInt(mDayNum);
        dest.writeInt(mMonth);
        dest.writeInt(mMaxHumidity);
        dest.writeInt(mMaxTemperature);
        dest.writeInt(mMinTemperature);
    }

    private Vogue(Parcel in){
        mIconStr = in.readString();
        mDayNum = in.readInt();
        mMonth = in.readInt();
        mMaxHumidity = in.readInt();
        mMaxTemperature = in.readInt();
        mMinTemperature = in.readInt();
    }

    public static final Creator<Vogue> CREATOR = new Creator<Vogue>() {
        @Override
        public Vogue createFromParcel(Parcel source) {
            return new Vogue(source);
        }

        @Override
        public Vogue[] newArray(int size) {
            return new Vogue[size];
        }
    };
}
