package com.venkat.scala.test

import scala.annotation.tailrec
import collection.immutable.List._



/**
  * Created by Venkatram on 2/10/2018.
  */

object AlgoMatch{
  def apply() = {
    val a = new AlgoMatch()
    a
  }
}
class AlgoMatch {

  def fib(n: Int): BigInt = {
    @tailrec
    def fibHelper(n: Int, prev: BigInt = 0, next: BigInt = 1): BigInt = n match {
      case 0 => prev
      case 1 => next
      case _ => fibHelper(n - 1, next, (next + prev))
    }
    fibHelper(n)
  }

  def product(list: collection.immutable.List[Int]): Int = list match {
    case collection.immutable.Nil => 1
    case n :: rest => n * product(rest)
  }

  def product2(list: collection.immutable.List[Int]): Int = {
    @tailrec
    def productAccum(list: collection.immutable.List[Int], accum: Int): Int = {
      list match {
        case collection.immutable.Nil => accum
        case head :: tail => productAccum(tail, accum * head)
      }
    }
    productAccum(list, 1)
  }

  def max(n: collection.immutable.List[Int]): Int = {
    @tailrec
    def maxAccum(n: collection.immutable.List[Int], theMax: Int): Int = {
      n match {
        case collection.immutable.Nil => theMax
        case head :: tail =>
          val newMax = if(head > theMax) head else theMax //ternary operator
          maxAccum(tail, newMax)
      }
    }
    maxAccum(n, 0)
  }

  def sum(n: collection.immutable.List[Int]): Int = {
    @tailrec
    def sumHelper(n: collection.immutable.List[Int], accum: Int): Int = {
      n match {
        case collection.immutable.Nil => accum
        case h :: tail => sumHelper(tail, accum + h)
      }
    }
    sumHelper(n, 0)
  }
  /*

  import scala.collection.JavaConversions.propertiesAsScalaMap

  converts Java Properties to a scala map just to get an interesting example
  for((k,v) <- System.getProperties())
    println(k + "->" +v)

  for each key, value pair in the map, k is bound to the key and v to the value.
  In a for comprehension, match failure are silently ignored. For example, the following
  loop prints all keys with empty value, skipping over all others
  for((k,"") <- System.getProperties())
    println(k)

  you can also use guard. Note that the if goes after the <- symbol:

  for((k, v) <- System.getProperties() if v == "")
    println(k)
   */

}

object AlgoMatchTest extends App {
  val a = AlgoMatch()
  for(e <- 0 to 10 by 1) {
    println(a.fib(e))
  }

  println("product: " + a.product(collection.immutable.List(1,2,3,4,5,6)))
  println("max: " + a.max(collection.immutable.List(1,2,3,4,5,6)))

  println("sum: " + a.sum(collection.immutable.List(1,2,3,4,5,6)))
}
