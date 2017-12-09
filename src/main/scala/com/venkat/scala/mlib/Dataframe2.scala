package com.venkat.scala.mlib

import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession

/**
  * Created by Venkatram on 12/9/2017.
  */
object Dataframe2 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()

  val path = "sample_linear_regression_data.txt"

  val data = sparkSession.read.option("header", "true").option("inferSchema", "true").format("csv").load("Clean-USA-Housing.csv")

  //show schema
  data.printSchema()

  //data.head(1)

  /*val colnames = data.columns
  val firstrow = datahead(1)(0)
  println("\n")
  println("Example Data Row")

  for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
  }*/

  //label, features

  import org.apache.spark.ml.feature.VectorAssembler
  import org.apache.spark.ml.linalg.Vectors
  import sparkSession.implicits._

  val df = (data.select(data("Price").as("label"),$"Avg Area Income",$"Avg Area House Age",
            $"Avg Area Number of Rooms",$"Avg Area Number of Bedrooms",
            $"Area Population"))

  val assembler = new VectorAssembler().
                    setInputCols(Array("Avg Area Income","Avg Area House Age",
                      "Avg Area Number of Rooms","Avg Area Number of Bedrooms",
                      "Area Population")).setOutputCol("features")

  val output = assembler.transform(df).select($"label", $"features")

  output.show()

}
