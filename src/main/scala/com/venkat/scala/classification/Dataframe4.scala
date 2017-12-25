package com.venkat.scala.classification

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{LogisticRegression, RandomForestClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

//ftp://ftp.ncdc.noaa.gov/pub/data/ghcn/daily/by_year/     rainfall data

/**
  * Created by Venkatram on 12/24/2017.
  */
object Dataframe4 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()

  val data = sparkSession.read.option("header", "true").option("inferSchema", "true").format("csv").load("advertising.csv")

  //show schema
  data.printSchema()

  val colnames = data.columns
  val firstrow = data.head(1)(0)
  println("\n")
  println("Example Data Row")

  for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
  }

  import sparkSession.implicits._

  //   Do the Following:
  //    - Rename the Clicked on Ad column to "label"
  //    - Grab the following columns "Daily Time Spent on Site","Age","Area Income","Daily Internet Usage","Timestamp","Male"
  //    - Create a new column called Hour from the Timestamp containing the Hour of the click


  val timedata = data.withColumn("Hour", hour(data("Timestamp")))

  val logregdata =  timedata.select(data("Clicked on Ad").as("label"),
    $"Daily Time Spent on Site", $"Age", $"Area Income",
    $"Daily Internet Usage",$"Hour",$"Male")

  val assembler = new VectorAssembler().setInputCols(
    Array( "Daily Time Spent on Site", "Age", "Area Income",
      "Daily Internet Usage","Hour","Male")).setOutputCol("features")

  val Array(training, test) = logregdata.randomSplit(Array(0.7, 0.3), seed=12345)
  println ("training data count " + training.count)
  println ("test data count " + test.count)

  //val lr = new LogisticRegression()

  val classifier = new RandomForestClassifier()
    .setImpurity("gini")
    .setMaxDepth(3)
    .setNumTrees(20)
    .setFeatureSubsetStrategy("auto")
   // .setSeed(5043)


  val pipeline = new Pipeline().setStages(Array(assembler, classifier))

  val model = pipeline.fit(training)

  val results = model.transform(test)

  results.printSchema()
  // Convert the test results to an RDD using .as and .rdd
  val predictionAndLabels = results.select($"prediction", $"label")
  predictionAndLabels.show()

  predictionAndLabels.createOrReplaceTempView("predictionView")




  val evaluator = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("accuracy")

  println("Evaluator ")

  println(evaluator.evaluate(predictionAndLabels) )

  sparkSession.stop()

}
