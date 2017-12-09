package com.venkat.scala.work

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Created by Venkatram on 12/4/2017.
  */
object DataFrame5 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()
  val df = sparkSession.read.option("header", "true").option("inferSchema", "true").csv("CitiGroup2006_2008")

  //show schema
  df.printSchema()

  import sparkSession.implicits._

  df.select(month(df("Date"))).show()

  df.select(year(df("Date"))).show()

  val df2 = df.withColumn("Year", year(df("Date")))

  val dfavgs = df2.groupBy("Year").mean() //mean will give the avg so use avg(Close) in below

  dfavgs.select($"Year",$"avg(Close)").show()

  // calculate
  val dfmins = df2.groupBy("Year").min()

  dfmins.select($"Year",$"min(Close)").show()


}
