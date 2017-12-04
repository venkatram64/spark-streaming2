package com.venkat.scala.work

import org.apache.spark.sql.SparkSession

/**
  * Created by Venkatram on 12/3/2017.
  */
object DataFrame extends App{

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("DataFrame")
    .getOrCreate()


  //val df = sparkSession.read.csv("CitiGroup2006_2008")
  val df = sparkSession.read.option("header", "true").option("inferSchema", "true").csv("CitiGroup2006_2008")
  //df.head(5);

  df.columns
  df.describe().show()
  df.select("Volume").show()

  //df.select($"Date",$"Close", $"Volume").show()
  val df2 = df.withColumn("HighPlusLow",df("High") + df("Low"));  //create new column
  df2.printSchema()

  df2.select(df2("HighPlusLow").as("HPL"),df2("Close")).show() //renaming the column

  for(row <- df.head(5)){
    println(row);
  }

}
