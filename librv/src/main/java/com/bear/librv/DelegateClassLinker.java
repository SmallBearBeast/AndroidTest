package com.bear.librv;

public interface DelegateClassLinker<ITEM> {
    Class<MultiTypeDelegate<ITEM, ?>> index(int position, ITEM item);
}
