package com.venkat.scala.pca

import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.PCA

/**
  * Created by Venkatram on 12/30/2017.
  */
//principal component analysis or general factor analysis

object Dataframe extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("Dataframe")
    .getOrCreate()

  val data = Array(
    Vectors.sparse(5, Seq((1, 1.0), (3, 7.0))),
    Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
    Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0)
  )
  val df = sparkSession.createDataFrame(data.map(Tuple1.apply)).toDF("features")

  val pca = new PCA()
    .setInputCol("features")
    .setOutputCol("pcaFeatures")
    .setK(3) //how many principal components
    .fit(df)

  val result = pca.transform(df).select("pcaFeatures")
  result.show(false)


}
