package com.venkat.scala.test

/**
  * Created by Venkatram on 1/7/2018.
  */


object AnimalDemo extends App{

  val predator = new Cheetah
  predator.eat(new Gazelle)

}

trait Animal {
  def sayMyName: String
}

trait Predator extends Animal {
  def eat(other: Animal): Unit
}

class Gazelle extends Animal {
  override def sayMyName: String = "Helpless gazelle"
}

class Cheetah extends Predator {
  override def eat(other: Animal) = {
    println(sayMyName + " Eating " + other.sayMyName)
  }
  override def sayMyName = "Rawr!"
}
