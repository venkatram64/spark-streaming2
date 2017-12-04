package com.venkat.scala.work

import org.apache.spark.sql.SparkSession

/**
  * Created by Venkatram on 12/4/2017.
  */
object DataFrame2 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()


  //val df = sparkSession.read.csv("CitiGroup2006_2008")
  val df = sparkSession.read.option("header", "true").option("inferSchema", "true").csv("CitiGroup2006_2008")

  df.printSchema()



  import sparkSession.implicits._

  df.select($"Date",$"Close", $"Volume").show()
  //df.filter($"Close" > 480).show() //scala notation

  df.filter("Close > 480").show()//sql notation
}
