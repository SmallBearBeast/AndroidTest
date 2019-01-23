package com.example.administrator.androidtest.Common.Page;

import java.util.LinkedList;

public class Page {
    public @PageType int mPageId;

    public LinkedList<Page> mChildPageList = new LinkedList<>();

}
