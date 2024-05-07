package com.bear.librv;

public interface DelegateClassLinker<ITEM> {
    Class<? extends MultiTypeDelegate<ITEM, ?>> index(int position, ITEM item);
}
