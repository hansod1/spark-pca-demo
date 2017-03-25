#!/bin/bash
source ../run.conf

cmd="spark-submit --class com.hansod1.SparkPCADemo \
  --master yarn-cluster \
  --num-executors 40 \
  --driver-memory 20g \
  --driver-java-options \"-XX:-UseCompressedOops\" \
  --conf spark.input.dir=$1 \
  --conf spark.output.dir=$2 \
  --conf spark.vocab.output.dir=$3 \
  --conf spark.serializer=org.apache.spark.serializer.KryoSerializer \
  --conf spark.executor.memory=10g \
  --conf spark.executor.cores=6 \
  $projectJar"

echo $cmd 
eval $cmd
