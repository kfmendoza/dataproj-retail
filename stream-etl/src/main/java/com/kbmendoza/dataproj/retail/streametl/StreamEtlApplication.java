package com.kbmendoza.dataproj.retail.streametl;

import com.kbmendoza.dataproj.retail.streametl.spark.InvoiceStreamEtlService;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.xml.crypto.Data;

@SpringBootApplication
public class StreamEtlApplication {
    @Autowired
    SparkAppSession spark;

    @Autowired
    InvoiceStreamEtlService streamEtlService;

    public static Logger logger = LoggerFactory.getLogger(StreamEtlApplication.class);

    public static void main(String[] args) throws Exception {
        logger.info("Start streaming application");
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(StreamEtlApplication.class).run(args);
        ctx.getBean(StreamEtlApplication.class).run();
    }

    public void run() throws StreamingQueryException {
        StructType schema = new StructType()
                .add("InvoiceNo", "string")
                .add("StockCode", "string")
                .add("Description", "string")
                .add("Quantity", "integer")
                .add("InvoiceDate", "string")
                .add("UnitPrice", "float")
                .add("CustomerID", "string")
                .add("Country", "string");

        Dataset<Row> invoiceDF = spark.getSparkSession()
                //.read()
                .readStream()
                .option("maxFilesPerTrigger", 5)
                .option("sep", ",")
                .option("header", true)
                .schema(schema)
                .csv("s3a://dataproj/data/retail/input/invoice-dump/*.csv");

        streamEtlService.transformInvoice(invoiceDF);

        //Dataset<Row> ratingDF = spark
    }


}
