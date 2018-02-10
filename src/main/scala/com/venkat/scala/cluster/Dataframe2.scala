package com.venkat.scala.cluster

import org.apache.spark.ml.clustering.{KMeans, KMeansModel}
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.SparkSession

/**
  * Created by Venkatram on 12/30/2017.
  */
object Dataframe2 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("Dataframe2")
    .getOrCreate()

  val dataSet = sparkSession.read.option("header", "true").option("inferSchema", "true").format("csv").load("Wholesale customers data.csv")

  //show schema
  dataSet.printSchema()

  import sparkSession.implicits._

  val featueDataSet = dataSet.select($"Fresh",$"Milk",$"Grocery",$"Frozen",$"Detergents_Paper",$"Delicassen")

  val assembler = new VectorAssembler().
    setInputCols(Array("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen"))
    .setOutputCol("features")

  val trainingData = assembler.transform(featueDataSet).select("features")

  val kmeans = new KMeans().setK(8)

  val model = kmeans.fit(trainingData)
  /*
  To predict save the model
   */
  model.write.overwrite.save("MyModelData")



  val wssse = model.computeCost((trainingData))

  println(s"The within set sum of squared errors ${wssse}")

  //show the result
  println("Cluster Centers: ")
  model.clusterCenters.foreach(println)

  val predictionModel = KMeansModel.load("MyModelData")
  //predictionModel.transform()
}
