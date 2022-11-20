package com.hamaddo.labforwardlist.factory

import java.util.Comparator

trait ObjectFactory {
  def parse(input: String): Object;

  def getObjectName: String;

  def getComparator: Comparator[_ <: Object];
}
