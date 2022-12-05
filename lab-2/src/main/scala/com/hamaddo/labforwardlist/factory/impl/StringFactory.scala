package com.hamaddo.labforwardlist.factory.impl

import com.hamaddo.labforwardlist.factory.ObjectFactory

import java.util.Comparator

private class StringFactory extends ObjectFactory {
  override def parse(input: String): String = {
    input
  }

  override def getObjectName: String = {
    "String"
  }

  override def getComparator: Comparator[Object] = {
    Comparator.comparing(o => o.asInstanceOf[String])
  }
}
