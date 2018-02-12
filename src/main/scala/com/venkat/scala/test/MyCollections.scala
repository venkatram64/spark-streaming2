package com.venkat.scala.test
//http://www.itversity.com/topic/additional-dependencies-and-importing/
//https://docs.scala-lang.org/overviews/collections/overview.html

object MyCollections {
  def apply(): MyCollections = new MyCollections()
}

class MyCollections {
  def oddEvenGen() = {
    val (even, odd) = (1 to 30).partition(_ % 2 == 0)

    even.map(println)

    odd.map(println)
  }
}

object CollectionsTest extends App{

    val mc = MyCollections();
    mc.oddEvenGen()

}
