package com.venkat.scala.assign.commands

import com.venkat.scala.assign.State
import com.venkat.scala.assign.files.{DirEntry, File}

/**
  * Created by Venkatram on 1/7/2018.
  */
class Touch(name: String) extends CreateEntry(name){

  override  def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path, name)

}
