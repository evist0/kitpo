package com.evist0.labforwardlist.factory.impl;

import com.evist0.labforwardlist.factory.ObjectFactory;

import java.util.Comparator;

public class StringFactory implements ObjectFactory {
    @Override
    public Object parse(String input) {
        return input;
    }

    @Override
    public String getObjectName() {
        return "String";
    }

    @Override
    public Comparator getComparator() {
        return Comparator.comparing(o -> ((String) o));
    }
}
