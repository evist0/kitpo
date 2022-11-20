package com.hamaddo.labforwardlist.factory.impl

import com.hamaddo.labforwardlist.factory.ObjectFactory

import java.util.Comparator

class StringFactory extends ObjectFactory {
  override def parse(input: String): String = {
    return input;
  }

  override def getObjectName: String = {
    return "String"
  }

  override def getComparator: Comparator[String] = {
    return Comparator.comparing((o) => o.asInstanceOf[String])
  }
}
