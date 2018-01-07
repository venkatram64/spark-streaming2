package com.venkat.scala.assign.commands

import com.venkat.scala.assign.State
import com.venkat.scala.assign.files.{DirEntry, Directory}

/**
  * Created by Venkatram on 1/7/2018.
  */
class MKdir(name: String) extends CreateEntry(name){

  override  def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.wd.path, name)

}

