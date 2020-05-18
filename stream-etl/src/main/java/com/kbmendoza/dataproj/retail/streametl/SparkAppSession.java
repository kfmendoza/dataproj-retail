package com.kbmendoza.dataproj.retail.streametl;

import com.kbmendoza.dataproj.retail.streametl.config.SparkAppConfig;
import lombok.Getter;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Getter
public class SparkAppSession {
    @Autowired
    SparkAppConfig sparkAppConfig;

    private SparkSession sparkSession;

    @PostConstruct
    public void init(){
        SparkConf conf = new SparkConf()
                .set("spark.sql.streaming.schemaInference", "true");
        sparkSession = SparkSession
                .builder()
                .appName(sparkAppConfig.getAppName())
                //.master(sparkAppConfig.getMaster())
                .config(conf)
                .getOrCreate();
        sparkSession.sparkContext().hadoopConfiguration().set("spark.hadoop.f3.s3a.impl",
                "org.apache.hadoop.fs.s3a.S3AFileSystem");

    }
}
