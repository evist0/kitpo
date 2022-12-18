package ru.nstu.lab3.factory

interface ObjectFactory<T : Any> {
    fun parse(input: String): Comparable<T>
    val objectName: String
    val comparator: Comparator<in T>
}