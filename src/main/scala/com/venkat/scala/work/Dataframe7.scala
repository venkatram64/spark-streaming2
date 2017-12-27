package com.venkat.scala.work

import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer, VectorAssembler}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Created by Venkatram on 12/27/2017.
  */
object Dataframe7 extends App{


  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("Dataframe6")
    .getOrCreate()

  val testData = sparkSession.read.option("header", "true").option("inferSchema", "true").format("csv").load("weather-test.csv")
  //show schema
  testData.printSchema()

  val colnames = testData.columns
  val firstrow = testData.head(1)(0)
  println("\n")
  println("Example Data Row")

  for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
  }

  /*def dateToLong(d:String): Long = {
    val format = new java.text.SimpleDateFormat("M/dd/yyyy")
    format.parse(d).getTime()
  }*/

  import sparkSession.implicits._

  val model = PipelineModel.load("weatherModel")


  val predictions = model.transform(testData)

  predictions.printSchema()

  val predictionAndLabels = predictions.select($"prediction", $"label",$"probability")
  predictionAndLabels.show(100)



  sparkSession.stop()

}
