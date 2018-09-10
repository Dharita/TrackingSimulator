/**
 * 
 */
package com.microsoft.docs.iothub.samples.telemetry;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author dharita.chokshi
 *
 */
public abstract class TelemetryDataPoint implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2959080137028819006L;
	private String owner = "Dharita";
	private long timeCreated = Instant.now().toEpochMilli();

	// Serialize object to JSON format.
	public abstract String serialize();

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the timeCreated
	 */
	public long getTimeCreated() {
		return timeCreated;
	}

	/**
	 * @param timeCreated
	 *            the timeCreated to set
	 */
	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}

}
