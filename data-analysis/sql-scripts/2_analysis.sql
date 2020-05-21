--Analysis 2: Item bought more frequently
SELECT product_code as product_code, SUM(units) AS total_units
FROM fact_invoice_line
GROUP BY dim_country.name, product_code
ORDER BY 3 DESC
LIMIT 50