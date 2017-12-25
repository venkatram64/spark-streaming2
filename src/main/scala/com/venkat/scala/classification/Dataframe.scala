package com.venkat.scala.classification

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession

/**
  * Created by Venkatram on 12/10/2017.
  */
object Dataframe extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()

  val training = sparkSession.read.format("libsvm").load("sample_libsvm_data.txt")

  //show schema
  training.printSchema()


  val lr = new LogisticRegression().setMaxIter(10)
              .setRegParam(0.3)
                .setElasticNetParam(0.8)

  val lrModel = lr.fit(training)

  lrModel.summary.predictions.show()

  //println(s"Summary: ${lrModel.summary.predictions.show()}")

  println(s"Coefficients: ${lrModel.coefficientMatrix} Intercept: ${lrModel.intercept}")

  sparkSession.stop()

}
