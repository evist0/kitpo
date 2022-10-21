package com.evist0.labforwardlist.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public abstract class ObjectFactoryRegistry {
    private static final List<ObjectFactory> factories;

    static {
        factories = new ArrayList<>();

        ServiceLoader<ObjectFactory> loadedFactories = ServiceLoader.load(ObjectFactory.class);

        for (ObjectFactory factory : loadedFactories) {
            factories.add(factory);
        }
    }

    public static ObjectFactory getFactory(String factoryName) {
        Objects.requireNonNull(factoryName);

        return factories.stream().filter(x -> x.getObjectName().equals(factoryName)).findFirst().orElse(null);
    }

    public static List<String> getAllFactories() {
        return factories.stream().map(ObjectFactory::getObjectName).collect(Collectors.toList());
    }
}
