package com.venkat.scala.mlib

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.regression.LinearRegression

/**
  * Created by Venkatram on 12/10/2017.
  */
object Dataframe3 extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()


  val data = sparkSession.read.option("header", "true").option("inferSchema", "true").format("csv").load("Ecommerce Customers")

  //show schema
  data.printSchema()

  //data.head(2)

  val colnames = data.columns
  val firstrow = data.head(1)(0)
  println("\n")
  println("Example Data Row")

  for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
  }

  import org.apache.spark.ml.feature.VectorAssembler
  import org.apache.spark.ml.linalg.Vectors
  import sparkSession.implicits._

  // Rename the Yearly Amount Spent Column as "label"
  // Also grab only the numerical columns from the data
  // Set all of this as a new dataframe called df
  val df = data.select(data("Yearly Amount Spent").as("label"),
        $"Avg Session Length", $"Time on App", $"Time on Website", $"Length of Membership")

  // An assembler converts the input values to a vector
  // A vector is what the ML algorithm reads to train a model

  // Use VectorAssembler to convert the input columns of df
  // to a single output column of an array called "features"
  // Set the input columns from which we are supposed to read the values.
  // Call this new object assembler
  val assembler = new VectorAssembler().setInputCols(Array("Avg Session Length",
            "Time on App","Time on Website", "Length of Membership")).setOutputCol("features")

  // Use the assembler to transform our DataFrame to the two columns: label and features
  val output = assembler.transform(df).select($"label", $"features")

  // Create a Linear Regression Model object
  val lr = new LinearRegression()

  // Fit the model to the data and call this model lrModel
  val lrModel = lr.fit(output)

  // Print the coefficients and intercept for linear regression
  println(s"Coeff: ${lrModel.coefficients}, intercept: ${lrModel.intercept}")

  // Summarize the model over the training set and print out some metrics!
  // Use the .summary method off your model to create an object
  // called trainingSummary
  val trainingSummary = lrModel.summary

  // Show the residuals, the RMSE, the MSE, and the R^2 Values.
  trainingSummary.residuals.show()
  println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
  println(s"MSE: ${trainingSummary.meanSquaredError}")
  println(s"r2: ${trainingSummary.r2}")

  sparkSession.stop()
}
