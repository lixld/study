package com.lixl.study.designPattern.action.iterator;

public interface MyIterator<E> {
    boolean hasNext();

    void next();

    E currentItem();
}
