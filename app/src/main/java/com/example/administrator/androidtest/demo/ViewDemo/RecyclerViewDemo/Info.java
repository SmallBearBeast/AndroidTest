package com.example.administrator.androidtest.demo.ViewDemo.RecyclerViewDemo;

public class Info {
    private int id;
    public String text_1;
    public String text_2;
    public String text_3;

    public String url_1 = "https://cdn.stocksnap.io/img-thumbs/960w/YJIGVWFX9L.jpg";
    public String url_2 = "https://image.shutterstock.com/image-photo/fried-spring-roll-delicious-sauce-600w-1089308132.jpg";
    public String url_3 = "https://cdn.stocksnap.io/img-thumbs/280h/NOXXUWUBGJ.jpg";

    public Info(int id) {
        this.id = id;
        text_1 = "Info-1-" + id;
        text_2 = "Info-2-" + id;
        text_3 = "Info-3-" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return id == info.id;
    }
}
