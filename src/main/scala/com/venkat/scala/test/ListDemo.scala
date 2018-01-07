package com.venkat.scala.test

import scala.annotation.tailrec

/**
  * Created by Venkatram on 1/6/2018.
  */
object ListDemo extends App{
  val listA = Nil
  val listB = new Cons(2, new Cons(4, Nil))

  println(listA)
  println(listB)

  println(listB.reverse)
  //println(listA.head)

  println(listB ++ new Cons(5, new Cons(1,new Cons(9, Nil))))

  val listC = listB ++ new Cons(5, new Cons(1,new Cons(9, Nil)))
  val deepList = new Cons(listB, new Cons(listA, new Cons(listC, Nil)))

  println(List.flatten(deepList))

  val listD = List.flatten(deepList)
  val predicate: Predicate[Int] = new Predicate[Int] {
    override def apply(elem: Int): Boolean = elem % 2 == 0
  }

  println(listD.filter(predicate))
}


trait List[+T] { //co variant
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
  def add[S >: T](elem: S): List[S]  //type widening, S is super type of T -- lower bound
  def ++[S >: T](other: List[S]): List[S]
  def reverse: List[T]
  def filter[S >: T](predicate: Predicate[S]): List[S]
}
//empty List called as Nil, for all possible types type is Nothing, Nothing is possible place holder for everything
object Nil extends List[Nothing] {
  override def isEmpty: Boolean = true
  override def head: Nothing = throw new NoSuchElementException
  override def tail: List[Nothing] = throw new UnsupportedOperationException
 /* override def add[S >: Nothing](elem: S): List[S] = ???
  override def ++[S >: Nothing](other: List[S]):List[S] = other*/
  override def add[S](elem: S): List[S] = new Cons(elem, Nil)
  override def ++[S](other: List[S]):List[S] = other
  override def reverse: List[Nothing] = Nil
  override def filter[S](predicate: Predicate[S]): List[Nothing] = Nil
  override def toString: String = "[]"

}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  override def isEmpty: Boolean = false
  override def add[S >: T](elem: S): List[S] = new Cons(head, this)
  override def ++[S >: T](other: List[S]): List[S] = new Cons(head, tail ++ other)

  override def reverse: List[T] = {
    @tailrec
    def reverseUtil(input: List[T], output: List[T]): List[T] = {
      if(input.isEmpty) output
      else reverseUtil(input.tail, new Cons(input.head, output))
    }
    reverseUtil(this, Nil)
  }

  override def toString: String = {
    def enumerateAll(list: List[T]): String =
      if (list.isEmpty) ""
      else if (list.tail.isEmpty) "" + list.head
      else list.head + " " + enumerateAll(list.tail)

    "[" + enumerateAll(this) +"]"
  }

  override def filter[S >: T](predicate: Predicate[S]): List[S] = {
    if(predicate.apply(head)) new Cons(head, tail filter(predicate))
    else tail filter(predicate)
  }
}

object List{

  def flatten[T](deepList: List[List[T]]): List[T] = {
    /*if(deepList.isEmpty) Nil
    else deepList.head ++ flatten(deepList.tail)*/

    @tailrec
    def flattenUtil(remaining: List[List[T]], currentListExpanding: List[T], acc: List[T]): List[T] = {
      if(currentListExpanding.isEmpty) {
        if (remaining.isEmpty) acc
        else flattenUtil(remaining.tail, remaining.head, acc)
      }
      else flattenUtil(remaining, currentListExpanding.tail, new Cons(currentListExpanding.head, acc))
    }
    flattenUtil(deepList, Nil, Nil).reverse
  }
}

trait Predicate[T] {
  def apply(elem: T): Boolean
}


