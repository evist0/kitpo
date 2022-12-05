package com.hamaddo.labforwardlist.factory.impl

import com.hamaddo.labforwardlist.factory.ObjectFactory

import java.util.Comparator

private class IntegerFactory extends ObjectFactory {
  override def parse(input: String): Integer = {
    Integer.parseInt(input)
  }

  override def getObjectName: String = {
    "Integer"
  }

  override def getComparator: Comparator[Object] = {
    Comparator.comparingInt(o => o.asInstanceOf[Integer])
  }
}
