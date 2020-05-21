val spark = SparkSession.builder().appName("Spark Hive Example").config("spark.sql.warehouse.dir", warehouseLocation).enableHiveSupport().getOrCreate()

val analysis3 = spark.sql("SELECT dim_date.cal_date, SUM(actual_unit_price*units) AS total_revenue FROM fact_invoice_line, dim_date WHERE fact_invoice_line.date_id = dim_date.id GROUP BY dim_date.cal_date")

analysis3.coalesce(1).write.option("header", "true").mode("overwrite").csv("/data/retail/analysis/analysis3")

SELECT dim_date.cal_date, SUM(actual_unit_price*units) AS total_revenue FROM fact_invoice_line, dim_date WHERE fact_invoice_line.date_id = dim_date.id GROUP BY dim_date.cal_date