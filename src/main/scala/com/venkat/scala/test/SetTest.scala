package com.venkat.scala.test

case class Order2(orderId: Int, orderDate: String, orderCustomerId: Int, orderStatus: String)

object SetTest extends App{

  val s1 = Set(
    Order2(1,"2017-01-01",79,"NEW"),
    Order2(2,"2017-02-02",101,"CLOSED"),
    Order2(3,"2017-02-03",105,"PENDING"),
    Order2(4,"2017-01-01",103,"COMPLETE")
  )

  val s2 = Set(
    Order2(5,"2017-01-06",104,"CLOSED"),
    Order2(6,"2017-01-21",105,"COMPLETE"),
    Order2(7,"2017-01-11",10,"PENDING"),
    Order2(8,"2017-01-17",60,"COMPLETE")
  )

  val os = s1 ++ s2 //for immutable


  os.foreach(println)

  println("mutable")

  val ms1 = collection.mutable.Set(
    Order2(1,"2017-01-01",79,"NEW"),
    Order2(2,"2017-02-02",101,"CLOSED"),
    Order2(3,"2017-02-03",105,"PENDING"),
    Order2(4,"2017-01-01",103,"COMPLETE"),
    Order2(8,"2017-01-17",60,"COMPLETE")
  )

  val ms2 = collection.mutable.Set(
    Order2(5,"2017-01-06",104,"CLOSED"),
    Order2(6,"2017-01-21",105,"COMPLETE"),
    Order2(7,"2017-01-11",10,"PENDING"),
    Order2(8,"2017-01-17",60,"COMPLETE")
  )

  ms1 ++= ms2  //mutable
  os.foreach(println)

  val msu = ms1 | ms2  //union

  val msi = ms1 & ms2  //intersect

  val msd = ms1 &~ ms2  //diff

}
