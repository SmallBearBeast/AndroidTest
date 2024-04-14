package com.bear.librv;

public class OneToManyBuilder<ITEM> {
    private final MultiTypeAdapter adapter;
    private final Class<ITEM> clazz;
    private MultiTypeDelegate<ITEM, ?>[] delegates = new MultiTypeDelegate[]{};

    public OneToManyBuilder(MultiTypeAdapter adapter, Class<ITEM> clazz) {
        this.adapter = adapter;
        this.clazz = clazz;
    }

    public OneToManyBuilder<ITEM> to(MultiTypeDelegate<ITEM, ?>... delegateArray) {
        delegates = delegateArray;
        return this;
    }

    public void withLinker(TypeLinker<ITEM> typeLinker) {
        for (MultiTypeDelegate<ITEM, ?> bridge : delegates) {
            adapter.register(new Type<>(clazz, bridge, typeLinker));
        }
    }

    public void withClassLinker(DelegateClassLinker<ITEM> delegateClassLinker) {
        withLinker(new DelegateClassLinkerBridge<>(delegateClassLinker, delegates));
    }

    private static class DelegateClassLinkerBridge<ITEM> implements TypeLinker<ITEM> {
        private final DelegateClassLinker<ITEM> delegateClassLinker;
        private final MultiTypeDelegate<ITEM, ?>[] delegates;

        public DelegateClassLinkerBridge(DelegateClassLinker<ITEM> delegateClassLinker, MultiTypeDelegate<ITEM, ?>[] delegates) {
            this.delegateClassLinker = delegateClassLinker;
            this.delegates = delegates;
        }

        @Override
        public int index(ITEM item, int position) {
            Class<MultiTypeDelegate<ITEM, ?>> delegateClass = delegateClassLinker.index(position, item);
            for (int i = 0; i < delegates.length; i++) {
                if (delegates[i].getClass() == delegateClass) {
                    return i;
                }
            }
            return 0;
        }
    }
}
