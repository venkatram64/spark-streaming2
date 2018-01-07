package com.venkat.scala.assign.commands

import com.venkat.scala.assign.State
import com.venkat.scala.assign.files.{DirEntry}

/**
  * Created by Venkatram on 1/7/2018.
  */
class Ls extends Command{

  override def apply(state: State): State = {
    val contents = state.wd.contents
    val niceOutput = createNiceOutput(contents)
    state.setMessage(niceOutput)
  }

  def createNiceOutput(contents: List[DirEntry]): String = {
    if(contents.isEmpty) ""
    else{
      val entry = contents.head
      entry.name +"[" + entry.getType + "]" +"\n"+ createNiceOutput(contents.tail)
    }
  }
}
