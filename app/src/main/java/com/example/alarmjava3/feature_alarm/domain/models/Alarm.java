package com.example.alarmjava3.feature_alarm.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable {
    private final String time;
    private final boolean repeatWeekly;
    private final String days;
    private final int volume;
    private final String name;

    public Alarm(String time, boolean repeatWeekly, String days, int volume, String name) {
        this.time = time;
        this.repeatWeekly = repeatWeekly;
        this.days = days;
        this.volume = volume;
        this.name = name;
    }

    protected Alarm(Parcel in) {
        time = in.readString();
        repeatWeekly = in.readByte() != 0;
        days = in.readString();
        volume = in.readInt();
        name = in.readString();
    }

    public static final Parcelable.Creator<Alarm> CREATOR = new Parcelable.Creator<>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(time);
        out.writeByte((byte) (repeatWeekly ? 1 : 0));
        out.writeString(days);
        out.writeInt(volume);
        out.writeString(name);
    }


    public String getTime() {
        return time;
    }

    public boolean isRepeatWeekly() {
        return repeatWeekly;
    }

    public String getDays() {
        return days;
    }

    public int getVolume() {
        return volume;
    }

    public String getName() {
        return name;
    }
}
