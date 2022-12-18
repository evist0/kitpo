package ru.nstu.lab3.factory.impl

import ru.nstu.lab3.factory.ObjectFactory

class StringFactory : ObjectFactory<String> {
    override fun parse(input: String): String {
        return input
    }

    override val objectName: String
        get() = "String"

    override val comparator: Comparator<String>
        get() = Comparator.comparing { o: Any -> (o as String) }
}