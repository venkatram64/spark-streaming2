package com.venkat.scala.test

object Fraction{
  def apply(n: Int, d: Int): Fraction = new Fraction(n,d)
}

class Fraction(val n: Int, val d: Int) {

  def +(f: Fraction) = {
    Fraction((n * f.d + d * f.n), (d * f.d))
  }

  def -(f: Fraction) = {
    Fraction(((n * f.d) - (d * f.n)), (d * f.d))
  }

  def *(f: Fraction) = {
    Fraction((n * f.n), (d * f.d))
  }

  def /(f: Fraction) = {
    Fraction((n * f.d), (d * f.n))
  }

  def result = n/d.toDouble

  override def toString = n + "/" + d
}

object FractionTest{
  def main(args: Array[String]) = {
    val f = Fraction(3,4)
    println(f)

    val f2 = Fraction(5,6)

    val r = f + f2

    println(r)

    println(r.result)
  }
}
