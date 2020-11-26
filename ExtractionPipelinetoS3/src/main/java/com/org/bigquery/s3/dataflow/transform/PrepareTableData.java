package com.org.bigquery.s3.dataflow.transform;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import com.google.api.services.bigquery.model.TableRow;


public class PrepareTableData extends PTransform<PCollection<TableRow>, PCollection<String>> {
	

	private static final long serialVersionUID = 1L;

	@Override
	public PCollection<String> expand(PCollection<TableRow> input) {

		return input.apply(ParDo.of(new ConvertRowToText()));
	}



}