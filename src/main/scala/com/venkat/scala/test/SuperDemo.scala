package com.venkat.scala.test

/**
  * Created by Venkatram on 1/6/2018.
  */
object SuperDemo extends App{
  var c = new C();
  c.print
}

trait Base1{
  def print: Unit = println("Base1")
}

trait A extends Base1 {
  override def print: Unit = {
    println("A")
    super.print
  }
}

trait B extends Base1 {
  override def print: Unit = {
    println("B")
    super.print
  }
}

class Base2 {
  def print: Unit = {
    println("Base2")
  }
}

class C extends Base2 with A with B {
  override def print: Unit = {
    println("C")
    super.print
  }
}

/**
  Base1 = AnyRef with <Base1>
  A = Base1 with <A>
    = AnyRef with <Base1> with <A>
  B = Base1 with <B>
    = AnyRef with <Base1> with <B>
  Base2 = AnyRef with <Base2>

  C = Base2 with A with B with <C>
    = AnyRef with <Base2>
    with(AnyRef with <Base1> with <A>)
    with(AnyRef with <Base1> with <B>)
    with <C>

  AnyRef with <Base2> with <Base1> with <A> with <B> with <C>
  */
