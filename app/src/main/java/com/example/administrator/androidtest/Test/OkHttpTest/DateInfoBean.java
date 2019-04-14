package com.example.administrator.androidtest.Test.OkHttpTest;

import com.google.gson.annotations.SerializedName;

public class DateInfoBean {
    private int code;
    private String msg;
    private DataBean data;

    static class DataBean{
        private String date;
        private String weekDay;
        private String yearTips;
        private int type;
        private String typeDes;
        private String chineseZodiac;
        private String solarTerms;
        private String avoid;
        private String lunarCalendar;
        private String suit;
        @SerializedName("dayOfYear")
        private int dayOfDay;
        private int weekOfYear;

        @Override
        public String toString() {
            return "DataBean{" +
                    "date='" + date + '\'' +
                    ", weekDay='" + weekDay + '\'' +
                    ", yearTips='" + yearTips + '\'' +
                    ", type=" + type +
                    ", typeDes='" + typeDes + '\'' +
                    ", chineseZodiac='" + chineseZodiac + '\'' +
                    ", solarTerms='" + solarTerms + '\'' +
                    ", avoid='" + avoid + '\'' +
                    ", lunarCalendar='" + lunarCalendar + '\'' +
                    ", suit='" + suit + '\'' +
                    ", dayOfDay=" + dayOfDay +
                    ", weekOfYear=" + weekOfYear +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DateInfoBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
