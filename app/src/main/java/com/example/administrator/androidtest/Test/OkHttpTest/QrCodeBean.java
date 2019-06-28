package com.example.administrator.androidtest.Test.OkHttpTest;

public class QrCodeBean {
    private int code;
    private String msg;
    private DataBean data;

    static class DataBean{
        private String qrCodeUrl;
        private String content;
        private int type;
        private String qrCodeBase64;

        @Override
        public String toString() {
            return "DataBean{" +
                    "qrCodeUrl='" + qrCodeUrl + '\'' +
                    ", content='" + content + '\'' +
                    ", mType=" + type +
                    ", qrCodeBase64='" + qrCodeBase64 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QrCodeBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
