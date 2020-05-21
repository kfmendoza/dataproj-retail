DROP TABLE IF EXISTS retaildb.dim_product;

CREATE EXTERNAL TABLE retaildb.dim_product (
 code STRING,
 name STRING
)
COMMENT 'Product dimension'
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION 'hdfs://localhost:19000/data/retail/output/dim_product/'
TBLPROPERTIES ("skip.header.line.count"="1");

MSCK REPAIR TABLE retaildb.dim_product;
SELECT * FROM retaildb.dim_product LIMIT 1;