#!/bin/bash
source ../run.conf

cmd="spark-submit --class com.hansod1.demo.SparkPCADemo \
  --master yarn \
  --deploy-mode cluster \
  --num-executors 40 \
  --driver-memory 20g \
  --driver-java-options \"-XX:-UseCompressedOops\" \
  --conf spark.input.dir=$1 \
  --conf spark.output.dir=$2 \
  --conf spark.vocab.output.dir=$3 \
  --conf spark.serializer=org.apache.spark.serializer.KryoSerializer \
  --conf spark.kryoserializer.buffer.max=512m \
  --conf spark.driver.maxResultSize=8g \
  --conf spark.executor.memory=12g \
  --conf spark.executor.cores=6 \
  $projectJar"

echo $cmd 
eval $cmd
