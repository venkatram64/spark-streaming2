package com.venkat.scala.assign.commands

import com.venkat.scala.assign.State

/**
  * Created by Venkatram on 1/7/2018.
  */
class Pwd extends Command{

  override def apply(state: State): State = {
    state.setMessage(state.wd.path)
  }

}
