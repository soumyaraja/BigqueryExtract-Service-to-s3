package com.org.bigquery.s3.dataflow.transform;

import java.io.Serializable;

public class TableDTO implements Serializable
{
private static final long serialVersionUID = 1L;

public 	TableDTO()
{
	
}
	public String ReferenceDate;
	public String LocationID;
	public String Item;
	public String Unit;
	public String Mean;
	public String ForecastDate;
	public String Value;

	public String getReferenceDate() {
		return ReferenceDate;
	}
	public void setReferenceDate(String referenceDate) {
		ReferenceDate = referenceDate;
	}
	public String getLocationID() {
		return LocationID;
	}
	public void setLocationID(String locationID) {
		LocationID = locationID;
	}
	public String getItem() {
		return Item;
	}
	public void setItem(String item) {
		Item = item;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getMean() {
		return Mean;
	}
	public void setMean(String mean) {
		Mean = mean;
	}
	public String getForecastDate() {
		return ForecastDate;
	}
	public void setForecastDate(String forecastDate) {
		ForecastDate = forecastDate;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	
}
