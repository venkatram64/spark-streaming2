package com.venkat.scala.work

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql._

/**
  * Created by Venkatram on 12/4/2017.
  */
object DataFrame2 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()

  import sparkSession.implicits._

  //val df = sparkSession.read.csv("CitiGroup2006_2008")
  val df = sparkSession.read.option("header", "true").option("inferSchema", "true").csv("CitiGroup2006_2008")

  df.printSchema()


  //df.select($"Date",$"Close", $"Volume").show() //scala notation,beofore show, that is transformation here show is the action
  //df.filter($"Close" > 480).show() //scala notation

  df.filter($"High" === 484.40).show()
  //or
  df.filter("High = 484.40").show()
  //Pearson correlation
  //df.select(corr("High","Low")).show()
  val hl = df.stat.corr("High","Low","pearson")
  println(hl)

  val clHigh = df.filter($"Close" < 480 && $"High" < 480).collect()

  //df.filter("Close < 480 AND High < 480").show()//sql notation, here show is the action
}
