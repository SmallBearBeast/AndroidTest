package com.example.administrator.androidtest.Base.Page;

import java.util.LinkedList;

public class Page {
    @PageType int mPageId;

    LinkedList<Page> mChildPageList = new LinkedList<>();

    public Page(@PageType int pageId){
        mPageId = pageId;
    }

    boolean isEmpty(){
        return mChildPageList.isEmpty();
    }

    private boolean isMoreOne(){
        return mChildPageList.size() > 1;
    }

    Page getLast(){
        if(!isEmpty()){
            return mChildPageList.getLast();
        }
        return null;
    }

    boolean isLastEmpty(){
        Page page = getLast();
        return page == null || page.isEmpty();
    }

    Page getSecondLast(){
        if(isMoreOne()){
            return mChildPageList.get(mChildPageList.size() - 2);
        }
        return null;
    }


    boolean isSecondLastEmpty(){
        Page page = getSecondLast();
        return page == null || page.isEmpty();
    }

    void add(Page page){
        mChildPageList.add(page);
    }
}
