package com.venkat.scala.work

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Created by Venkatram on 12/4/2017.
  */
object DataFrame4 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()

  import sparkSession.implicits._
  val df = sparkSession.read.option("header", "true").option("inferSchema", "true").csv("ContainsNull.csv")

  //show schema
  df.printSchema()

  df.show()

  df.na.drop().show() //dropped null value rows

  //df.na.fill("").show() //it filled for string data type other shoudl be null

  val df2 = df.na.fill("Missing name", Array("Name"))

  df2.na.fill(200.5, Array("Sales")).show()

  df.describe().show() //shows count ,mean, stddev, min and max

  sparkSession.stop()


}
