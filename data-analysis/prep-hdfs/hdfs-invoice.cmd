hadoop fs -mkdir -p /data/retail/input/invoice-dump/
hadoop fs -put OnlineRetail.csv /data/retail/input/invoice-dump/

hadoop fs -mkdir -p /data/retail/output/
hadoop fs -put country.csv /data/retail/output/dim_country/

hadoop fs -mkdir -p /data/retail/sample
hadoop fs -put OnlineRetail.csv /data/retail/sample/

hadoop fs -mkdir -p /spark-checkpoints/retail