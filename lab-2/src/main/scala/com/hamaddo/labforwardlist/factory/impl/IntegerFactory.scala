package com.hamaddo.labforwardlist.factory.impl

import com.hamaddo.labforwardlist.factory.ObjectFactory

import java.util.Comparator

class IntegerFactory extends ObjectFactory {
  override def parse(input: String): Integer = {
    return Integer.parseInt(input);
  }

  override def getObjectName: String = {
    return "Integer"
  }

  override def getComparator: Comparator[Integer] = {
    return Comparator.comparingInt((o) => o.asInstanceOf[Integer])
  }
}
