package com.bear.librv;

public class Type<ITEM> {
    private final Class<ITEM> clazz;
    private final MultiTypeDelegate<ITEM, ?> delegate;
    private final TypeLinker<ITEM> typeLinker;

    public Type(Class<ITEM> clazz, MultiTypeDelegate<ITEM, ?> delegate, TypeLinker<ITEM> typeLinker) {
        this.clazz = clazz;
        this.delegate = delegate;
        this.typeLinker = typeLinker;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public MultiTypeDelegate<ITEM, ?> getDelegate() {
        return delegate;
    }

    public TypeLinker<ITEM> getLinker() {
        return typeLinker;
    }
}
