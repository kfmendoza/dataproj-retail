package com.kbmendoza.dataproj.retail.streametl.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class FactInvoiceLine implements Serializable {
    private String invoice_date;
    private String invoice_no;
    private String customer_id;
    private String country_code;
    private String product_code;
    private String actual_unit_price;
    private String units;
    private String date_id;
}
