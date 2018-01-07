package com.venkat.scala.assign

import java.util.Scanner

import com.venkat.scala.assign.commands.Command
import com.venkat.scala.assign.files.Directory

/**
  * Created by Venkatram on 1/7/2018.
  */
object Filesystem extends App{

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while(true){
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }

}
