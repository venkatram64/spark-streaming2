package com.venkat.scala.test

/**
  * Created by Venkatram on 1/5/2018.
  */

/*
class parameters are not members
class Rational(n: Int, d: Int) {

}
 */

/*
promoting parameters to members
 */

class Rational(var n: Int, val d: Int) { //primary constructor

  def this(d: Int) = this(1, d)   //auxiliary constructor

  private[this] def this() = this(0) //can restrict visibility
  /*
    Auxiliary constructors must call another constructor first
    Auxiliary constructors cannot call super-constructors!(only the primary can)

    val r = new Rational(2)
   */

  val sign: Boolean = n * d > 0

  //return call may be omitted
  //parameter less function do not need paranthesis, same for constructors
  def add(that: Rational): Rational = {
    new Rational(n * that.d + d * that.n, d * that.d)
  }

  def +(that: Rational): Rational = {
    new Rational(n * that.d + d * that.n, d * that.d)
  }

  def abs = if(sign) this //access the current instaance
            else new Rational(-n,d)

  //define a unary method,
  //unary method is for only +,-,!,~
  def unary_- : Rational = new Rational(-n, d)

  def printPretty = n + "/" + d
}

object Rational extends App{

  def apply(n: Int, d: Int): Rational = new Rational(n, d)

  /*val x = new Rational(2,3)
  val y = new Rational(4,5)*/

  val x = Rational(2,3) //there is no need to have "new", apply automatically called to create the object
  val y = Rational(4,5)
  /*val x = rational.n
  rational.n = 4*/
  println(x.printPretty)
  //infix notation gives a lot of power
  val z = x + y //equivalent to x.+(y)
  println(z.printPretty)

  //prefix call
  val p = -x
  println(p.printPretty)
}

