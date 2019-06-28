package com.example.administrator.androidtest.Common.PageShareData;

import java.util.Objects;

public class PageKey {
    public String mPage;
    public int mIndex;

    public PageKey(String page, int index) {
        mPage = page;
        mIndex = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageKey pageKey = (PageKey) o;
        return mIndex == pageKey.mIndex &&
                Objects.equals(mPage, pageKey.mPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mPage, mIndex);
    }
}
