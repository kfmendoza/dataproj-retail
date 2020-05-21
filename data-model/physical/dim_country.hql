DROP TABLE IF EXISTS retaildb.dim_country;

CREATE EXTERNAL TABLE retaildb.dim_country (
 name STRING,
 code STRING
)
COMMENT 'Country dimension'
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'hdfs://localhost:19000/data/retail/output/dim_country/'
TBLPROPERTIES ("skip.header.line.count"="1");
;

MSCK REPAIR TABLE retaildb.dim_country;
SELECT * FROM retaildb.dim_country LIMIT 1;