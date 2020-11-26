package com.org.bigquery.s3.dataflow.common;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.io.aws.options.S3Options;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.ValueProvider;

	public interface BigqueryOption extends DataflowPipelineOptions,S3Options{
	  

	    @Description("GCS bucket to export BigQuery table data to (e.g. gs://mybucket/folder/).")
	    ValueProvider<String> getBucket();
        void setBucket(ValueProvider<String> bucket);
        
        @Description("query to be submitted")
		void setextractQuery( ValueProvider<String> value);
          ValueProvider<String>getextractQuery();
        

	    @Description("Optional: Number of shards for output file.")
	    @Default.Integer(0)
	    int getNumShards();
        void setNumShards(int numShards);

	    
        //AWS related configuration
		
		@Description(" Optional AWS Access Key")
	    void setAWSAccessKey(String value);
		String getAWSAccessKey();

	    @Description("Optional AWS secret key")
	    void setAWSSecretKey(String value);
        String getAWSSecretKey();
        
        @Description("Optional AWS Region")
	    void setAWSRegion(String value);
		String getAWSRegion();
		
		@Description("optional AWS bucket to export BigQuery table data to (e.g. gs://mybucket/folder/).")
	    String getAWSBucket();
        void setAWSBucket(String bucket);
      //AWS related configuration
		
        
        
        
      

		
		
		
	  }
