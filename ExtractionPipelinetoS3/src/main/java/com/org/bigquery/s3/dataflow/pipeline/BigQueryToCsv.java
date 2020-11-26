package com.org.bigquery.s3.dataflow.pipeline;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.beam.runners.dataflow.DataflowRunner;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.Compression;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.google.api.services.bigquery.model.TableRow;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.org.bigquery.s3.dataflow.common.BigqueryOption;
import com.org.bigquery.s3.dataflow.transform.PrepareTableData;
public class BigQueryToCsv {
	private static final Logger LOG = LoggerFactory.getLogger(BigQueryToCsv.class);

	


	private static final List<String> SCOPES = Arrays.asList(
			"https://www.googleapis.com/auth/cloud-platform",
			"https://www.googleapis.com/auth/devstorage.full_control",
			"https://www.googleapis.com/auth/userinfo.email",
			"https://www.googleapis.com/auth/datastore",
			"https://www.googleapis.com/auth/pubsub");

	/**
	 * Main entry point for pipeline execution.
	 *
	 * @param args Command line arguments to the pipeline.
	 * @throws IOException 
	 * 
	 */
	public static void main(String[] args) throws  IOException {
		

		LOG.info("Pipeline Excution Started"); 
		PipelineOptionsFactory.register(BigqueryOption.class);
		BigqueryOption options = PipelineOptionsFactory.fromArgs(args).withValidation().as(BigqueryOption.class);
		
		options.setRunner(DataflowRunner.class);
		options.setGcpCredential(ServiceAccountCredentials.fromStream(new FileInputStream("comm-forecastservice-dev-25e02fde9bb9.json")).createScoped(SCOPES));
		
		 AWSCredentials awsCredentials = new BasicAWSCredentials(options.getAWSAccessKey(),options.getAWSSecretKey());
	     options.setAwsCredentialsProvider(new AWSStaticCredentialsProvider(awsCredentials));

		// Create the pipeline.
		Pipeline pipeline = Pipeline.create(options);
		PCollection<TableRow> forcastData = pipeline
				.apply(
						BigQueryIO.readTableRows()
						.withTemplateCompatibility()
						.fromQuery(options.getextractQuery())
						.withoutValidation()
						.withTemplateCompatibility()
						.usingStandardSql());
		
						
				
		
		       if(options.getAWSAccessKey() != null && options.getAWSSecretKey() !=null && options.getAwsRegion()!= null)
		       {
				forcastData.apply("Convert Table to Transfer Object",
					    new PrepareTableData()).apply("Write data to S3",TextIO.write().to(options.getAWSBucket()).withSuffix(".csv").withNumShards(options.getNumShards()).withCompression(Compression.GZIP));

		       }
		       
		// Execute the pipeline and return the result.
		       pipeline.run();
	

	}

	

}

