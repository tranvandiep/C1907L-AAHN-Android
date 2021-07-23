package com.gokisoft.c1907l.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Diep.Tran on 7/16/21.
 */

public class Food implements Parcelable{
    int _id;
    String thumbnail, title, description;

    public Food() {
    }

    public Food(int _id, String thumbnail, String title, String description) {
        this._id = _id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.description = description;
    }

    public Food(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Food(String thumbnail, String title, String description) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.description = description;
    }

    protected Food(Parcel in) {
        thumbnail = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail);
        dest.writeString(title);
        dest.writeString(description);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
