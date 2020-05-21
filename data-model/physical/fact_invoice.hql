DROP TABLE IF EXISTS retaildb.fact_invoice_line;

CREATE EXTERNAL TABLE retaildb.fact_invoice_line (
 invoice_no STRING, 
 status int,
 customer_id STRING, 
 country_code STRING, 
 product_code STRING, 
 actual_unit_price double, 
 units int, 
 date_id STRING
)
COMMENT 'Fact invoice'
PARTITIONED BY (invoice_date STRING)
STORED AS PARQUET
LOCATION 'hdfs://localhost:19000/data/retail/output/fact_invoice_line';

MSCK REPAIR TABLE retaildb.fact_invoice_line;
SELECT * FROM retaildb.fact_invoice_line LIMIT 1;