# Data Project - Online Retail

## Data Flow
see https://github.com/kfmendoza/dataproj-retail/wiki/Data-Flow
## Data Sources

#### FILE UCI Online Retail Dataset
- Download the dataset [here](https://archive.ics.uci.edu/ml/machine-learning-databases/00352/)
- View the information [here](https://archive.ics.uci.edu/ml/datasets/online+retail)
- CSV version is here https://github.com/kfmendoza/dataproj-retail/blob/master/dataset/OnlineRetail.csv
#### FILE Country Master List File
- Countries with their ISO 2-letter code here https://github.com/kfmendoza/dataproj-retail/blob/master/dataset/country.csv
##### STREAM TO KAFKA Fake Data Generator (ToDo: needs more work)
- Source code here: https://github.com/kfmendoza/dataproj-retail/tree/master/fake-data-generator 

## Data Ingestion
Ingestion of Invoice Retail Dataset and Fake Data will be via ingested via SPARK STRUCTURED STREAMING and ingested into HDFS
### Data Preparation 
- References like Product Info, Customer Info, Country refs are usually coming from an RDBMS (e.g. CRM or Portal database). 
<br> In this case, data preparation was done via spark script. ToDo if AWS: spark-submit
### Streaming Application
#### Spark Streaming Application (Can stream messages from Filesystem and Kafka)
- Source code here: https://github.com/kfmendoza/dataproj-retail/tree/master/data-prep. 
<br> This can be readily packaged into executable jar. ToDo if AWS: spark-submit
#### Kafka (Message queue for event logs)
- The fake data will be sent over to the kafka broker and ingested by Spark and Kafka streaming application

## Data storage and warehouse
This is more of a data lake warehouse with data tool Apache Hive, metastore is Derby. *Note: Data lake cannot deduplicate. ToDo: Consider delta lake and supplement with Data warehouse with deduplication
#### Logical Data Models
- Logical data models are found here: https://github.com/kfmendoza/dataproj-retail/tree/master/data-model/logical.
#### Physical Data Models
- Physical data models are found here: https://github.com/kfmendoza/dataproj-retail/tree/master/data-model/logical
ToDo: Assignment 2 needs more work here

## Data Presentation 
Analyses done is only BI and Descriptive Analytics. These are straightforward Spark SQLs on the Data Lake via Hive. Exploratory Data Analysis will involve more complex functions and probably ML libs (e.g. Spark ML, Mahout)
#### Data analysis
Spark with Hive support was used to query Hive. Spark scripts (and SQL scripts) are here: https://github.com/kfmendoza/dataproj-retail/tree/master/data-analysis
#### Data Viz
Please check my analysis (*Don't forget to check all sheets*):
1. Customer Engagement Analysis
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/CustomerEngagement_15909579267610/Sheet1

2. Product Analysis
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Product_15909576486420/Sheet1

3. Revenue Analysis
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Revenue_15900971968340/Sheet2
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Revenue_15900971968340/Sheet1
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Revenue_15900971968340/Sheet3

4. Demographics
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Demographics_15909574257670/Userview2
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Demographics_15909574257670/Userview1

5. Geographical Analysis
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Geographical_15909575561200/Sheet1
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Geographical_15909575561200/Sheet2
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Geographical_15909575561200/Sheet3

## Assessment
Objective: Propose further steps for application development and address areas missing in this assessment
1. Operationalizing: Migrate all components to AWS, and practice infrastracture as a code see original plan https://github.com/kfmendoza/dataproj-retail/blob/master/data-flow/retail-orig.PNG
1. Monitoring: Use Hue to submit SQL and analyses work, as well as ad-hoc and batch work
1. Dataset curation: Some other datasets can be curated to supplement online retail dataset. Social Media and web scraping should be considered.
1. Analysis: Perform exploratory analysis. Here are some of what I have in mind:
- Market Basket analysis 
- Churn rate prediction
- NLP Sentiment analysis on Customer satisfaction level or reviews 


