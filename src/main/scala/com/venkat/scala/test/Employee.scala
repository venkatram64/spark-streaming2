package com.venkat.scala.test

/**
  * Created by Venkatram on 2/10/2018.
  */
class Employee {

  var firstName: String = _
  var lastName: String = _

}

object Employee {

  def apply(first: String, last: String) = {
    val e = new Employee

    e.firstName = first
    e.lastName = last
    e
  }

  def apply() = {
    val e = new Employee()
    e
  }
}

object EmpTest extends App{
  val e = Employee("Venkatram","Veerareddy")

  println(e.firstName, ", ", e.lastName)
}
