package com.venkat.scala.mlib

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.regression.LinearRegression

/**
  * Created by Venkatram on 12/10/2017.
  */
object Dataframe3 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()


  val data = sparkSession.read.option("header", "true").option("inferSchema", "true").format("csv").load("Ecommerce Customers")

  //show schema
  data.printSchema()

  //data.head(2)

  val colnames = data.columns
  val firstrow = data.head(1)(0)
  println("\n")
  println("Example Data Row")

  for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
  }

}
