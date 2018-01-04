package com.venkat.scala.test

import scala.annotation.tailrec

/**
  * Created by Venkatram on 1/3/2018.
  */
class MyUtils {

  def isPrime(x: Int): Boolean = {
    def isPrimeUntil(n: Int): Boolean = {
      if(n == 1) true
      else {
        (x % n != 0) && isPrimeUntil(n - 1)
      }
    }
    isPrimeUntil(x/2)
  }

  def factorial(x: Int): Int = {
    if(x <= 1) 1
    else x * factorial(x - 1)
  }

  //tail recursion
  def factorial2(n: Int): Int = {

    @tailrec def factorialAcc(x: Int, acc: Int = 1): Int = {
      if(x <= 1) acc
      else factorialAcc(x - 1, x * acc)
    }

    factorialAcc(n)
  }
  //string inter polation
  def myPrint(x: Int, y: Int) = {
    println(s"$x + $y = ${x + y}")
  }



}

object MyUtils extends App{
  val myUtils = new MyUtils()
  println(myUtils.isPrime(7))
}
