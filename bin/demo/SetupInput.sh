#!/bin/bash
source ../run.conf
hadoop fs -copyFromLocal $projectHome/sample/Andy-Weir-The-Martian.csv /sample/Andy-Weir-The-Martian.csv