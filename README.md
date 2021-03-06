# BigqueryExtract-Service-to-s3
Extract gcp bigquery data to aws s3 by providing sql query in gcp pub/sub.

# Steps for the utility
* external system publish sql query and aws s3 path with delimiter "|" to gcp pub/sub 
* Pub/sub triggers cloud fn
* cloud fn gets authenticated and call data-flow rest api
* cloud fn extract data from big query
* compress and write data to csv files based on underline shard design in aws S3

# pre-requisite
* create a service account and provides required access to push data to gcp pub sub
* Create a service account to deploy dataflow as a service in gcp
# compilation

## To compile dataflow.

  * mvn compile exec:java \
-Dexec.mainClass=com.org.bigquery.s3.dataflow.pipeline.BigQueryToCsv \
-Dexec.args="--project=<GCP PROJECT ID> \
--bucketUrl=s3://<S3 BUCKET NAME> \
--awsRegion=eu-west-1 \
--bqTableName=<BIGQUERY TABLE e.g. project:finance.transactions> \
--awsAccessKey=<YOUR ACCESS KEY> \
--awsSecretKey=<YOUR SECRET KEY> \
--runner=DataflowRunner \
--region=europe-west1 \
--stagingLocation=gs://<DATAFLOW BUCKET>/stage/ \
--tempLocation=gs://<DATAFLOW BUCKET>/temp/"
 
 ## Deploy NodeJS code in cloud function and provides required access to fetch data from gcp big query.

