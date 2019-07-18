package com.example.administrator.androidtest.Base.Page;

import java.util.LinkedList;

// TODO: 2019-06-13 加上index
public class PageProvider{
    private static final byte FIRST = -1;
    private static final byte LAST = -2;
    private static final byte LIMIT_COUNT = 2; //每个节点最多只保存两个节点
    private final LinkedList<Integer> sPageNodeList = new LinkedList<>(); //记录页面路由
    private final LinkedList<Page> sPageList = new LinkedList<>();

    private static class SingleTon{
        static PageProvider sPageProvider = new PageProvider();
    }

    public static PageProvider getInstance(){
        return SingleTon.sPageProvider;
    }

    public void addPage(Page page){
        if(page != null) {
            if(sPageList.size() >= LIMIT_COUNT)
                sPageList.removeFirst();
            sPageList.add(page);
            sPageNodeList.add(page.mPageId);
        }
    }

    public void addPage(Page parent, Page child){
        if(parent != null && child != null){
            if(parent.mChildPageList.size() >= LIMIT_COUNT)
                parent.mChildPageList.removeFirst();
            parent.add(child);
            sPageNodeList.add(child.mPageId);
        }
    }

    /**
     * 获取当前page的id
     */
    public int curPageId(int level){
        Page page = curPage();
        if(page == null){
            return IPage.PageNone;
        }
        int count = getPageLevel(page);
        level = (level == FIRST ? 1 : (level == LAST ? count : level));
        if(level < 1 || level > count)
            throw new RuntimeException("level is out from range");
        return pageByLevel(level, page);
    }

    /**
     * 获取上一个page的id
     */
    public int lastPageId(int level){
        Page page = lastPage();
        if(page == null){
            return IPage.PageNone;
        }
        int count = getPageLevel(page);
        level = (level == FIRST ? 1 : (level == LAST ? count : level));
        if(level < 1 || level > count)
            throw new RuntimeException("level is out from range");
        return pageByLevel(level, page);
    }

    /**
     * 获取当前page
     */
    private Page curPage(){
        Page curPage = new Page(IPage.PageNone);
        Page temPage = curPage;
        if(!sPageList.isEmpty()){
            Page last = sPageList.getLast();
            while (last != null){
                Page page = new Page(last.mPageId);
                temPage.add(page);
                temPage = page;
                last = last.getLast();
            }
        }
        curPage = curPage.getLast();
        return curPage;
    }

    /**
     * 获取上一个page level是页面层级
     */
    public Page lastPage(){
        Page result = null;
        for (int i = sPageList.size() - 1; i >= 0; i--) {
            Page p = sPageList.get(i);
            result = lastPage(p);
            if(result != null){
                break;
            }
        }
        return result;
    }

    /**
     * 递归搜索一个page树的上一个page
     */
    private Page lastPage(Page page){
        if(page.isEmpty())
            return null;
        Page parent = new Page(page.mPageId);
        Page result = null;
        //递归结束条件
        if(page.isLastEmpty() && page.isSecondLastEmpty()){
            result = new Page(page.getSecondLast().mPageId);
        }else {
            for (int i = page.mChildPageList.size() - 1; i >= 0; i--) {
                Page p = page.mChildPageList.get(i);
                result = lastPage(p);
                if(result != null){
                    break;
                }
            }
        }
        if(result != null) {
            parent.add(result);
            return parent;
        }
        return null;
    }

    /**
     * 获取page层级数
     */
    private int getPageLevel(Page page){
        Page temp = page;
        int result = 0;
        while (temp != null){
            result ++;
            temp = temp.getLast();
        }
        return result;
    }

    /**
     * 通过层级搜索对应的pageid
     */
    private int pageByLevel(int level, Page page){
        int i = 1;
        Page temp = page;
        while (i < level){
            i ++;
            temp = temp.getLast();
        }
        return temp.mPageId;
    }
}
