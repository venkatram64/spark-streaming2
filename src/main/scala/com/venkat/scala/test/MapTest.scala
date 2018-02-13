package com.venkat.scala.test

case class Order3(orderId: Int, orderDate: String, orderCustomerId: Int, orderStatus: String)


object MapTest extends App{

  val m = Map(1 -> "Venkatram", 2 -> "Srijan")

  println(m.get(1))

  println(m.get(2).get)

  //m.foreach(println)

  val keys = m.keySet
  keys.foreach(k => println(m.get(k).get))

  val m1 = Map(
    1 -> Order3(1,"2017-01-01",79,"NEW"),
    2 -> Order3(2,"2017-02-02",101,"CLOSED"),
    3 -> Order3(3,"2017-02-03",105,"PENDING"),
    4 -> Order3(4,"2017-01-01",103,"COMPLETE")
  )
  //add
  m1 + ((5,  Order2(5,"2017-01-06",104,"CLOSED")))

  val keys1 = m1.keySet
  keys1.foreach(k => println(m1.get(k).get))
}
