package com.hamaddo.labforwardlist.factory

import java.util
import java.util.stream.Collectors
import java.util.{Objects, ServiceLoader}

object ObjectFactoryRegistry {
  private val factories = new util.ArrayList[ObjectFactory]()

  private val loadedFactories: ServiceLoader[ObjectFactory] = ServiceLoader.load(classOf[ObjectFactory])

  loadedFactories.forEach(factory => {
    factories.add(factory)
  })

  def getFactory(factoryName: String): ObjectFactory = {
    Objects.requireNonNull(factoryName)

    factories.stream().filter(x => x.getObjectName.equals(factoryName)).findFirst().orElse(null)
  }

  def getAllFactories: util.List[String] = {
    factories.stream().map(x => x.getObjectName).collect(Collectors.toList[String])
  }
}
