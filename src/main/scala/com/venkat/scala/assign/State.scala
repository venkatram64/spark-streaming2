package com.venkat.scala.assign

import com.venkat.scala.assign.files.Directory


/**
  * Created by Venkatram on 1/7/2018.
  */
class State(val root: Directory, val wd: Directory, val output: String) {

  def show: Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  def setMessage(message: String): State =
    State(root, wd, message)

}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, wd: Directory, output: String = ""): State =
              new State(root, wd, output)
}
