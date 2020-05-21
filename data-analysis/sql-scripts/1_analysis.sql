--Analysis 1: Customers per country per day

SELECT d_country.name AS country, COUNT(DISTINCT(customer_id)) AS no_of_customers 
FROM fact_invoice_line f_invoice_line, dim_country d_country 
WHERE f_invoice_line.country_code = d_country.code 
GROUP BY d_country.name

SELECT d_date.cal_date AS cal_date, COUNT(DISTINCT(customer_id)) AS no_of_customers 
FROM fact_invoice_line f_invoice_line, dim_date d_date 
WHERE f_invoice_line.date_id = d_date.id 
GROUP BY d_date.cal_date