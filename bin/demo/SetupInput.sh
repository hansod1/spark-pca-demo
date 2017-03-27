#!/bin/bash
source ../run.conf
hadoop fs -mkdir /sample
hadoop fs -mkdir /jars
hadoop fs -copyFromLocal -f $projectHome/sample/Andy-Weir-The-Martian.csv /sample/Andy-Weir-The-Martian.csv
hadoop fs -copyFromLocal -f $projectJar $hdfsJar