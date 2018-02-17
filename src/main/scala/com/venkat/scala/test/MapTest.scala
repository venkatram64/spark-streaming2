package com.venkat.scala.test

//https://www.youtube.com/watch?v=ecJXK27eCOo&list=PLf0swTFhTI8rv85gSUEyBxN6fdv5D2E0C

case class Order3(orderId: Int, orderDate: String, orderCustomerId: Int, orderStatus: String)


object MapTest extends App{

  val m = Map(1 -> "Venkatram", 2 -> "Srijan") //by default whith is mutable Map

  println(m.get(1))

  println(m.get(2).get)

  //m.foreach(println)

  val keys = m.keySet
  keys.foreach(k => println(m.get(k).get))

  val m1 = collection.mutable.Map(
    1 -> Order3(1,"2017-01-01",79,"NEW"),
    2 -> Order3(2,"2017-02-02",101,"CLOSED"),
    3 -> Order3(3,"2017-02-03",105,"PENDING"),
    4 -> Order3(4,"2017-01-01",103,"COMPLETE")
  )
  //add
  m1 += ((5,  Order3(5,"2017-01-06",104,"CLOSED")))  //this is the tuple notation

  m1 += (6->  Order3(6,"2017-03-09",107,"New"))  //map notation

  val keys1 = m1.keySet
  keys1.foreach(k => println(m1.get(k).get))

  println("******************")
  var m2 = Map(
    1 -> Order3(11,"2017-01-01",79,"NEW"),
    2 -> Order3(21,"2017-02-02",101,"CLOSED"),
    3 -> Order3(31,"2017-02-03",105,"PENDING"),
    4 -> Order3(41,"2017-01-01",103,"COMPLETE")
  )
  //add
  m2 = m2 + ((15,  Order3(5,"2017-01-06",104,"CLOSED")))  //this is the tuple notation

  m2 = m2 + (16->  Order3(6,"2017-03-09",107,"New"))  //map notation

  val keys2 = m2.keySet
  keys2.foreach(k => println(m2.get(k).get))

  println("keys")
  m2.filterKeys(p => if(p != 3 && p != 4) true else false)
   .foreach(k => println(k._2))

  println("values")
  m2.mapValues(order => (order.orderStatus,order.orderDate))
    .foreach(p => println(p._2))

  println("map values")
  m2.mapValues(order => (order.orderStatus,order.orderDate))
    .map(p => println(p._2))
}
