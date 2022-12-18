package ru.nstu.lab3.model

class ForwardList<T> : Iterable<T> {
    private inner class Node {
        val value: T
        var next: Node?

        constructor(value: T, next: Node?) {
            this.value = value
            this.next = next
        }

        constructor(value: T) {
            this.value = value
            next = null
        }
    }

    private var root: Node? = null

    private var _size = 0
    public val size: Int
        get() {
            return _size
        }

    constructor() {
    }

    constructor(value: T) {
        add(value)
    }

    fun add(value: T) {
        if (root == null) {
            root = Node(value)
            _size += 1
            return
        }

        var previous = root

        while (previous?.next != null) {
            previous = previous.next
        }

        previous?.next = Node(value)
        _size += 1
    }

    fun insert(at: Int, value: T) {
        if (at == 0) {
            val next: Node? = root
            root = Node(value, next)
            return
        }

        var previous = root

        var i = 1
        while (i != at && previous?.next != null) {
            previous = previous.next
            ++i
        }
        if (i != at) {
            throw IndexOutOfBoundsException()
        }

        val next = previous?.next
        previous?.next = Node(value, next)
        _size += 1
    }

    fun remove(at: Int) {
        if (at == 0) {
            if (root == null) {
                throw IndexOutOfBoundsException()
            }

            root = root!!.next
            return
        }

        var previous = root

        var i = 1
        while (i != at && previous?.next != null) {
            previous = previous.next
            ++i
        }
        if (i != at) {
            throw IndexOutOfBoundsException()
        }

        previous?.next = previous?.next?.next
        _size -= 1;
    }

    fun toList(): List<T> {
        val arrayList = ArrayList<T>()
        var current: Node? = root

        while (current != null) {
            arrayList.add(current.value)
            current = current.next
        }

        return arrayList
    }

    fun mergeSort(comparator: Comparator<in T>): ForwardList<T> {
        // Если пустой список — возвращаем новый пустой список
        if (root == null) return ForwardList()

        // Если один элемент - возвращаем новый список на один элемент
        if (size < 2) return ForwardList(root!!.value)

        // Находим центральный узел
        val middle: Node? = getMiddle(root)
        // Находим конечный узел
        val end = getEnd()

        // Рекурсивно получаем подсписки
        val left = this.sublist(root, middle).mergeSort(comparator)
        val right = this.sublist(middle?.next, end).mergeSort(comparator)

        // Сливаем подсписки
        return merge(left, right, comparator)
    }

    private fun merge(a: ForwardList<T>, b: ForwardList<T>, comparator: Comparator<in T>): ForwardList<T> {
        val merged = ForwardList<T>();

        val aIterator: Iterator<T> = a.iterator()
        val bIterator: Iterator<T> = b.iterator()

        var aValue: T? = if (aIterator.hasNext()) aIterator.next() else null
        var bValue: T? = if (bIterator.hasNext()) bIterator.next() else null

        while (aValue != null || bValue != null) {
            if (bValue == null || (aValue != null && comparator.compare(aValue, bValue) < 0)) {
                merged.add(aValue!!)
                aValue = if (aIterator.hasNext()) aIterator.next() else null
            } else {
                merged.add(bValue)
                bValue = if (bIterator.hasNext()) bIterator.next() else null
            }
        }

        return merged
    }

    private fun sublist(from: Node?, to: Node?): ForwardList<T> {
        val result = ForwardList<T>()
        var temp: Node? = from

        while (temp != to) {
            if (temp == null) {
                throw RuntimeException("Can not find such sublist")
            }

            result.add(temp.value)
            temp = temp.next
        }

        if (temp != null) {
            result.add(temp.value)
        }

        return result
    }

    private fun getMiddle(head: Node?): Node? {
        if (head == null) {
            return null
        }

        var slow = head
        var fast = head

        while (fast!!.next != null && fast.next!!.next != null) {
            slow = slow!!.next
            fast = fast.next!!.next
        }

        return slow
    }

    private fun getEnd(): Node {
        if (root == null) {
            throw RuntimeException()
        }

        var temp = root

        while (temp!!.next != null) {
            temp = temp.next
        }

        return temp
    }

    override fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            private var node: ForwardList<T>.Node? = root

            override fun hasNext(): Boolean {
                return node != null
            }

            override fun next(): T {
                if (node == null) {
                    throw NullPointerException()
                }

                val value: T = node!!.value
                node = node?.next
                return value
            }
        }
    }
}
