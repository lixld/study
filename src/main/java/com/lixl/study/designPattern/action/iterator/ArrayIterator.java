package com.lixl.study.designPattern.action.iterator;


import java.util.NoSuchElementException;
public class ArrayIterator<E> implements MyIterator<E> {
    private int cursor;
    private java.util.ArrayList<E> arrayList;

    public ArrayIterator(java.util.ArrayList arrayList) {
        this.cursor = 0;
        this.arrayList = arrayList;
    }

    @Override
    public boolean hasNext() {
        return cursor < arrayList.size();
    }

    @Override
    public void next() {
        cursor++;
    }

    @Override
    public E currentItem() {
        if (cursor >= arrayList.size()) {
            throw new NoSuchElementException();
        }
        return arrayList.get(cursor);
    }
}
