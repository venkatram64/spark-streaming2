package com.venkat.scala.classification

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer, VectorAssembler, VectorIndexer}
import org.apache.spark.ml.linalg.Vectors
//import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.mllib.evaluation.MulticlassMetrics

/**
  * Created by Venkatram on 12/10/2017.
  */
object Dataframe2 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()

  val data = sparkSession.read.option("header", "true").option("inferSchema", "true").format("csv").load("titanic.csv")

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

  val logregDataAll = data.select(data("Survived").as("label"),
              $"Pclass",$"Name",$"Sex", $"Age", $"SibSp", $"Parch", $"Fare", $"Embarked"
            )

  val logregData = logregDataAll.na.drop()

  //Converting Strings into numerical values
  val genderIndexer = new StringIndexer().setInputCol("Sex").setOutputCol("SexIndex")

  val embarkIndexer = new StringIndexer().setInputCol("Embarked").setOutputCol("EmbarkIndex")

  //Converting Numerical vlues into One Hot Encoding 0 or 1
  val genderEncoder = new OneHotEncoder().setInputCol("SexIndex").setOutputCol("SexVec")
  val embarkEncoder = new OneHotEncoder().setInputCol("EmbarkIndex").setOutputCol("EmbarkVec")

  //(label, features)
  val assembler = new VectorAssembler().setInputCols(Array("Pclass","SexVec","Age","SibSp","Parch","Fare","EmbarkVec")).setOutputCol("features")

  val Array(training, test) = logregData.randomSplit(Array(0.7, 0.3), seed=12345)

  val lr = new LogisticRegression()

  val pipeline = new Pipeline().setStages(Array(genderIndexer, embarkIndexer, genderEncoder,embarkEncoder,assembler,lr))

  val model = pipeline.fit(training)

  val results = model.transform(test)

  //Model Evaluation
  val predictionAndLabels = results.select($"prediction", $"label").as[(Double, Double)].rdd

  val metrics = new MulticlassMetrics(predictionAndLabels)

  println("Confusion matrix: ")

  println(metrics.confusionMatrix)


  sparkSession.stop()


}
