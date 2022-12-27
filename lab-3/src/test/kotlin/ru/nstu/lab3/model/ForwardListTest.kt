package ru.nstu.lab3.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ForwardListTest {

    @Test
    fun testListSizeAfterAddingElement() {
        var forwardList: ForwardList<Int> = ForwardList();

        forwardList.add(5);
        forwardList.add(6);
        forwardList.add(7);

        var actualSize = forwardList.size;
        var expectedSize = 3;

        assertEquals(actualSize, expectedSize);
    }

    @Test
    fun testListEmptyOnCreation() {
        var forwardList: ForwardList<Int> = ForwardList();
        assertTrue(forwardList.isEmpty());
    }

    @Test
    fun testListNotEmptyAfterInsertion() {
        var forwardList: ForwardList<Int> = ForwardList();
        forwardList.add(1);
        assertFalse(forwardList.isEmpty())
    }

    @Test
    fun testListEmptyAfterDelete() {
        var forwardList: ForwardList<Int> = ForwardList();
        forwardList.add(10);
        forwardList.remove(0)
        assertTrue(forwardList.isEmpty())
    }

    @Test
    fun testListCorrectAfterInsertion() {
        var forwardList: ForwardList<Int> = ForwardList();

        forwardList.add(10);
        forwardList.add(20);
        forwardList.add(30);

        var forwardListIterator = forwardList.iterator();
        var actualRootValue = forwardListIterator.next()
        var actualSecondValue = forwardListIterator.next()
        var actualThirdValue = forwardListIterator.next()

        var expectedRootValue = 10;
        var expectedSecondValue = 20;
        var expectedThirdValue = 30;

        assertEquals(actualRootValue, expectedRootValue);
        assertEquals(actualSecondValue, expectedSecondValue);
        assertEquals(actualThirdValue, expectedThirdValue);
    }

    @Test
    fun testListSort() {
        var listToSort: ForwardList<Int> = ForwardList();
        var sortedList: ForwardList<Int> = ForwardList();

        // Инициализация компаратора
        var intComparator: Comparator<Int> = Comparator() { a, b ->
            when {
                (a == b) -> 0
                (a < b) -> -1
                else -> 1
            }
        }

        // Заполнение списка значениями в случайном порядке
        listToSort.add(6);
        listToSort.add(5);
        listToSort.add(12);
        listToSort.add(10);
        listToSort.add(9);
        listToSort.add(1);

        // Заполнение списка с ожидаемым порядком значений
        sortedList.add(1);
        sortedList.add(5);
        sortedList.add(6);
        sortedList.add(9);
        sortedList.add(10);
        sortedList.add(12);

        listToSort.printList(description = "Unsorted: ")

        // Выполнение сортировки
        listToSort = listToSort.mergeSort(intComparator);
        listToSort.printList(description = "Sorted: ")

        assertTrue(listToSort.toList() == sortedList.toList())
    }

    @Test
    fun testGetMiddleForSort(){
        var forwardList: ForwardList<Int> = ForwardList();

        for (i in 1..5) { forwardList.add(i) }

        var actualMiddleValue = forwardList.getMiddle(forwardList.getRoot())!!.value
        var expectedMiddleValue = 3

        assertEquals(expectedMiddleValue, actualMiddleValue)
    }

    @Test
    fun testSublistsForSort(){
        var forwardList: ForwardList<Int> = ForwardList();
        for (i in 1..6) { forwardList.add(i) }

        // define actual
        var middleNode = forwardList.getMiddle(forwardList.getRoot())
        var endNode = forwardList.getEnd()

        val actualLeftSublist = forwardList.sublist(forwardList.getRoot(), middleNode)
        val actualRightSublist = forwardList.sublist(middleNode?.next, endNode)

        // define expected
        var expectedLeftSublist: ForwardList<Int> = ForwardList()
        var expectedRightSublist: ForwardList<Int> = ForwardList()

        expectedLeftSublist.add(1)
        expectedLeftSublist.add(2)
        expectedLeftSublist.add(3)

        expectedRightSublist.add(4)
        expectedRightSublist.add(5)
        expectedRightSublist.add(6)

        assertEquals(expectedLeftSublist.toList(), actualLeftSublist.toList())
        assertEquals(expectedRightSublist.toList(), actualRightSublist.toList())
    }
}