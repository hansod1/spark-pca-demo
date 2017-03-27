package com.hansod1.demo

import org.apache.spark.ml.feature._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.functions.udf


object SparkPCADemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("SparkPCADemo").getOrCreate()
    import spark.implicits._

    val fields = Array(
      StructField("score",DataTypes.StringType),
      StructField("uri", DataTypes.StringType),
      StructField("title", DataTypes.StringType),
      StructField("source_html",DataTypes.StringType)
    )

    val schema = StructType(fields)

    val df = spark.read.schema(schema)
      .option("delimiter","\t")
      .csv(spark.conf.get("spark.input.dir"))
      .repartition(160)

    val tokenizer = new Tokenizer()
      .setInputCol("source_html")
      .setOutputCol("source_words")

    val tokenizedDF = tokenizer.transform(df)

    val bigram = new NGram().setInputCol("source_words").setOutputCol("bigrams").setN(2)

    val bigramDF = bigram.transform(tokenizedDF)

    val trigram = new NGram().setInputCol("source_words").setOutputCol("trigrams").setN(3)

    val concat_array = udf((c1 : Seq[String], c2 : Seq[String], c3 : Seq[String]) => {
      c1 ++ c2 ++ c3
    })

    val trigramDF = trigram.transform(bigramDF).withColumn("source_words_ngrams",concat_array($"source_words",$"bigrams",$"trigrams"))

    val cvModel = new CountVectorizer()
      .setInputCol("source_words_ngrams")
      .setOutputCol("count_features")
      .setVocabSize(10000)
      .setMinDF(2)
      .fit(trigramDF)

    spark.sparkContext.parallelize[String](cvModel.vocabulary).toDF("vocab").write.save(spark.conf.get("spark.vocab.output.dir"))

    val countVectorizedDF = cvModel.transform(trigramDF)

    val idf = new IDF()
      .setInputCol("count_features")
      .setOutputCol("idf_features")
      .fit(countVectorizedDF)

    val idfVectorizedDF = idf.transform(countVectorizedDF)

    val pca = new PCA()
      .setInputCol("idf_features")
      .setOutputCol("pca_features")
      .setK(1000)
      .fit(idfVectorizedDF)

    val pcaDF = pca.transform(idfVectorizedDF)

    pcaDF.write.save(spark.conf.get("spark.output.dir"))

  }

}
