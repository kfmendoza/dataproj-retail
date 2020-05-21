import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DataTypes.TimestampType
import java.io.File


//val warehouseLocation = new File("spark-warehouse").getAbsolutePath

//val spark = SparkSession.builder().appName("PrepInvoiceApplication").config("spark.sql.streaming.schemaInference", "true") .config("spark.sql.warehouse.dir", warehouseLocation).enableHiveSupport().getOrCreate()
val spark = SparkSession.builder().appName("PrepInvoiceApplication").config("spark.sql.streaming.schemaInference", "true").getOrCreate()
//sc.hadoopConfiguration().set("spark.hadoop.f3.s3a.impl","org.apache.hadoop.fs.s3a.S3AFileSystem")*/

val countryDF = sparkSession.read().option("header", "true").csv("hdfs://localhost:19000/data/retail/output/dim_country")
countryDF.show


val schema = new StructType().add("InvoiceNo", "string").add("StockCode", "string").add("Description", "string").add("Quantity", "integer").add("InvoiceDate", "string").add("UnitPrice", "float").add("CustomerID", "string").add("Country", "string")

var sampleInvoiceDF = spark.read.option("header", "true").schema(schema).csv("hdfs://localhost:19000/data/retail/sample/") 
sampleInvoiceDF = sampleInvoiceDF .withColumnRenamed("InvoiceNo", "invoice_no") .withColumnRenamed("StockCode", "product_code") .withColumnRenamed("Description", "product_name") .withColumnRenamed("Quantity", "quantity") .withColumnRenamed("UnitPrice", "actual_unit_price") .withColumnRenamed("CustomerID", "customer_id") .withColumnRenamed("Country", "country_name") .withColumn("invoice_timestamp",to_timestamp(unix_timestamp(col("InvoiceDate"),"d M yyyy H:mm").cast(TimestampType))) .withColumn("invoice_date", to_date(col("invoice_timestamp"))) .withColumn("cal_date", to_date(col("invoice_timestamp"))) .withColumn("year", year(col("invoice_timestamp"))) .withColumn("month", month(col("invoice_timestamp"))) .withColumn("day", dayofmonth(col("invoice_timestamp"))) .withColumn("hour", hour(col("invoice_timestamp"))) .withColumn("min", minute(col("invoice_timestamp"))) .drop(col("InvoiceDate")) 
/*.join(country, sampleInvoiceDF.col("Country").equalTo(country.col("name")), "left") .withColumn("country_code", country.col("code")) .drop(country.columns()).drop("Country")*/  

sampleInvoiceDF.registerTempTable("invoice")

val productDF = spark .sql("SELECT product_code as code, product_name as name\n" + " FROM (\n" + "SELECT product_code, product_name,\n" + "row_number() over(partition by product_code\n" + "order by invoice_timestamp desc) row_num\n" + "FROM invoice" + " WHERE trim(product_name) != '') a\n" + " WHERE a.row_num = 1\n" + " ORDER BY 1, 2 ASC")         
productDF.show()
productDF.write.option("header", "true").mode(SaveMode.Overwrite).csv("hdfs://localhost:19000/data/retail/output/dim_product/")

val dateDF = spark .sql("SELECT date_format(invoice_timestamp, \"yyyyMMddHHmmss\") AS id, cal_date, year, month, day, hour, min\n" + " FROM invoice\n" + " GROUP BY id, cal_date, year, month, day, hour, min\n" + " ORDER BY 1")         
dateDF.show()
dateDF.write.option("header", "true").mode(SaveMode.Overwrite).csv("hdfs://localhost:19000/data/retail/output/dim_date/")


val customerDF = spark .sql("SELECT DISTINCT(customer_id) AS id\n" + " FROM invoice")         
customerDF.show()
customerDF.write.option("header", "true").mode(SaveMode.Overwrite).csv("hdfs://localhost:19000/data/retail/output/dim_customer")

