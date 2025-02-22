package com.example.administrator.androidtest.widget.FullTextView;

public class TextOpt {
    public int mBgColor;
    public int mFgColor;
    public int mDeleteLineColor;
    public int mUnderLineColor;
    public int mStart;
    public int mEnd;
    public int mStyle;
    public Runnable mRun;
    public String mUrl;
    public int mSize;

    public static TextOpt bgOpt(int start, int end, int bgColor){
        TextOpt opt = new TextOpt();
        opt.mStart = start;
        opt.mEnd = end;
        opt.mBgColor = bgColor;
        return opt;
    }

    public static TextOpt fgOpt(int start, int end, int fgColor){
        TextOpt opt = new TextOpt();
        opt.mStart = start;
        opt.mEnd = end;
        opt.mFgColor = fgColor;
        return opt;
    }

    public static TextOpt clickOpt(int start, int end, Runnable run){
        TextOpt opt = new TextOpt();
        opt.mStart = start;
        opt.mEnd = end;
        opt.mRun = run;
        return opt;
    }

    public static TextOpt sizeOpt(int start, int end, int size){
        TextOpt opt = new TextOpt();
        opt.mStart = start;
        opt.mEnd = end;
        opt.mSize = size;
        return opt;
    }

    public static TextOpt deletelineOpt(int start, int end, int deleteLineColor){
        TextOpt opt = new TextOpt();
        opt.mStart = start;
        opt.mEnd = end;
        opt.mDeleteLineColor = deleteLineColor;
        return opt;
    }

    public static TextOpt underlineOpt(int start, int end, int underLineColor){
        TextOpt opt = new TextOpt();
        opt.mStart = start;
        opt.mEnd = end;
        opt.mUnderLineColor = underLineColor;
        return opt;
    }

    public static TextOpt styleOpt(int start, int end, int style){
        TextOpt opt = new TextOpt();
        opt.mStart = start;
        opt.mEnd = end;
        opt.mStyle = style;
        return opt;
    }


    public static TextOpt urlOpt(int start, int end, String url){
        TextOpt opt = new TextOpt();
        opt.mStart = start;
        opt.mEnd = end;
        opt.mUrl = url;
        return opt;
    }
}
