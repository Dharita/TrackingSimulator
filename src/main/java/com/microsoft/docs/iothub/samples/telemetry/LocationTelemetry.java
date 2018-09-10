package com.microsoft.docs.iothub.samples.telemetry;

import com.google.gson.Gson;

public class LocationTelemetry extends TelemetryDataPoint {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4683282445111430084L;
	private double latitude;
	private double longitude;
	private String location = "Outside the provided Geo-Fence areas";
	private int stdb = 300;
	private int speed = 5;
	private int dbt = 31;

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the stdb
	 */
	public int getStdb() {
		return stdb;
	}

	/**
	 * @param stdb
	 *            the stdb to set
	 */
	public void setStdb(int stdb) {
		this.stdb = stdb;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the dbt
	 */
	public int getDbt() {
		return dbt;
	}

	/**
	 * @param dbt
	 *            the dbt to set
	 */
	public void setDbt(int dbt) {
		this.dbt = dbt;
	}

	@Override
	// Serialize object to JSON format.
	public String serialize() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}