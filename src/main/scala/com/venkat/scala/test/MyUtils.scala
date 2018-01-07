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

  //format interpolation
  def myPrintf():Unit = {
    val speed = 1.2f
    val name = "Venkatram"
    println(f"$name%s can eat $speed%2.2f burgers per minute")
    //%s string
    //%2.2f float number format
  }

  //raw
  def rawPrintf(): Unit = {
    println(raw"Un-escaped: this is a \n newline")
    val anEscapedStr = "This is a \n newline"
    println(raw"Un-escaped: $anEscapedStr")
  }

  //call by value
  /*
  evaluate before entering the function
  use the same value everywhere
   */
  def callByValue(x: Long) = {
    println("By value :" + x) //x will be replace by 28389056590776
    println("By value :" + x) //x will be replace by 28389056590776
  }

  //call by name
  /*
  passes expression without evaluation
  recomputes the expression everywhere
   */
  def callByName(x: =>Long) = {
    println("By name :" + x) //x will be replace by System.nanoTime()
    println("By name :" + x)  //x will be replace by System.nanoTime()
  }

  def infinite: Int = 1 + infinite

  def printFirst(x: Int, y: => Int) = println(x)

}

object MyUtils extends App{
  val myUtils = new MyUtils()
  println(myUtils.isPrime(7))

  println("By value called")
  myUtils.callByValue(System.nanoTime())
  println("By name called")
  myUtils.callByName(System.nanoTime())

  //myUtils.printFirst(myUtils.infinite,2) //this will fail, due to infinite recursive call
  myUtils.printFirst(2,myUtils.infinite)

}
