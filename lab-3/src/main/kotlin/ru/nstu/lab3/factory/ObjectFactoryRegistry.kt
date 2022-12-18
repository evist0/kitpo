package ru.nstu.lab3.factory

import java.util.*
import java.util.stream.Collectors.toUnmodifiableList

object ObjectFactoryRegistry {
    private val factories: ArrayList<ObjectFactory<Any>> = ArrayList()

    init {
        val loadedFactories = ServiceLoader.load(
                ObjectFactory::class.java
        ) ?: throw RuntimeException("Factories not found")

        loadedFactories.forEach{ factory -> factories.add(factory as ObjectFactory<Any>) }
    }

    fun getFactory(factoryName: String): ObjectFactory<Any> {
        Objects.requireNonNull(factoryName)

        return factories
                .stream()
                .filter { x -> x.objectName == factoryName }
                .findFirst()
                .orElse(null)
    }

    val allFactories: List<String>
        get() = factories
                .stream()
                .map { obj -> obj.objectName }
                .collect(toUnmodifiableList())
}