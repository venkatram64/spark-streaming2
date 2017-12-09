package com.venkat.scala.mlib

import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession

/**
  * Created by Venkatram on 12/9/2017.
  */
object Dataframe extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()

  val path = "sample_linear_regression_data.txt"

  //Training data
  val training = sparkSession.read.format("libsvm").load(path)
  training.printSchema()

  //Create new LinearRegression Object

  val lr = new LinearRegression().setMaxIter(100).setRegParam(0.3).setElasticNetParam(0.8)
  //Fit the model
  val lrModel = lr.fit(training)

  //print the coefficients and intercept for linear regression

  println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")

  //summarize the model over the training set and print out some metrics
  val trainingSummary = lrModel.summary
  println(s"numIterations: ${trainingSummary.totalIterations}")
  println(s"ObjectiveHistory: ${trainingSummary.objectiveHistory.toList}")
  trainingSummary.residuals.show()
  println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")

  println(s"r2: ${trainingSummary.r2}")

  sparkSession.stop()
}
