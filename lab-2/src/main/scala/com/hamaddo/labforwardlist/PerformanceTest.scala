package com.hamaddo.labforwardlist

import com.hamaddo.labforwardlist.factory.ObjectFactoryRegistry
import com.hamaddo.labforwardlist.model.ForwardList

import java.util.Random

object PerformanceTest {
  def main(args: Array[String]): Unit = {
    val random = new Random

    for (i <- 1 to 10) {
      System.gc()

      val forwardList = new ForwardList

      val amount = 50_000 + (50_000 * i)

      System.out.println("[" + i + "]" + " Elements amount: " + amount)

      for (_ <- 0 until amount) {
        val randomValue: Integer = random.nextInt
        forwardList.add(randomValue)
      }

      val start = System.nanoTime

      val comparator = ObjectFactoryRegistry.getFactory("Integer").getComparator
      forwardList.sort(comparator)

      val end = System.nanoTime

      System.out.println("Milliseconds elapsed " + (end - start) * 1.0 / 1_000_000)
      System.out.println()
    }
  }


}