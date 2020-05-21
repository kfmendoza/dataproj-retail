val spark = SparkSession.builder().appName("Spark Hive Example").config("spark.sql.warehouse.dir", warehouseLocation).enableHiveSupport().getOrCreate()

val analysis2 = spark.sql("SELECT product_code as product_code, SUM(units) AS total_units FROM fact_invoice_line GROUP BY product_code ORDER BY 2 DESC LIMIT 10")

analysis2.coalesce(1).write.option("header", "true").mode("overwrite").csv("/data/retail/analysis/analysis2")

