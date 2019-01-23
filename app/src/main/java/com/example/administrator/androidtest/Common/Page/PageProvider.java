package com.example.administrator.androidtest.Common.Page;

import java.util.LinkedList;

public class PageProvider{
    private static final LinkedList<Integer> sPageNodeList = new LinkedList<>();
    private static final LinkedList<Page> sPageList = new LinkedList<>();

    public static class SingleTon{
        public static PageProvider sPageProvider = new PageProvider();
    }

    public static PageProvider getInstance(){
        return SingleTon.sPageProvider;
    }

    public void addPage(IPage page){
        if(page.page() != null) {
            sPageList.add(page.page());
        }
    }

    public void addPage(IPage parent, IPage child){
        if(parent.page() != null && child.page() != null){
            LinkedList<Page> list = parent.page().mChildPageList;
            list.add(child.page());
        }
    }

    public int currentPageId(){
        if(sPageNodeList.isEmpty())
            return -1;
        return sPageNodeList.getLast();
    }

    public int lastPageId(){
        int size = sPageNodeList.size();
        if(size <= 1)
            return -1;
        return sPageNodeList.get(size - 2);
    }
}
