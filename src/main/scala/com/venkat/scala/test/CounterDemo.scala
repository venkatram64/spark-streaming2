package com.venkat.scala.test

/**
  * Created by Venkatram on 1/7/2018.
  */
object CounterDemo extends App{
  val counter = new Counter(1)
  counter.increment(54)
  println(counter.value)
}

class Counter(var value: Int = 0){
  def increment(value: Int): Unit = {
    this.value += value
  }
}


