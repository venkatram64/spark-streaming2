package com.venkat.scala.work

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Created by Venkatram on 12/4/2017.
  */
object DataFrame3 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()

  import sparkSession.implicits._
  val df = sparkSession.read.option("header", "true").option("inferSchema", "true").csv("Sales.csv")

  //show schema
  df.printSchema()

  df.show()

  df.groupBy("Company").mean().show()
  df.orderBy("Sales").show() //ascending
  df.orderBy($"Sales".desc).show()

  df.select(countDistinct("Sales")).show()
  df.select(sumDistinct("Sales")).show()
  df.select(variance("Sales")).show()
  df.select(stddev("Sales")).show()
  df.select(collect_set("Sales")).show()
}
