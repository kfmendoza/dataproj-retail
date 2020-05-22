# PREREQUISITES:

## INSTALLING JAVA
1. Download Java JDK 8 from Java’s official website
1. Set the following environment variables:
1. JAVA_HOME = D:\Java\jdk1.8.0_201
PATH += %JAVA_HOME%\bin
1. Test by using cmd: <br>
 `java`

## INSTALLING HADOOP
- see https://cwiki.apache.org/confluence/display/HADOOP2/Hadoop2OnWindows
- see https://medium.com/@shwetabh.dixit/hadoop-installation-on-windows-bf699ce088ab
- see https://exitcondition.com/install-hadoop-windows/
1. Download hadoop. Choose release to use (2.7.3 in my case) then Follow tutorial
1. Add hadoop.dll in Windows System32
1. Start namenode and datanode/s
1. Set the following environment variables:
HADOOP_HOME='D:\hadoop-2.7.3'
PATH += %HADOOP_HOME%\bin
1. Test by using cmd: <br>
 `hadoop`

## INSTALLING SPARK
1. Download Spark from Spark’s official website. Choose the release with Pre-built for Hadoop (2.7 or later in my case). Download the .tgz file
1. Extract the .tgz file into folder
1. Set the environment variables:
SPARK_HOME = D:\spark-2.4.0-bin-hadoop2.7
PATH += %SPARK_HOME%\bin
1. Test by using cmd:
 `spark-shell`

## INSTALLING DERBY DB (HIVE METASTORE)
1. Download derby that matches hive release to be downloaded
1. Extract the .tgz file into folder
1. Start the metastore

## INSTALLING HIVE 
1. Download from https://archive.apache.org/dist/hive/hive-2.3.7/. Choose the release. Download the .tgz file
1. Extract the .tgz file into folder
1. Set the environment variables:
HIVE_HOME='D:\apache-hive-3.0.0-bin'
PATH += %HIVE_HOME%\bin
HADOOP_CLASSPATH += $HIVE_HOME\lib\*.jar
1. Test by using cmd:
 `hive`

## INSTALLING TABLEAU PUBLIC
1. Download https://public.tableau.com/en-us/s/download
1. Install the software
1. Subscribe to a Tableau public account to save dashboards

## INSTALLING KAFKA 
1. Download from https://archive.apache.org/dist/kafka/2.4.0/kafka_2.12-2.4.0.tgz. Download the .tgz file. Extract the .tgz file into folder
1. Run the zookeeper
1. Run the kafka server

## INSTALLING NODEJS
1. Download LTS version from https://nodejs.org/en/download/. Download the .tgz file. Extract the .tgz file into folder
