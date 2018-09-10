package com.microsoft.docs.iothub.samples.telemetry;

import com.google.gson.Gson;

public class FuelTheft extends TelemetryDataPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3896441187734991475L;
	private String message = "Fuel theft";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	// Serialize object to JSON format.
	public String serialize() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
