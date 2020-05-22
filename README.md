# Data Project - Online Retail

## Data Flow 
### How I identify data flow:
1. Data sources
1. Data ingestion
1. Data storage and warehouse; and
1. Data presentation
#### Data platform deployment: Create an end to end data pipeline using public cloud components
- Original plan is [here](https://github.com/kfmendoza/dataproj-retail/blob/master/data-flow/retail-orig.png), which is to deploy to AWS and use AWS components <br>
  Considerations:
   1. I am only using a Basic Tier and I am past Free limits
   1. Limited internet connection
- New plan is [here](https://github.com/kfmendoza/dataproj-retail/blob/master/data-flow/retail-new.png), which is to use local machine and use similar components
  Considerations:
   1. Laptop is quad-core and 8GB memory and may not accommodate concurrent running applications
   1. Is not scalable
   *Note: If you are interested in setting similar, see https://github.com/kfmendoza/dataproj-retail/blob/master/data-flow/README.md

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
1. Customer Analysis
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/CustomerPerCountry/Sheet3
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/CustomerperDayoftheweek/Sheet1
2. Product Analysis
* https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Top10Mostsellingproducts/Sheet1
3. Revenue Analysis
https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Revenue_15900971968340/Sheet2
https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Revenue_15900971968340/Sheet1
https://public.tableau.com/profile/krisna.mendoza#!/vizhome/Revenue_15900971968340/Sheet3


