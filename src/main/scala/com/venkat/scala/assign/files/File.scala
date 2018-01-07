package com.venkat.scala.assign.files

import java.nio.file.FileSystemException

import com.venkat.scala.assign.FilesystemException

/**
  * Created by Venkatram on 1/7/2018.
  */
class File(override val parentPath: String, override val name: String, contents: String)
                        extends DirEntry(parentPath, name){

  def asDirectory: Directory =
    throw new FilesystemException("A file cannot be converted to a directory!")

  def getType: String = "File"

  def asFile: File = this

  def isDirectory: Boolean = false

  def isFile: Boolean = true
}

object File{
  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")
}
