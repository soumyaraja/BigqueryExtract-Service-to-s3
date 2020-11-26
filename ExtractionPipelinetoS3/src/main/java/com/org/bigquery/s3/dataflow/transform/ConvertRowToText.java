package com.org.bigquery.s3.dataflow.transform;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

import org.apache.beam.sdk.transforms.DoFn;
import com.google.api.services.bigquery.model.TableRow;


public class ConvertRowToText extends DoFn<TableRow, String>
{


	private static final long serialVersionUID = 1L;
	

	@ProcessElement
	public void processElement(ProcessContext c) throws ParseException, IOException{
		if(null!= c)
		{
			

			String referenceDate = Optional.ofNullable((String) c.element().get("ReferenceDate")).orElse("");
			String locationID = Optional.ofNullable((String) c.element().get("LocationID")).orElse("");
			String item  = Optional.ofNullable((String) c.element().get("Item")).orElse("");
			String unit = Optional.ofNullable((String) c.element().get("Unit")).orElse("");
			String mean = Optional.ofNullable((String) c.element().get("Mean")).orElse("");
			String forecastDate = Optional.ofNullable((String) c.element().get("ForecastDate")).orElse("");
			Double value = Optional.ofNullable((Double) c.element().get("Value")).orElse(0.0);

			
			String output = referenceDate+","+item+","+locationID+","+mean+","+unit+","+forecastDate+","+value;
			
			c.output(output);

		}
	}








}
