package com.venkat.scala.test

case class Order4(orderId: Int, orderDate: String, orderCustomerId: Int, orderStatus: String)

object TupleTest extends App{

  val l1 = collection.immutable.List((1,Set(
         Order4(1,"2017-01-01",79,"NEW"),
         Order4(2,"2017-02-02",101,"CLOSED"),
         Order4(3,"2017-02-03",105,"PENDING"),
         Order4(4,"2017-01-05",103,"COMPLETE"),
         Order4(11,"2017-01-09",79,"PENDING"),
         Order4(12,"2017-02-22",101,"CLOSED"),
         Order4(33,"2017-02-13",105,"PENDING"),
         Order4(42,"2017-01-21",103,"COMPLETE")
      )),
        (2,Set(
          Order4(11,"2017-01-01",69,"NEW"),
          Order4(22,"2017-02-02",141,"CLOSED"),
          Order4(33,"2017-02-03",125,"PENDING"),
          Order4(44,"2017-01-05",113,"COMPLETE"),
          Order4(112,"2017-01-09",179,"NEW"),
          Order4(120,"2017-02-22",181,"CLOSED"),
          Order4(330,"2017-02-13",195,"PENDING"),
          Order4(428,"2017-01-21",14,"COMPLETE")
        ))
    )

  println("Min")
  val ol = l1.map(rec => (rec._1, rec._2.minBy(k => k.orderId)))
  ol.foreach(rec => println(rec._2))

  println("Max")
  val ol2 = l1.map(rec => (rec._1, rec._2.maxBy(k => k.orderId)))
  ol2.foreach(rec => println(rec._2))

  println("Filter")
  val ol3 = l1.map(rec => (rec._1, rec._2.filter(order => order.orderStatus == "COMPLETE")))
  ol3.foreach(rec => println(rec._2))

  println("flatten")
  val ol4 = l1.flatMap(rec => rec._2.filter(order => order.orderStatus == "PENDING"))
  ol4.foreach(o => println(o))

}
