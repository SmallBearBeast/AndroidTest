package com.example.administrator.androidtest.Test.RvActTest;

public class Info {
    private int mId;
    public String mText_1;
    public String mText_2;
    public String mText_3;

    public String mUrl_1 = "https://cdn.stocksnap.io/img-thumbs/960w/YJIGVWFX9L.jpg";
    public String mUrl_2 = "https://image.shutterstock.com/image-photo/fried-spring-roll-delicious-sauce-600w-1089308132.jpg";
    public String mUrl_3 = "https://cdn.stocksnap.io/img-thumbs/280h/NOXXUWUBGJ.jpg";

    public Info(int id) {
        mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return mId == info.mId;
    }
}
