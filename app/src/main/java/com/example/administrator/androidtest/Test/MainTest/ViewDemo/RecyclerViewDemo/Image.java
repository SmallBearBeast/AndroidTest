package com.example.administrator.androidtest.Test.MainTest.ViewDemo.RecyclerViewDemo;

public class Image {
    public int id;

    //    public String url = "https://cdn.stocksnap.io/img-thumbs/960w/YJIGVWFX9L.jpg"; // 这个加载403错误
    public String url = "https://upload-images.jianshu.io/upload_images/5809200-a99419bb94924e6d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";

    public Image(int id) {
        this.id = id;
    }

    public Image(int id, String url) {
        this.id = id;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return id == image.id;
    }
}
