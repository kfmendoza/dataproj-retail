--Analysis 3: Revenue per day
SELECT dim_date.cal_date, SUM(actual_unit_price*units) AS total_revenue
FROM fact_invoice_line, dim_date
WHERE fact_invoice_line.date_id = dim_date.id
GROUP BY dim_date.cal_date

