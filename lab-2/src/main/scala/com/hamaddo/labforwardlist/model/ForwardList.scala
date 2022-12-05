package com.hamaddo.labforwardlist.model

import java.util.Comparator

class ForwardList extends Iterable[Object] {
  private class Node(val value: Object, var next: Option[Node]) {
    def this(value: Object) = {
      this(value, None)
    }
  }

  private var root: Option[Node] = None

  def add(value: Object): Unit = {
    if (root.isEmpty) {
      root = Option(new Node(value))
      return
    }

    var previous: Node = root.get

    while (previous.next.isDefined) {
      previous = previous.next.get
    }

    previous.next = Option(new Node(value))
  }

  def insert(at: Int, value: Object): Unit = {
    if (at == 0) {
      val next = root
      root = Option(new Node(value, next))
      return
    }

    var previous: Node = root.get
    var i = 1

    while (i != at && previous.next.isDefined) {
      previous = previous.next.get
      i += 1
    }

    if (i != at) {
      throw new IndexOutOfBoundsException
    }

    val next = previous.next
    previous.next = Option(new Node(value, next))
  }

  def remove(at: Int): Unit = {
    if (at == 0) {
      if (root.isEmpty) {
        throw new IndexOutOfBoundsException
      }

      root = root.get.next
      return
    }

    var previous: Node = root.get
    var i = 1

    while (i != at && previous.next.isDefined) {
      previous = previous.next.get
      i += 1
    }

    if (i != at) {
      throw new IndexOutOfBoundsException
    }

    previous.next = previous.next.get.next
  }

  def sort(comparator: Comparator[Object]): Unit = {
    root = mergeSort(root, comparator)
  }

  override def toList: List[Object] = {
    var arrayList: List[Object] = List()

    var current: Option[Node] = root

    while (current.isDefined) {
      arrayList = arrayList :+ current.get.value
      current = current.get.next
    }

    arrayList
  }

  override def iterator: Iterator[Object] = new Iterator[Object]() {
    private var node = root

    override def hasNext: Boolean = node.isDefined

    override def next: Object = {
      val value = node.get.value
      node = node.get.next
      value
    }
  }

  private def mergeSort(h: Option[Node], comparator: Comparator[Object]): Option[Node] = {
    if (h.isEmpty || h.get.next.isEmpty) {
      return h
    }

    val middle = getMiddle(h)
    val nextOfMiddle = middle.get.next

    middle.get.next = None

    val left = mergeSort(h, comparator)

    val right = mergeSort(nextOfMiddle, comparator)

    sortedMerge(left, right, comparator)
  }

  private def sortedMerge(a: Option[Node], b: Option[Node], comparator: Comparator[Object]): Option[Node] = {
    val merged = Option(new Node(None))
    var temp = merged

    var a_ = a
    var b_ = b

    while (a_.isDefined && b_.isDefined) {
      if (comparator.compare(a_.get.value, b_.get.value) < 0) {
        temp.get.next = a_
        a_ = a_.get.next
      }
      else {
        temp.get.next = b_
        b_ = b_.get.next
      }

      temp = temp.get.next
    }

    while (a_.isDefined) {
      temp.get.next = a_
      a_ = a_.get.next
      temp = temp.get.next
    }

    while (b_.isDefined) {
      temp.get.next = b_
      b_ = b_.get.next
      temp = temp.get.next
    }

    merged.get.next
  }

  private def getMiddle(head: Option[Node]): Option[Node] = {
    if (head.isEmpty) {
      return None
    }

    var slow = head
    var fast = head

    while (fast.get.next.isDefined && fast.get.next.get.next.isDefined) {
      slow = slow.get.next
      fast = fast.get.next.get.next
    }

    slow
  }
}