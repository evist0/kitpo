package com.evist0.labforwardlist.factory.impl;

import com.evist0.labforwardlist.factory.ObjectFactory;

import java.util.Comparator;

public class IntegerFactory implements ObjectFactory {
    @Override
    public Object parse(String input) {
        return Integer.parseInt(input);
    }

    @Override
    public String getObjectName() {
        return "Integer";
    }

    @Override
    public Comparator getComparator() {
        return Comparator.comparingInt(o -> (Integer) o);
    }
}
