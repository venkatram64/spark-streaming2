package com.venkat.scala.work

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer, VectorAssembler}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
  * Created by Venkatram on 12/25/2017.
  */
object Dataframe6 extends App{
  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("RainfallProcess")
    .getOrCreate()

  val data = sparkSession.read.option("header", "true").option("inferSchema", "true").format("csv").load("weather.csv")
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



  /*  val logregDataAll = data.select($"RainTomorrow",
      $"MinTemp",$"MaxTemp", $"Rainfall", $"Evaporation", $"Sunshine", $"WindGustDir", $"WindGustSpeed",
      $"WindDir9am",$"WindDir3pm",$"WindSpeed9am", $"WindSpeed3pm", $"Humidity9am", $"Humidity3pm", $"Pressure9am", $"Pressure3pm",
      $"Cloud9am",$"Cloud3pm",$"Temp9am", $"Temp3pm", $"RainToday", $"RISK_MM"
    )*/

  val rainfallData = data.withColumn("Date", unix_timestamp($"Date"))

  val logregDataAll = rainfallData.select($"RainTomorrow",
    $"Date",$"MinTemp",$"MaxTemp", $"Rainfall", $"Evaporation", $"Sunshine",$"WindGustSpeed",
    $"WindSpeed9am", $"WindSpeed3pm", $"Humidity9am", $"Humidity3pm", $"Pressure9am", $"Pressure3pm",
    $"Cloud9am",$"Cloud3pm",$"Temp9am", $"Temp3pm",  $"RISK_MM"
  )



  val logregData = logregDataAll.na.drop()


  val rainTomorrowIndexer = new StringIndexer().setInputCol("RainTomorrow").setOutputCol("label")
  val rainTomorrowEncoder = new OneHotEncoder().setInputCol("label").setOutputCol("RainTomorrowVec")

  /* val windGustDirIndexer = new StringIndexer().setInputCol("WindGustDir").setOutputCol("WindGustDirIndex")
   val windGustDirEncoder = new OneHotEncoder().setInputCol("WindGustDirIndex").setOutputCol("WindGustDirVec")*/


  val rainTodayIndexer = new StringIndexer().setInputCol("RainToday").setOutputCol("RainTodayIndex")
  val rainTodayEncoder = new OneHotEncoder().setInputCol("RainTodayIndex").setOutputCol("RainTodayVec")

  /* val windDir9amIndexer = new StringIndexer().setInputCol("WindDir9am").setOutputCol("WindDir9amIndex")
   val windDir9amEncoder = new OneHotEncoder().setInputCol("WindDir9amIndex").setOutputCol("WindDir9amVec")

   val windDir3pmIndexer = new StringIndexer().setInputCol("WindDir3pm").setOutputCol("WindDir3pmIndex")
   val windDir3pmEncoder = new OneHotEncoder().setInputCol("WindDir3pmIndex").setOutputCol("WindDir3pmVec")*/


  val assembler = new VectorAssembler().
    setInputCols(Array("Date","MinTemp","MaxTemp","Rainfall","Evaporation","Sunshine",
      "WindGustSpeed","WindSpeed9am","WindSpeed3pm","Humidity9am","Humidity3pm","Pressure9am",
      "Pressure3pm","Cloud9am","Cloud3pm","Temp9am","Temp3pm","RISK_MM"))
    .setOutputCol("features")



  val Array(training, test) = logregData.randomSplit(Array(0.7, 0.3), seed=12345)

  println ("training data count " + training.count)
  println ("test data count " + test.count)

  val rf = new RandomForestClassifier()
    .setLabelCol("label")
    .setFeaturesCol("features")
    .setImpurity("gini")
    .setMaxDepth(3)
    .setNumTrees(10)

  /*  val pipeline = new Pipeline().setStages(Array(windGustDirIndexer,windDir9amIndexer,windDir3pmIndexer,rainTodayIndexer,
      rainTomorrowIndexer,windGustDirEncoder,windDir9amEncoder,windDir3pmEncoder,rainTodayEncoder,rainTomorrowEncoder,assembler,rf))*/

  val pipeline = new Pipeline().setStages(Array(rainTomorrowIndexer, rainTomorrowEncoder,assembler,rf))


  val model = pipeline.fit(training)

  val predictions = model.transform(test)

  predictions.printSchema()

  val predictionAndLabels = predictions.select($"prediction", $"label",$"probability")
  predictionAndLabels.show(100)

  val evaluator = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("accuracy")

  evaluator.explainParams()

  println("Evaluator ")

  println(evaluator.evaluate(predictionAndLabels) )

  val total = predictionAndLabels.count()
  val correct = predictionAndLabels.filter($"label" === $"prediction").count()
  val wrong = predictionAndLabels.filter(not($"label" === $"prediction")).count()
  val ratioWrong = wrong.toDouble / total.toDouble
  val tatioCorrect = correct.toDouble / total.toDouble


  val truep = predictionAndLabels.filter($"prediction" === 0.0).filter($"label" === $"prediction").count() / total.toDouble
  val truen = predictionAndLabels.filter($"prediction" === 1.0).filter($"label" === $"prediction").count() / total.toDouble
  val falsep = predictionAndLabels.filter($"prediction" === 1.0).filter(not($"label" === $"prediction")).count() / total.toDouble
  val falsen = predictionAndLabels.filter($"prediction" === 0.0).filter(not($"label" === $"prediction")).count() / total.toDouble

  println("counttotal : " + total)
  println("correct : " + correct)
  println("wrong: " + wrong)
  println("ratio wrong: " + ratioWrong)
  println("ratio correct: " + tatioCorrect)
  println("ratio true positive : " + truep)
  println("ratio false positive : " + falsep)
  println("ratio true negative : " + truen)
  println("ratio false negative : " + falsen)

  sparkSession.stop()
}
