package com.venkat.scala.test


case class Order(orderId: Int, orderDate: String, orderCustomerId: Int, orderStatus: String)

object ListTest extends App{

  def sortDesc(a: Int): Int = -a

  val os = collection.immutable.List(
    Order(1,"2017-01-01",79,"NEW"),
    Order(2,"2017-02-02",101,"CLOSED"),
    Order(3,"2017-02-03",105,"PENDING"),
    Order(4,"2017-01-01",103,"COMPLETE"),
    Order(5,"2017-01-06",104,"CLOSED"),
    Order(6,"2017-01-21",105,"COMPLETE"),
    Order(7,"2017-01-11",10,"PENDING"),
    Order(8,"2017-01-17",60,"COMPLETE"),
    Order(9,"2017-01-19",80,"CLOSED"),
    Order(10,"2017-01-11",105,"COMPLETE")
  )

  os.map(x => x.orderDate.replace("-", "").toInt).foreach(println)
  os.filter(x => x.orderStatus == "COMPLETE").foreach(println)
  os.sortBy(x => x.orderCustomerId)
  os.groupBy(x => x.orderStatus)

  os.groupBy(x => x.orderStatus).map(x => (x._1, x._2.size)).foreach(println)

  val result = (1 to 100).filter(_ % 2 == 0).reduce((a, b) => a + b)
  println(result)

  val l = collection.immutable.List(2,0,7,3,-11,99,23,5,-17,1,4,9,12)
  //println(l.sorted)
  //l.sortBy(k => -k)
  println(l.sortBy(k => sortDesc(k)))

  os.sortWith((a,b) => {
    if(a.orderCustomerId > b.orderCustomerId)
      false
    else if (a.orderCustomerId < b.orderCustomerId)
      false
    else {
      if (a.orderId > b.orderId)
        false
      else
        true
    }
  }).foreach(println)
  println("**partition**")
  val (closed, notClosed) =
    os.partition(order => order.orderStatus == "CLOSED"
      || order.orderStatus == "COMPLETE")
  println("****")
  closed.foreach(println)
  println("****")
  notClosed.foreach(println)
  println("***par***")
  val (closed2, notClosed2) =
    os.par.partition(order => order.orderStatus == "CLOSED"
      || order.orderStatus == "COMPLETE")

  closed2.foreach(println)
  println("****")
  notClosed2.foreach(println)
}
