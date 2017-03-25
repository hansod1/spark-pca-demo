# spark-pca-demo
Demonstrate a scaling problem with the default implementation of PCA in spark ML and mllib

sample/Andy-Weir-The-Martian.csv was sourced from https://archive.ics.uci.edu/ml/datasets/Amazon+book+reviews, Lichman, M. (2013). UCI Machine Learning Repository [http://archive.ics.uci.edu/ml]. Irvine, CA: University of California, School of Information and Computer Science.

Example run:
~~~~
sbt assembly
./bin/demo/SetupInput.sh #setup input and project jar on hdfs
./bin/demo/SparkPCADemo.sh /sample/Andy-Weir-The-Martian.csv /output/pca-demo-output /output/pca-demo-vocab #run demo job
~~~~