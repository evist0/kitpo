package ru.nstu.lab3.factory.impl

import ru.nstu.lab3.factory.ObjectFactory

class IntegerFactory : ObjectFactory<Int> {
    override fun parse(input: String): Int {
        return input.toInt()
    }

    override val objectName: String
        get() = "Integer"
    override val comparator: Comparator<Int>
        get() = Comparator.comparingInt { o: Any -> (o as Int) }
}