package com.venkat.scala.test


object HigherOrderFunc {
  def apply(): HigherOrderFunc = new HigherOrderFunc()
}


class HigherOrderFunc {

  def sum2(a: Int, b: Int): Int = {
    if(a > b) 0 else a + sum2(a + 1, b)
  }

  def sum(f: Int => Int, a: Int, b: Int): Int = {
    if(a > b) 0 else f(a) + sum(f, a + 1, b)
  }
// below function signatures will be matched to the
// above sum's parameter function signature

  def id(i: Int): Int = i

  def sqr(i: Int): Int = math.pow(i, 2).toInt

  def cube(i: Int): Int = i * i * i

  //anonymous functions
  /*
    (i: Int) => i
    (i: Int) => i * i
    (i: Int) => i * i * i
          or
    val i = (i: Int) => i
    val s = (i: Int) => i * i
    val c = (i: Int) => i * i * i
   */

  val i = (i: Int) => i
  val s = (i: Int) => i * i
  val c = (i: Int) => i * i * i


}

object HigherOrderFuncTest extends App {
  val hof = HigherOrderFunc()
  println(hof.sum2(3, 10))

  /*println(hof.sum(hof.id, 1, 10))
  println(hof.sum(hof.sqr, 1, 10))
  println(hof.sum(hof.cube, 1, 10))*/
  println(hof.sum(hof.i, 1, 10))
  println(hof.sum(hof.s, 1, 10))
  println(hof.sum(hof.c, 1, 10))
}




