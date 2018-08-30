package com.chd.chd56lc.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class NotifyBean implements Parcelable {
    private String title;
    private String content;
    private String time;

    public NotifyBean() {
    }

    protected NotifyBean(Parcel in) {
        title = in.readString();
        content = in.readString();
        time = in.readString();
    }

    public static final Creator<NotifyBean> CREATOR = new Creator<NotifyBean>() {
        @Override
        public NotifyBean createFromParcel(Parcel in) {
            return new NotifyBean(in);
        }

        @Override
        public NotifyBean[] newArray(int size) {
            return new NotifyBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(time);
    }
}
