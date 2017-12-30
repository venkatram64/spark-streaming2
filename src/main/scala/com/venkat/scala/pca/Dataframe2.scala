package com.venkat.scala.pca

import org.apache.spark.ml.feature.{StandardScaler, VectorAssembler}
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.PCA

/**
  * Created by Venkatram on 12/30/2017.
  */
//File uploaded to /FileStore/tables/movie_ratings.csv
object Dataframe2 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("Dataframe2")
    .getOrCreate()

  val dataSet = sparkSession.read.option("header", "true").option("inferSchema", "true").format("csv").load("Cancer_Data.txt")

  dataSet.printSchema()

  val colnames = Array("mean radius", "mean texture", "mean perimeter", "mean area", "mean smoothness",
    "mean compactness", "mean concavity", "mean concave points", "mean symmetry", "mean fractal dimension",
    "radius error", "texture error", "perimeter error", "area error", "smoothness error", "compactness error",
    "concavity error", "concave points error", "symmetry error", "fractal dimension error", "worst radius",
    "worst texture", "worst perimeter", "worst area", "worst smoothness", "worst compactness", "worst concavity",
    "worst concave points", "worst symmetry", "worst fractal dimension")

  val assembler = new VectorAssembler().setInputCols(colnames).setOutputCol("features")

  import sparkSession.implicits._
  // Use the assembler to transform our DataFrame to a single column: features
  val output = assembler.transform(dataSet).select($"features")

  val scaler = new StandardScaler()
    .setInputCol("features")
    .setOutputCol("scaledFeatures")
    .setWithStd(true)
    .setWithMean(false)

  val scalerModel = scaler.fit(output)

  val scaledData = scalerModel.transform(output)

  val pca = new PCA()
    .setInputCol("scaledFeatures")
    .setOutputCol("pcaFeatures")
    .setK(4)
    .fit(scaledData)

  val pcaDF = pca.transform(scaledData)

  val result = pcaDF.select("pcaFeatures")
  result.show()

  result.head(1)
}
