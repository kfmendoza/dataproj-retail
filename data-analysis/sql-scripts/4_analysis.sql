--Analysis 4: Geographical distribution of the customers
SELECT d_geoloc.latitude, d_geoloc.longitude, COUNT(DISTINCT(customer_id)) AS "no_of_customers_in_lat_long"
FROM fact_geoloc f_geoloc, dim_geoloc d_geoloc
WHERE f_geoloc.geoloc_id=d_geoloc.id
GROYP BY 1, 2;