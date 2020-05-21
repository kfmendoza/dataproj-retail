package com.kbmendoza.dataproj.retail.streametl.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class FactInvoiceLine implements Serializable {
    private String invoice_date;
    private String invoice_no;
    private int status;
    private String customer_id;
    private String country_code;
    private String product_code;
    private double actual_unit_price;
    private int units;
    private String date_id;
}
