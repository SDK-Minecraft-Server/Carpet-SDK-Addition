package com.renzaifei.carpetsdkaddition.utils;

import java.util.ArrayList;

public class ChainableList<T> extends ArrayList<T> {
    @SuppressWarnings("UnusedReturnValue")
    public ChainableList<T> cAdd(T element) {
        super.add(element);
        return this;
    }
}