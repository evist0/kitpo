package com.evist0.labforwardlist.factory;

import java.util.Comparator;

public interface ObjectFactory {
    Object parse(String input);

    String getObjectName();

    Comparator getComparator();
}
