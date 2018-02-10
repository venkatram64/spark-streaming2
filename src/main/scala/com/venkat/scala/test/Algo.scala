package com.venkat.scala.test

/**
  * Created by Venkatram on 2/10/2018.
  */

object Algo{
  def apply() = {
    val a = new Algo()
    a
  }
}

class Algo {

  def factorial(n:Int): Int = {
    var res = 1
    for(e <- 1 to n by 1){
      res = res * e
    }
    res
  }

  def factr(n: Int): Int = {
    if(n == 1) 1
    else n * factr(n - 1)
  }

  def nCr(n: Int, r: Int): Int = {
    factr(n)/(factr(r) * factr(n-r))
  }

  def nPr(n: Int, r: Int): Int = {
    factr(n)/(factr(n-r))
  }

  //nested functions
  def nCrNested(n: Int, r: Int): Int = {
    def fact(i: Int): Int = {
      var res = 1
      for(e <- i to 1 by - 1){
        res *= e
      }
      res
    }
    fact(n)/(fact(r) * fact(n - r))
  }

  def fibonacci(n: Int): Unit = {
    var prev = 0
    var next = 1
    println(prev)
    println(next)
    for(e <- 1 to n by 1){
      val res = prev + next
      println(res)
      prev = next
      next = res
    }
  }
}

object AlgoTest extends App{
  val a = Algo()
  println(a.factorial(5))
  a.fibonacci(10)

  println("Combinations: " + a.nCr(5,3))

  println("Combinations nested : " + a.nCrNested(5,3))

  println("Permutation: " + a.nPr(5,3))
}


