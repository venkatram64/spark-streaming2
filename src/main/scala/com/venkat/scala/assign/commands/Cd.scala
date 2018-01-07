package com.venkat.scala.assign.commands

import com.venkat.scala.assign.State
import com.venkat.scala.assign.files.{DirEntry, Directory}

import scala.annotation.tailrec

/**
  * Created by Venkatram on 1/7/2018.
  */
class Cd(dir: String) extends Command{
  override def apply(state: State): State = {
    //1. find root
    val root = state.root
    val wd = state.wd
    //2. find the absolute path of the direcotry I want to cd to
    val absolutePath =
      if(dir.startsWith(Directory.SEPARATOR)) dir
      else if(wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir

    //3. find the directory to cd to, given the path
    val destinationDirectory = doFindEntry(root, absolutePath)
    //4. change the state given the new directory
    if(destinationDirectory == null || !destinationDirectory.isDirectory){
      state.setMessage(dir + ": no such directory")
    }else{
      State(root, destinationDirectory.asDirectory)
    }
  }

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry =
      if(path.isEmpty || path.head.isEmpty) currentDirectory
      else if(path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if(nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    //1. tokens
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    //2. navigate to the correct entry
    findEntryHelper(root, tokens)
  }
}
