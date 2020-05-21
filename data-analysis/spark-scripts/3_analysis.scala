val spark = SparkSession.builder().appName("Spark Hive Example").config("spark.sql.warehouse.dir", warehouseLocation).enableHiveSupport().getOrCreate()

val analysis2 = spark.sql("SELECT product_code as product_code, SUM(units) AS total_units FROM fact_invoice_line GROUP BY product_code ORDER BY 2 DESC LIMIT 10")

analysis2.coalesce(1).write.option("header", "true").mode("overwrite").csv("/data/retail/analysis/analysis2")

SELECT d_date.cal_date AS cal_date, SUM(actual_unit_price*units) AS total_revenue
FROM fact_invoice f_invoice, dim_date d_date
WHERE f_invoice.date_id = d_date.id
GROUP BY d_date.cal_date