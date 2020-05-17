package com.kbmendoza.dataproj.retail.prepinvoice;

import com.kbmendoza.dataproj.retail.prepinvoice.config.SparkAppConfig;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import static org.apache.spark.sql.functions.*;
import static org.apache.spark.sql.types.DataTypes.TimestampType;

@SpringBootApplication
public class PrepInvoiceApplication {
    @Autowired
    SparkAppConfig sparkAppConfig;

    public static Logger logger = LoggerFactory.getLogger(PrepInvoiceApplication.class);

    public static void main(String[] args) throws Exception {
        logger.info("Start streaming application");
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(PrepInvoiceApplication.class).run(args);
        ctx.getBean(PrepInvoiceApplication.class).run();
    }

    public void run() throws Exception {
        SparkConf conf = new SparkConf()
                .set("spark.sql.streaming.schemaInference", "true");
        SparkSession sparkSession = SparkSession
                .builder()
                .appName(sparkAppConfig.getAppName())
                .master(sparkAppConfig.getMaster())
                .config(conf)
                .getOrCreate();

        StructType schema = new StructType()
                .add("InvoiceNo", "string")
                .add("StockCode", "string")
                .add("Description", "string")
                .add("Quantity", "integer")
                .add("InvoiceDate", "string")
                .add("UnitPrice", "float")
                .add("CustomerID", "string")
                .add("Country", "string");

        Dataset<Row> country = sparkSession
                                .read()
                                .option("header", "true")
                                .csv("/data/retail/output/dim_country");

        Dataset<Row> sampleInvoiceDF = sparkSession.read()
                                        .option("header", "true")
                                        .schema(schema)
                                        .csv("/data/retail/sample/");

        sampleInvoiceDF = sampleInvoiceDF
                .withColumnRenamed("InvoiceNo", "invoice_no")
                .withColumnRenamed("StockCode", "product_code")
                .withColumnRenamed("Description", "product_name")
                .withColumnRenamed("Quantity", "quantity")
                .withColumnRenamed("UnitPrice", "actual_unit_price")
                .withColumnRenamed("CustomerID", "customer_id")
                .withColumnRenamed("Country", "country_name")
                .withColumn("invoice_timestamp",to_timestamp(unix_timestamp(col("InvoiceDate"),"d M yyyy H:mm").cast(TimestampType)))
                .withColumn("invoice_date", to_date(col("invoice_timestamp")))
                .withColumn("year", year(col("invoice_timestamp")))
                .withColumn("month", month(col("invoice_timestamp")))
                .withColumn("day", dayofmonth(col("invoice_timestamp")))
                .withColumn("hour", hour(col("invoice_timestamp")))
                .withColumn("min", minute(col("invoice_timestamp")))
                .drop(col("InvoiceDate"));


        sampleInvoiceDF.registerTempTable("invoice");
        Dataset<Row> productDF = sparkSession
                .sql("SELECT product_code as code, product_name as name\n" +
                        " FROM invoice\n" +
                        " WHERE trim(product_name) != ''\n" +
                        " GROUP BY product_code, product_name\n" +
                        " ORDER BY 1, 2 ASC");
        productDF.show();
        productDF.write().option("header", "true").mode(SaveMode.Overwrite).csv("/data/retail/output/dim_product/");

        Dataset<Row> dateDF = sparkSession
                .sql("SELECT date_format(invoice_timestamp, \"yyyyMMddHHmmss\") AS date_id, year, month, day, hour, min\n" +
                        " FROM invoice\n" +
                        " GROUP BY date_id, year, month, day, hour, min\n" +
                        " ORDER BY 1");
        dateDF.show();
        dateDF.write().option("header", "true").mode(SaveMode.Overwrite).csv("/data/retail/output/dim_date/");


        Dataset<Row> customerDF = sparkSession
                .sql("SELECT customer_id AS id, ROW_NUMBER OVER(PARTITION by customerid, countr");

        Dataset<Row> addressDF =
    }

}
