package com.example.administrator.androidtest.Test.RvActTest;

public class Image {
    private int mId;

    public Image(int mId) {
        this.mId = mId;
    }

    public String mUrl_1 = "https://cdn.stocksnap.io/img-thumbs/960w/YJIGVWFX9L.jpg";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return mId == image.mId;
    }

}
