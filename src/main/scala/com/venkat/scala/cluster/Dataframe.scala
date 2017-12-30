package com.venkat.scala.cluster

import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.sql.SparkSession

/**
  * Created by Venkatram on 12/30/2017.
  */
object Dataframe extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("Dataframe")
    .getOrCreate()

  val dataSet = sparkSession.read.format("libsvm").load("sample_kmeans_data.txt")
  //train a k-means model
  val kmeans = new KMeans().setK(2).setSeed(1L);
  val model = kmeans.fit(dataSet)

  //evaluate clusterning by computing within set sum of squared errors
  val ssse = model.computeCost(dataSet)
  println(s" ssse set sum of squared errors = $ssse")

  //show the result
  println("Cluster Centers: ")
  model.clusterCenters.foreach(println)



}
