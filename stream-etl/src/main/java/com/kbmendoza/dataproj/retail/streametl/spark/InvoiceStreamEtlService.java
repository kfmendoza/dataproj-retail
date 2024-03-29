package com.kbmendoza.dataproj.retail.streametl.spark;

import com.kbmendoza.dataproj.retail.streametl.SparkAppSession;
import com.kbmendoza.dataproj.retail.streametl.entity.FactInvoiceLine;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.streaming.Trigger;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;

import static org.apache.spark.sql.functions.*;
import static org.apache.spark.sql.types.DataTypes.StringType;
import static org.apache.spark.sql.types.DataTypes.TimestampType;

@Service
public class InvoiceStreamEtlService {

    @Autowired
    SparkAppSession spark;

    @Value("${invoice.trigger}")
    private String trigger;

    private Dataset<Row> country;

    private static Logger logger = LoggerFactory.getLogger(InvoiceStreamEtlService.class);

    @PostConstruct
    public void init() {
        StructType schema = new StructType()
                .add("name", "string")
                .add("code", "string");
        country = spark.getSparkSession()
                .read()
                .schema(schema)
                .option("header", "true")
                .csv("hdfs://localhost:19000/data/retail/output/dim_country");


        /*spark.getSparkSession().streams().addListener(new StreamingQueryListener() {
            @Override
            public void onQueryStarted(QueryStartedEvent queryStarted) {
                logger.info("Stream started: " + queryStarted.id());
            }
            @Override
            public void onQueryTerminated(QueryTerminatedEvent queryTerminated) {
                logger.info("Stream terminated: " + queryTerminated.id());
            }

        });*/
    }

   public void transformInvoice(Dataset<Row> invoiceDF) throws StreamingQueryException {
       Dataset<Row> invoiceDFClean = invoiceDF
               .join(country, invoiceDF.col("Country").equalTo(country.col("name")), "left")
               .withColumn("country_code", country.col("code"))
               .drop(country.columns()).drop("Country")
               .withColumn("invoice_timestamp",to_timestamp(unix_timestamp(col("InvoiceDate"),"d M yyyy H:mm").cast(TimestampType)))
               .select(
                       col("InvoiceNo").alias("invoice_no"),
                       //regexp_replace(col("CustomerID"), "[A-Za-z]", "").alias("customer_id"),
                       //regexp_replace(col("StockCode"), "[A-Za-z]", "").alias("product_code"),
                       col("CustomerID").alias("customer_id"),
                       col("StockCode").alias("product_code"),
                       col("UnitPrice").alias("actual_unit_price"),
                       col("Quantity").alias("units"),
                       col("country_code"),
                       to_date(col("invoice_timestamp")).as("invoice_date").cast(StringType),
                       date_format(col("invoice_timestamp"), "yyyyMMddHHmmss").alias("date_id")
                );

       /*invoiceDFClean = invoiceDFClean
                        .where(not(col("InvoiceNo").startsWith("C")
                                .or(col("Quantity").$less$eq(0))));*/
       invoiceDFClean = invoiceDFClean
               .withColumn("status", when(col("invoice_no").startsWith("C"), "0").otherwise(1));
       //WindowSpec windowSpec = Window.partitionBy("invoice_no").orderBy(asc("invoice_timestamp"));
       Dataset<FactInvoiceLine> factInvoiceLine = invoiceDFClean
                .selectExpr("invoice_date", "invoice_no", "status", "customer_id", "country_code", "product_code", "actual_unit_price", "units", "date_id")
                .as(Encoders.bean(FactInvoiceLine.class));
                //.withColumn("invoice_line_no", row_number().over(windowSpec));

       factInvoiceLine.printSchema();

       StreamingQuery invoiceQuery  = factInvoiceLine
               .writeStream()
               .format("parquet")
               .option("checkpointLocation", "hdfs://localhost:19000/spark-checkpoints/retail")
               .option("path", "hdfs://localhost:19000/data/retail/output/fact_invoice_line/")
               .partitionBy("invoice_date")
               .outputMode(OutputMode.Append())
               .trigger(Trigger.ProcessingTime(trigger))
               .start();

       invoiceQuery.awaitTermination();
   }
}
