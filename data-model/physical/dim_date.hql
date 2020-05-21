DROP TABLE IF EXISTS retaildb.dim_date;

CREATE EXTERNAL TABLE retaildb.dim_date (
 id STRING,
 cal_date date,
 year STRING,
 month STRING,
 day STRING,
 HOUR STRING
)
COMMENT 'Date dimension'
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'hdfs://localhost:19000/data/retail/output/dim_date/'
TBLPROPERTIES ("skip.header.line.count"="1");

MSCK REPAIR TABLE retaildb.dim_date;
SELECT * FROM retaildb.dim_date LIMIT 1;