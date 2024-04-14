package com.bear.librv;

public class DefaultTypeLinker<ITEM> implements TypeLinker<ITEM> {
    @Override
    public int index(ITEM item, int position) {
        return 0;
    }
}
